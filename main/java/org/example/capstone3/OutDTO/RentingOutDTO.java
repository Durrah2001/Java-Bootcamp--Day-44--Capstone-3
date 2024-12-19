package org.example.capstone3.OutDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class RentingOutDTO {


    private Double pricePerDay;


    private String pickupLocation;


    private String dropOffLocation;

    private MotorcycleOutDTO motorcycles;



}

