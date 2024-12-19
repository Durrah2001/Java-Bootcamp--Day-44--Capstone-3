package org.example.capstone3.OutDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class EventOutDTO {

    private String name;

    private String location;

    private String details;

    private LocalDate date;
}
