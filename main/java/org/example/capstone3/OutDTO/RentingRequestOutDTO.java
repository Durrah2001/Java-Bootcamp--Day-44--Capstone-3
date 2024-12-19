package org.example.capstone3.OutDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
public class RentingRequestOutDTO {

    private LocalDate requestDate;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer totalCost;


}
