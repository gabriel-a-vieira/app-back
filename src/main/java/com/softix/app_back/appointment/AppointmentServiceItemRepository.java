package com.softix.app_back.appointment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentServiceItemRepository extends JpaRepository<AppointmentServiceItem, String> {

    List<AppointmentServiceItem> findByAppointmentIdOrderByExecutionOrderAsc(String appointmentId);

    void deleteByAppointmentId(String appointmentId);

}