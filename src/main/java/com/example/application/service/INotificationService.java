package com.example.application.service;

import com.example.application.domain.Notification;

import java.util.List;

public interface INotificationService {
    void addNotification(String message, String title, long personId);
    void markAsRead(long notificationId);
    void deleteNotification(long notificationId);
    List<Notification> getNotificationsByPersonId(long personId);
}
