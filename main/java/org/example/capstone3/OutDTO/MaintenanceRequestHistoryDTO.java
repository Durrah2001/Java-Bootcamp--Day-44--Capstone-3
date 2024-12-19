package org.example.capstone3.OutDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class MaintenanceRequestHistoryDTO {


    private LocalDate requestDate = LocalDate.now();

    private Double totalPrice;

    private String expertName;

    private String status;

    private LocalDate pickupDate;

    private List<MotorcycleOutDTO> motorcycleS;


    public MaintenanceRequestHistoryDTO() {

    }
}
