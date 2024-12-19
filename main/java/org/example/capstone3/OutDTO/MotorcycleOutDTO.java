package org.example.capstone3.OutDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MotorcycleOutDTO {

    private String brand;

    private String model;

    private Integer year;

    private Double price;

    private String color;

    private Boolean isAvailable;

    private Boolean isForSale;

    private Boolean hasOffer;



}
