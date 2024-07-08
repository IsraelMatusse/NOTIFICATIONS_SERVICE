package com.personal_projects.notifications_qpi.infrastructure.enums;

import lombok.Getter;

@Getter
public enum Channel {

    SMS(1l, "SMS"),
    PUSH(2l, "PUSH"),
    EMAIL(3l, "EMAIL"),
    WHATSAPP(4l, "WHATSAPP");

    private final Long id;
    private final String name;


    Channel(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
