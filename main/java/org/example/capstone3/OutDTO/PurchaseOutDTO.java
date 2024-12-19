package org.example.capstone3.OutDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class PurchaseOutDTO {
    private LocalDate purchaseDate ;

    private PurchaseUserOutDTO user;

    private PurchaseMotorcycleOutDTO motorcycle;

    public PurchaseOutDTO(LocalDate purchaseDate, Boolean isForSale, PurchaseUserOutDTO purchaseUserOutDTO, PurchaseMotorcycleOutDTO purchaseMotorcycleOutDTO) {
    }
}
