package org.example.capstone3.OutDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyOutDTO {


    private String name;
    private String email;
    private String contactInfo;
    private Boolean isApproved;

}
