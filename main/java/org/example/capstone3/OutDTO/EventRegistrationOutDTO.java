package org.example.capstone3.OutDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventRegistrationOutDTO {


    private Integer event_id;

    private Integer owner_id;

    private Integer user_id;

    private Integer motorcycle_id;

    private String status;

}
