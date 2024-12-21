package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiResponse;
import org.example.capstone3.InDTO.RentingRequestDTO_In;
import org.example.capstone3.Service.RentingRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/motorcycle-system/renting-request")

public class RentingRequestController {

    private final RentingRequestService rentingRequestService;


    @GetMapping("/get")
    public ResponseEntity getAllRentingRequests() {
        return ResponseEntity.status(200).body(rentingRequestService.getAllRentingRequests());
    }

    @PostMapping("/add/{user_id}")
    public ResponseEntity addRentingRequest(@PathVariable Integer user_id,@RequestBody @Valid RentingRequestDTO_In rentingRequestInDTO) {
        return ResponseEntity.status(200).body(new ApiResponse("Your rental request has been successfully created! Total cost:" + rentingRequestService.addRentingRequest(user_id,rentingRequestInDTO)));
    }

    @PutMapping("/update/{rentingRequest_id}/{user_id}")
    public ResponseEntity updateRentingRequest(@PathVariable Integer rentingRequest_id,@PathVariable Integer user_id,@RequestBody @Valid RentingRequestDTO_In rentingRequestInDTO) {
        rentingRequestService.updateRentingRequest(rentingRequest_id,user_id, rentingRequestInDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Renting Request Updated "));
    }

    @DeleteMapping("/delete/{rentingRequest_id}")
    public ResponseEntity deleteRentingRequest(@PathVariable Integer rentingRequest_id) {
        rentingRequestService.deleteRentingRequest(rentingRequest_id);
        return ResponseEntity.status(200).body(new ApiResponse("Renting Request Deleted "));
    }


    @PutMapping("/extend-rental/{rentingRequestId}/{newEndDate}/{userId}")
    public ResponseEntity extendRental(@PathVariable Integer rentingRequestId,@PathVariable LocalDate newEndDate,@PathVariable Integer userId) {
        rentingRequestService.extendRental(rentingRequestId,newEndDate,userId);
        return ResponseEntity.status(200).body(new ApiResponse("Renting Request Extend "));
    }


}
