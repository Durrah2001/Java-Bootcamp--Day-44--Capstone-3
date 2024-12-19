package org.example.capstone3.InDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FineDTO_In {


    @NotNull(message = "Empty user id ")
    private Integer userId;

    @NotNull(message = "Empty renting Request Id ")
    private Integer rentingRequestId;

    @NotEmpty(message = "Empty description ")
    private String description;






}

