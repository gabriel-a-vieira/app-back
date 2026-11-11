package com.softix.app_back.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Builds calendar invites (iCalendar / RFC 5545 .ics) and "add to Google
 * Calendar" links for a CalendarEvent. Written by hand instead of pulling a
 * library (ical4j): the subset needed here -- one VEVENT with two VALARMs -- is
 * small and plain text.
 *
 * Times: appointments are stored as LocalDateTime without zone (the wall-clock
 * time the client picked). The server runs in UTC on Render, so the zone is
 * explicit config (app.time-zone) instead of the JVM default; the .ics carries
 * UTC instants converted from it, which every calendar app shows in the
 * viewer's own zone.
 *
 * Reminders: VALARMs 1 day and 1 hour before. Apple Calendar and Outlook honour
 * them; Google Calendar ignores alarms from imported invites and applies the
 * user's own default notifications instead.
 */
@Component
public class CalendarInviteBuilder {

    static final String PRODUCT_ID = "-//Softix//Schedule App//PT-BR";

    private static final DateTimeFormatter UTC_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'").withZone(ZoneOffset.UTC);

    private static final DateTimeFormatter LOCAL_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");

    private static final String GOOGLE_CALENDAR_URL = "https://calendar.google.com/calendar/render?action=TEMPLATE";

    private static final int MAX_LINE_OCTETS = 75;

    private final ZoneId zoneId;

    private final Clock clock;

    @Autowired
    public CalendarInviteBuilder(@Value("${app.time-zone:America/Sao_Paulo}") String timeZone) {
        this(ZoneId.of(timeZone), Clock.systemUTC());
    }

    CalendarInviteBuilder(ZoneId zoneId, Clock clock) {
        this.zoneId = zoneId;
        this.clock = clock;
    }

    /**
     * An invite meant for e-mail (METHOD:REQUEST, or METHOD:CANCEL when the
     * event is cancelled): mail clients show it as an invitation and later
     * e-mails with the same UID update/remove the entry.
     *
     * @param organizerEmail sender address (MAIL_FROM); may be blank
     * @param attendeeEmail  recipient address
     */
    public String buildInvite(CalendarEvent event, String organizerEmail, String attendeeEmail, String attendeeName) {
        return build(event, event.cancelled() ? "CANCEL" : "REQUEST", organizerEmail, attendeeEmail, attendeeName);
    }

    /**
     * A standalone file for "download and open" (METHOD:PUBLISH): the calendar
     * app just imports it, no invitation/RSVP semantics.
     */
    public String buildFile(CalendarEvent event) {
        return build(event, "PUBLISH", null, null, null);
    }

    /**
     * Google Calendar "create event" link with the fields pre-filled. Uses the
     * wall-clock times plus ctz, so no conversion is involved.
     */
    public String buildGoogleCalendarUrl(CalendarEvent event) {

        StringBuilder url = new StringBuilder(GOOGLE_CALENDAR_URL)
                .append("&text=").append(encode(event.summary()))
                .append("&dates=").append(event.startAt().format(LOCAL_FORMAT)).append('/').append(event.endAt().format(LOCAL_FORMAT))
                .append("&ctz=").append(encode(zoneId.getId()));

        if (isNotBlank(event.description())) {
            url.append("&details=").append(encode(event.description()));
        }

        if (isNotBlank(event.location())) {
            url.append("&location=").append(encode(event.location()));
        }

        return url.toString();
    }

    private String build(CalendarEvent event, String method, String organizerEmail, String attendeeEmail, String attendeeName) {

        StringBuilder ics = new StringBuilder();

        line(ics, "BEGIN:VCALENDAR");
        line(ics, "VERSION:2.0");
        line(ics, "PRODID:" + PRODUCT_ID);
        line(ics, "CALSCALE:GREGORIAN");
        line(ics, "METHOD:" + method);

        line(ics, "BEGIN:VEVENT");
        line(ics, "UID:" + event.uid());
        line(ics, "SEQUENCE:" + event.sequence());
        line(ics, "DTSTAMP:" + UTC_FORMAT.format(Instant.now(clock)));
        line(ics, "DTSTART:" + toUtc(event.startAt()));
        line(ics, "DTEND:" + toUtc(event.endAt()));
        line(ics, "SUMMARY:" + escape(event.summary()));

        if (isNotBlank(event.description())) {
            line(ics, "DESCRIPTION:" + escape(event.description()));
        }

        if (isNotBlank(event.location())) {
            line(ics, "LOCATION:" + escape(event.location()));
        }

        if (isNotBlank(organizerEmail)) {
            line(ics, "ORGANIZER;CN=Softix:mailto:" + organizerEmail);
        }

        if (isNotBlank(attendeeEmail)) {
            String cn = isNotBlank(attendeeName) ? ";CN=" + quoteParam(attendeeName) : "";
            line(ics, "ATTENDEE" + cn + ";ROLE=REQ-PARTICIPANT;PARTSTAT=NEEDS-ACTION;RSVP=FALSE:mailto:" + attendeeEmail);
        }

        line(ics, "STATUS:" + (event.cancelled() ? "CANCELLED" : "CONFIRMED"));
        line(ics, "TRANSP:OPAQUE");

        if (!event.cancelled()) {
            alarm(ics, "-P1D", "Amanha: " + event.summary());
            alarm(ics, "-PT1H", "Em 1 hora: " + event.summary());
        }

        line(ics, "END:VEVENT");
        line(ics, "END:VCALENDAR");

        return ics.toString();
    }

    private void alarm(StringBuilder ics, String trigger, String description) {
        line(ics, "BEGIN:VALARM");
        line(ics, "ACTION:DISPLAY");
        line(ics, "TRIGGER:" + trigger);
        line(ics, "DESCRIPTION:" + escape(description));
        line(ics, "END:VALARM");
    }

    private String toUtc(LocalDateTime localDateTime) {
        return UTC_FORMAT.format(localDateTime.atZone(zoneId).toInstant());
    }

    /**
     * RFC 5545 3.3.11 TEXT escaping.
     */
    static String escape(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace(";", "\\;")
                .replace(",", "\\,")
                .replace("\r\n", "\\n")
                .replace("\n", "\\n")
                .replace("\r", "\\n");
    }

    private static String quoteParam(String value) {
        // parameter values can't contain DQUOTE; quoting covers ; , :
        return "\"" + value.replace("\"", "'") + "\"";
    }

    /**
     * Appends one content line, folded at 75 octets (UTF-8) as RFC 5545 3.1
     * requires: continuation lines start with a single space. Never splits a
     * multi-byte character.
     */
    static void line(StringBuilder out, String content) {

        int octets = 0;

        for (int i = 0; i < content.length(); ) {

            int codePoint = content.codePointAt(i);
            int size = new String(Character.toChars(codePoint)).getBytes(StandardCharsets.UTF_8).length;

            if (octets + size > MAX_LINE_OCTETS) {
                out.append("\r\n ");
                octets = 1;
            }

            out.appendCodePoint(codePoint);
            octets += size;
            i += Character.charCount(codePoint);

        }

        out.append("\r\n");
    }

    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8).replace("+", "%20");
    }

    private static boolean isNotBlank(String value) {
        return value != null && !value.isBlank();
    }

}
