package org.example.capstone3.InDTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;


@Data
@AllArgsConstructor
public class MaintenanceRequestDTO_In {

    @NotEmpty(message = "Expert name is required!")
    private String expertName;

    private Integer motorcycle_id;

    private Integer owner_id;

    @NotNull
    private LocalDate pickupDate;


}
