package org.example.capstone3.OutDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PurchaseUserOutDTO {

    private String name;

    private String email;

    private String phoneNumber;

    private Integer age;

    private String address;
}
