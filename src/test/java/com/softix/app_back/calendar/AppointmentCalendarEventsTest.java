package com.softix.app_back.calendar;

import com.softix.app_back.address.Address;
import com.softix.app_back.city.City;
import com.softix.app_back.state.State;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class AppointmentCalendarEventsTest {

    private static final LocalDateTime START = LocalDateTime.of(2026, 11, 10, 14, 30);

    @Test
    void of_buildsTitleDescriptionAndStableUid() {

        CalendarEvent event = AppointmentCalendarEvents.of("id-1", 3, START, START.plusMinutes(45), "Studio X", "Ana", "Corte", "Rua A", false);

        assertThat(event.uid()).isEqualTo("id-1@softix-schedule");
        assertThat(event.sequence()).isEqualTo(3);
        assertThat(event.summary()).isEqualTo("Agendamento - Studio X");
        assertThat(event.description()).isEqualTo("Profissional: Ana\nServicos: Corte");
        assertThat(event.location()).isEqualTo("Rua A");
        assertThat(event.cancelled()).isFalse();
    }

    @Test
    void of_toleratesMissingOptionalData() {

        CalendarEvent event = AppointmentCalendarEvents.of("id-1", null, START, null, "", "", null, null, true);

        assertThat(event.sequence()).isZero();
        assertThat(event.endAt()).isEqualTo(START);
        assertThat(event.summary()).isEqualTo("Agendamento");
        assertThat(event.description()).isNull();
        assertThat(event.cancelled()).isTrue();
    }

    @Test
    void formatAddress_joinsTheAvailableParts() {

        State state = new State();
        state.setAbbreviation("PR");

        City city = new City();
        city.setName("Curitiba");
        city.setState(state);

        Address address = new Address();
        address.setStreet("Rua A");
        address.setNumber("10");
        address.setNeighborhood("Centro");
        address.setCity(city);

        assertThat(AppointmentCalendarEvents.formatAddress(address)).isEqualTo("Rua A, 10 - Centro, Curitiba - PR");
    }

    @Test
    void formatAddress_returnsNullWhenEmpty() {
        assertThat(AppointmentCalendarEvents.formatAddress(null)).isNull();
        assertThat(AppointmentCalendarEvents.formatAddress(new Address())).isNull();
    }

}
