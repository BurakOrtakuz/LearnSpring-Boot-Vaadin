package com.example.application.service;

import com.example.application.domain.*;
import com.example.application.repository.INotificationRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService implements INotificationService {
    private final INotificationRepository notificationRepository;
    private final IPersonService personService;

    public NotificationService(INotificationRepository notificationRepository, IPersonService personService) {
        this.notificationRepository = notificationRepository;
        this.personService = personService;
    }

    @Override
    public void addNotification(String message, String title, long personId, NotificationType type, Long relatedEntityId) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setTitle(title);
        notification.setReadStatus(false);
        notification.setTimestamp(new Date());
        Optional<Person> person = personService.findById(personId);
        if (person.isEmpty()) {
            throw new IllegalArgumentException("Person with ID " + personId + " does not exist.");
        }
        notification.setPerson(person.get());
        notification.setType(type);
        notification.setRelatedEntityId(relatedEntityId);
        notificationRepository.save(notification);
    }

    @Override
    public void markAsRead(long notificationId) {
        Optional<Notification> notificationOpt = notificationRepository.findById(notificationId);
        if (notificationOpt.isPresent()) {
            Notification notification = notificationOpt.get();
            notification.setReadStatus(true);
            notificationRepository.save(notification);
        }
    }

    @Override
    public void deleteNotification(long notificationId) {
        if (notificationRepository.existsById(notificationId)) {
            notificationRepository.deleteById( notificationId);
        }
    }

    @Override
    public List<Notification> getNotificationsByPersonId(long personId) {
        return notificationRepository.findAll().stream()
                .filter(notification -> notification.getPerson().getId().equals(personId))
                .toList();
    }

    @Override
    public void sendAppointmentNotificationToPatient(Person patient, Doctor doctor, Examination examination, Date appointmentDate) {
        String title = "Yeni Randevu Oluşturuldu";
        String message = String.format("Sayın %s %s, Dr. %s %s ile %s tarihinde randevunuz oluşturulmuştur.",
                patient.getFirstName(),
                patient.getLastName(),
                doctor.getPerson().getFirstName(),
                doctor.getPerson().getLastName(),
                formatDate(appointmentDate));

        addNotification(message, title, patient.getId(), NotificationType.EXAMINATION, examination.getExaminationId());
    }

    @Override
    public void sendAppointmentNotificationToDoctor(Doctor doctor, Person patient,Examination examination, Date appointmentDate) {
        String title = "Yeni Randevu Bildirimi";
        String message = String.format("Dr. %s %s, %s %s isimli hasta ile %s tarihinde randevunuz bulunmaktadır.",
                doctor.getPerson().getFirstName(),
                doctor.getPerson().getLastName(),
                patient.getFirstName(),
                patient.getLastName(),
                formatDate(appointmentDate));

        addNotification(message, title, doctor.getPerson().getId(), NotificationType.EXAMINATION, examination.getExaminationId());
    }

    private String formatDate(Date date) {
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm");
        return formatter.format(date);
    }
}
