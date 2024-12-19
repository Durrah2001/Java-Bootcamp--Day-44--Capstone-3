package org.example.capstone3.OutDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OwnerEventRegistrationOutDTO {


    private String status;

    private Integer event_id;

    private Integer owner_id;

    private Integer motorcycle_id;

}
