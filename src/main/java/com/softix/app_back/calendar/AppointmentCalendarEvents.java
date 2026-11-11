package com.softix.app_back.calendar;

import com.softix.app_back.address.Address;
import com.softix.app_back.city.City;

import java.time.LocalDateTime;

/**
 * Single place that decides how an appointment looks on the client's calendar
 * (title, description, UID), so the e-mailed invite and the downloaded file
 * always describe -- and match -- the same entry.
 */
public final class AppointmentCalendarEvents {

    static final String UID_DOMAIN = "@softix-schedule";

    private AppointmentCalendarEvents() {
    }

    public static CalendarEvent of(String appointmentId,
                                   Integer version,
                                   LocalDateTime startAt,
                                   LocalDateTime endAt,
                                   String companyName,
                                   String professionalName,
                                   String serviceNames,
                                   String companyAddress,
                                   boolean cancelled) {

        StringBuilder description = new StringBuilder();

        if (isNotBlank(professionalName)) {
            description.append("Profissional: ").append(professionalName);
        }

        if (isNotBlank(serviceNames)) {

            if (!description.isEmpty()) {
                description.append('\n');
            }

            description.append("Servicos: ").append(serviceNames);
        }

        String summary = isNotBlank(companyName) ? "Agendamento - " + companyName : "Agendamento";

        return new CalendarEvent(
                appointmentId + UID_DOMAIN,
                version != null ? version : 0,
                startAt,
                endAt != null ? endAt : startAt,
                summary,
                description.isEmpty() ? null : description.toString(),
                companyAddress,
                cancelled);
    }

    /**
     * "Rua X, 123 - Bairro, Cidade - UF", skipping missing parts; null when
     * there's nothing to show.
     */
    public static String formatAddress(Address address) {

        if (address == null) {
            return null;
        }

        StringBuilder text = new StringBuilder();

        if (isNotBlank(address.getStreet())) {
            text.append(address.getStreet());

            if (isNotBlank(address.getNumber())) {
                text.append(", ").append(address.getNumber());
            }
        }

        if (isNotBlank(address.getNeighborhood())) {
            appendSeparated(text, " - ", address.getNeighborhood());
        }

        City city = address.getCity();

        if (city != null && isNotBlank(city.getName())) {

            String cityText = city.getName();

            if (city.getState() != null && isNotBlank(city.getState().getAbbreviation())) {
                cityText += " - " + city.getState().getAbbreviation();
            }

            appendSeparated(text, ", ", cityText);
        }

        return text.isEmpty() ? null : text.toString();
    }

    private static void appendSeparated(StringBuilder text, String separator, String value) {

        if (!text.isEmpty()) {
            text.append(separator);
        }

        text.append(value);
    }

    private static boolean isNotBlank(String value) {
        return value != null && !value.isBlank();
    }

}
