package com.softix.app_back.appointment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AppointmentServiceItemRepository extends JpaRepository<AppointmentServiceItem, String> {

}
