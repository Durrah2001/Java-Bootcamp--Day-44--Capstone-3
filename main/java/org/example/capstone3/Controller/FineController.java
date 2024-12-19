package org.example.capstone3.Controller;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiResponse;
import org.example.capstone3.Service.FineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/motorcycle-system/fine")

public class FineController {

    private final FineService fineService;



    @GetMapping("/getFinesByUserId/{userId}")
    public ResponseEntity getFinesByUserId(@PathVariable Integer userId){
        return ResponseEntity.status(200).body(fineService.getAllFineByUserId(userId));
    }

    @GetMapping("/getNumberOfFines/{userId}")
    public ResponseEntity getNumberOfFinesByUserId(@PathVariable Integer userId){
        return ResponseEntity.status(200).body(new ApiResponse("Number of fines: "+fineService.getNumberOfFinesByUserId(userId)));
    }

    @PutMapping("markBikeAsReturned/{rentingRequestId}")
    public ResponseEntity markBikeAsReturned(@PathVariable Integer rentingRequestId){
        fineService.markBikeAsReturned(rentingRequestId);
        return ResponseEntity.status(200).body(new ApiResponse("Bike returned"));
    }

    @PutMapping("/payFine/{fineId}")
    public ResponseEntity payFine(@PathVariable Integer fineId){
        fineService.payFine(fineId);
        return ResponseEntity.status(200).body(new ApiResponse("Fine with ID " + fineId + " has been successfully paid."));
    }

    @GetMapping("/getUnpaidFinesByUserId/{userId}")
    public ResponseEntity getUnpaidFinesByUserId(@PathVariable Integer userId){
        return ResponseEntity.status(200).body(fineService.getUnpaidFinesByUserId(userId));
    }







}