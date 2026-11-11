package com.softix.app_back.calendar;

import java.time.LocalDateTime;

/**
 * Provider-agnostic description of one calendar entry (today: an appointment),
 * turned into an .ics invite or a Google Calendar link by CalendarInviteBuilder.
 *
 * @param uid         stable id across updates -- a calendar app matches a later
 *                    invite to the entry it already has by this value, which is
 *                    what makes a reschedule move the event and a cancel remove it
 * @param sequence    revision number; must grow on every change, or calendar apps
 *                    ignore the update as stale
 * @param startAt     local wall-clock time (as stored), interpreted in the
 *                    configured app.time-zone
 * @param endAt       same as startAt
 * @param summary     event title
 * @param description free text, may contain line breaks; may be null
 * @param location    free text address; may be null
 * @param cancelled   true produces a cancellation instead of an invite
 */
public record CalendarEvent(String uid,
                            int sequence,
                            LocalDateTime startAt,
                            LocalDateTime endAt,
                            String summary,
                            String description,
                            String location,
                            boolean cancelled) {
}
