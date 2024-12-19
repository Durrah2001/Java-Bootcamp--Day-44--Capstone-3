package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiResponse;
import org.example.capstone3.InDTO.PurchaseDTO_In;
import org.example.capstone3.Service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/motorcycle-system/purchase")

public class PurchaseController {


    private final PurchaseService purchaseService;

    @GetMapping("/get")
    public ResponseEntity getAllPurchases() {
        return ResponseEntity.status(200).body(purchaseService.getAllPurchases());
    }

    @PostMapping("/add")
    public ResponseEntity addPurchase(@RequestBody @Valid PurchaseDTO_In purchaseInDTO) {
        purchaseService.addPurchase(purchaseInDTO);
        return ResponseEntity.status(200).body(new ApiResponse("purchases added"));
    }










}
