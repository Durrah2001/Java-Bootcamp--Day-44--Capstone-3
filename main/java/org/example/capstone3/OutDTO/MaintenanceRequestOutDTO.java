package org.example.capstone3.OutDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class MaintenanceRequestOutDTO {

    private LocalDate requestDate = LocalDate.now();

    private Double totalPrice;

    private String expertName;

    private String status;

    private LocalDate pickupDate;

    private MotorcycleOutDTO motorcycleS;



    public MaintenanceRequestOutDTO(LocalDate requestDate, Double totalPrice, String expertName,
                                    @Pattern(regexp = "^(Pending|Completed)$") @NotEmpty(message = "varchar(10)") String status,
                                    LocalDate pickupDate, MotorcycleOutDTO motorcycleDTO) {
        this.requestDate = requestDate;
        this.totalPrice = totalPrice;
        this.expertName = expertName;
        this.status = status;
        this.pickupDate = pickupDate;
        this.motorcycleS = motorcycleDTO;
    }
}
