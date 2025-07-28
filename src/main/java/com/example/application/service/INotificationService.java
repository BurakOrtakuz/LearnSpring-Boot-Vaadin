package com.example.application.service;

import com.example.application.domain.*;

import java.util.Date;
import java.util.List;

public interface INotificationService {
    void addNotification(String message, String title, long personId, NotificationType type, Long relatedEntityId);
    void markAsRead(long notificationId);
    void deleteNotification(long notificationId);
    List<Notification> getNotificationsByPersonId(long personId);
    void sendAppointmentNotificationToPatient(Person patient, Doctor doctor, Examination examination, Date appointmentDate);
    void sendAppointmentNotificationToDoctor(Doctor doctor, Person patient,Examination examination, Date appointmentDate);
}
