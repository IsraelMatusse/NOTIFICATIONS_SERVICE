package com.personal_projects.notifications_qpi.dtos.internal;

import lombok.Data;

@Data

public class ResponseAPI{
       private final  String message;
       private final Object data;

       public ResponseAPI(String message, Object data) {
           this.message = message;
           this.data = data;
       }

}
