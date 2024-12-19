package org.example.capstone3.OutDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserEventRegistrationOutDTO {

    private String status;

    private Integer eventId;

    private Integer userId;

}
