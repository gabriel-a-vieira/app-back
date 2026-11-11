package com.softix.app_back.calendar;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class CalendarInviteBuilderTest {

    private static final Clock FIXED_CLOCK = Clock.fixed(Instant.parse("2026-11-01T12:00:00Z"), ZoneOffset.UTC);

    private final CalendarInviteBuilder builder = new CalendarInviteBuilder(ZoneId.of("America/Sao_Paulo"), FIXED_CLOCK);

    private CalendarEvent event(boolean cancelled) {
        return new CalendarEvent(
                "abc-123@softix-schedule",
                2,
                LocalDateTime.of(2026, 11, 10, 14, 30),
                LocalDateTime.of(2026, 11, 10, 15, 15),
                "Agendamento - Studio X",
                "Profissional: Ana\nServicos: Corte, Barba",
                "Rua A, 10 - Centro, Curitiba - PR",
                cancelled);
    }

    @Test
    void buildInvite_convertsLocalTimesToUtcUsingTheConfiguredZone() {

        String ics = builder.buildInvite(event(false), "noreply@teste.com", "cliente@teste.com", "Cliente");

        // Sao Paulo is UTC-3 (no DST since 2019)
        assertThat(ics)
                .contains("DTSTART:20261110T173000Z\r\n")
                .contains("DTEND:20261110T181500Z\r\n")
                .contains("DTSTAMP:20261101T120000Z\r\n");
    }

    @Test
    void buildInvite_isARequestWithStableUidSequenceAndBothReminders() {

        String ics = builder.buildInvite(event(false), "noreply@teste.com", "cliente@teste.com", "Cliente");

        assertThat(ics)
                .startsWith("BEGIN:VCALENDAR\r\n")
                .endsWith("END:VCALENDAR\r\n")
                .contains("METHOD:REQUEST\r\n")
                .contains("UID:abc-123@softix-schedule\r\n")
                .contains("SEQUENCE:2\r\n")
                .contains("STATUS:CONFIRMED\r\n")
                .contains("ORGANIZER;CN=Softix:mailto:noreply@teste.com\r\n")
                .contains("mailto:cliente@teste.com")
                .contains("TRIGGER:-P1D\r\n")
                .contains("TRIGGER:-PT1H\r\n");
    }

    @Test
    void buildInvite_forACancelledEventIsACancelWithoutReminders() {

        String ics = builder.buildInvite(event(true), "noreply@teste.com", "cliente@teste.com", "Cliente");

        assertThat(ics)
                .contains("METHOD:CANCEL\r\n")
                .contains("STATUS:CANCELLED\r\n")
                .contains("UID:abc-123@softix-schedule\r\n")
                .doesNotContain("BEGIN:VALARM");
    }

    @Test
    void buildFile_isAPublishWithoutOrganizerOrAttendee() {

        String ics = builder.buildFile(event(false));

        assertThat(ics)
                .contains("METHOD:PUBLISH\r\n")
                .doesNotContain("ORGANIZER")
                .doesNotContain("ATTENDEE")
                .contains("TRIGGER:-P1D\r\n");
    }

    @Test
    void buildFile_escapesSpecialCharactersInText() {

        String ics = builder.buildFile(event(false));

        assertThat(ics)
                .contains("DESCRIPTION:Profissional: Ana\\nServicos: Corte\\, Barba\r\n")
                .contains("LOCATION:Rua A\\, 10 - Centro\\, Curitiba - PR\r\n");
    }

    @Test
    void escape_handlesBackslashSemicolonCommaAndNewlines() {
        assertThat(CalendarInviteBuilder.escape("a\\b;c,d\r\ne\nf")).isEqualTo("a\\\\b\\;c\\,d\\ne\\nf");
    }

    @Test
    void line_foldsAt75OctetsWithoutSplittingMultiByteCharacters() {

        StringBuilder out = new StringBuilder();
        CalendarInviteBuilder.line(out, "SUMMARY:" + "ção".repeat(40));

        String[] lines = out.toString().split("\r\n");

        assertThat(lines.length).isGreaterThan(1);
        assertThat(Arrays.stream(lines).allMatch(l -> l.getBytes(StandardCharsets.UTF_8).length <= 75)).isTrue();
        assertThat(Arrays.stream(lines).skip(1).allMatch(l -> l.startsWith(" "))).isTrue();

        // unfolding (drop CRLF + one space) gives back the original text
        assertThat(out.toString().replace("\r\n ", "").replace("\r\n", "")).isEqualTo("SUMMARY:" + "ção".repeat(40));
    }

    @Test
    void buildGoogleCalendarUrl_prefillsTitleDatesZoneDetailsAndLocation() {

        String url = builder.buildGoogleCalendarUrl(event(false));

        assertThat(url)
                .startsWith("https://calendar.google.com/calendar/render?action=TEMPLATE")
                .contains("&text=Agendamento%20-%20Studio%20X")
                .contains("&dates=20261110T143000/20261110T151500")
                .contains("&ctz=America%2FSao_Paulo")
                .contains("&details=Profissional%3A%20Ana%0AServicos%3A%20Corte%2C%20Barba")
                .contains("&location=Rua%20A%2C%2010");
    }

}
