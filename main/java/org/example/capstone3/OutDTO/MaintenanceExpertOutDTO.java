package org.example.capstone3.OutDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MaintenanceExpertOutDTO {

    private String name;
    private String email;
    private String specialty;
    private Boolean isApproved;
    private String description;


}
