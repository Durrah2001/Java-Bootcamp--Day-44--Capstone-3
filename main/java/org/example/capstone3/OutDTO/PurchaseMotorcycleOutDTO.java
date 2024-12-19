package org.example.capstone3.OutDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PurchaseMotorcycleOutDTO {

    private String brand; // e.g., Yamaha, Honda

    private String model;

    private Integer year;

    private Double price;

    private String color;

    private Boolean isAvailable;
}
