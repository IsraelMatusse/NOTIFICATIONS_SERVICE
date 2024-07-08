package com.personal_projects.notifications_qpi.infrastructure.enums;

import lombok.Getter;

@Getter
public enum NotificationStatus {

    SENT(1l, "SENT"),
    DELIVERED(2l, "DELIVERED"),
    FAILED(3l, "FAILED"),
    PENDING(4L, "PENDING");

    private final Long id;
    private final String name;

    NotificationStatus(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
