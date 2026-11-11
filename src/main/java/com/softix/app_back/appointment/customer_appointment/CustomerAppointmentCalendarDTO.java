package com.softix.app_back.appointment.customer_appointment;

/**
 * Calendar data for one of the client's own appointments.
 *
 * @param googleCalendarUrl link that opens Google Calendar with the event pre-filled
 * @param ics               iCalendar file content (Apple Calendar, Outlook, Android etc.)
 * @param fileName          suggested file name for the .ics download
 */
public record CustomerAppointmentCalendarDTO(String googleCalendarUrl, String ics, String fileName) {
}
