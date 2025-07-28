package com.example.application.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "notification")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "message")
    private String message;

    @Column(name = "read_status", nullable = false, columnDefinition = "boolean default false")
    private Boolean readStatus = false;

    @Column(name = "timestamp")
    private Date timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Person person;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private NotificationType type = NotificationType.GENERAL;

    @Column(name = "related_entity_id")
    private Long relatedEntityId;

    public boolean isExaminationNotification() {
        return NotificationType.EXAMINATION.equals(this.type);
    }

    public boolean isPrescriptionNotification() {
        return NotificationType.PRESCRIPTION.equals(this.type);
    }

    public Long getExaminationId() {
        return isExaminationNotification() ? this.relatedEntityId : null;
    }

}
