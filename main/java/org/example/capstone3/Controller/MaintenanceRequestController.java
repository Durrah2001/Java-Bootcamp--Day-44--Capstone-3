package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiResponse;
import org.example.capstone3.Model.MaintenanceRequest;
import org.example.capstone3.OutDTO.MaintenanceRequestOutDTO;
import org.example.capstone3.Service.MaintenanceRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/motorcycle-system/maintenance-request")
@RequiredArgsConstructor
public class MaintenanceRequestController {

    private final MaintenanceRequestService maintenanceRequestService;

    @GetMapping("/get")
    public ResponseEntity getAllMaintenanceRequest(){
        List<MaintenanceRequestOutDTO> maintenanceRequestDTOS = maintenanceRequestService.getAllMaintenanceRequest();
        return ResponseEntity.status(200).body(maintenanceRequestDTOS);
    }

    @PostMapping("/add/{ownerId}/{expertId}")
    public ResponseEntity addMaintenanceRequest(@PathVariable Integer ownerId, @PathVariable Integer  expertId, @RequestBody @Valid MaintenanceRequest maintenanceRequest){
        maintenanceRequestService.addMaintenanceRequest(ownerId, expertId, maintenanceRequest);
        return ResponseEntity.status(200).body(new ApiResponse("MaintenanceRequest created!"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateMaintenanceRequest(@PathVariable Integer id, @RequestBody @Valid MaintenanceRequest maintenanceRequest){

        maintenanceRequestService.updateMaintenanceRequest(id, maintenanceRequest);
        return ResponseEntity.status(200).body(new ApiResponse("MaintenanceRequest updated!"));

    }

    @PutMapping("/mark-request-completed/{maintenanceRequestId}/{expertId}")
    public ApiResponse completeMaintenanceRequest(
            @PathVariable Integer maintenanceRequestId,
            @PathVariable Integer expertId) {

        maintenanceRequestService.updateMaintenanceRequestStatusToCompleted(maintenanceRequestId, expertId);

        return new ApiResponse("Maintenance request marked as completed successfully!");
    }


    @DeleteMapping("/delete/{maintenanceRequest_id}")
    public ResponseEntity deleteMaintenanceRequest(@PathVariable Integer maintenanceRequest_id){

        maintenanceRequestService.deleteMaintenanceRequest(maintenanceRequest_id);
        return ResponseEntity.status(200).body(new ApiResponse("MaintenanceRequest deleted!"));

    }


    @PostMapping("/generate-invoice/{maintenanceRequest_id}")
    public ResponseEntity<Map<String, Object>> generateMaintenanceRequestInvoice(@PathVariable Integer maintenanceRequest_id ) {

        Map<String, Object> invoiceData = maintenanceRequestService.generateMaintenanceRequestInvoice(maintenanceRequest_id);
        return ResponseEntity.ok(invoiceData);
    }




    @PostMapping("/notify-owner-completion/{maintenanceRequest_Id}")
    public ResponseEntity<String> notifyOwnerOnCompletion(@PathVariable Integer maintenanceRequest_Id) {

        maintenanceRequestService.notifyOwnerOnCompletion(maintenanceRequest_Id);

        return ResponseEntity.ok("Notification sent successfully to the owner!");
    }




    @GetMapping("/maintenance-history/{ownerId}")
    public ResponseEntity<List<MaintenanceRequest>> getMaintenanceHistory(@PathVariable Integer ownerId) {
        List<MaintenanceRequest> history = maintenanceRequestService.getMaintenanceHistoryByOwner(ownerId);
        return ResponseEntity.ok(history);
    }


    @PostMapping("/notify-expert/{maintenanceId}")
    public ResponseEntity notifyExpertOfNewRequest(@PathVariable Integer maintenanceId) {
        maintenanceRequestService.notifyExpert(maintenanceId);

        return ResponseEntity.status(200).body(new ApiResponse("Notification sent to expert successfully!"));
    }


    @GetMapping("/upcoming/{expertId}")
    public ResponseEntity<List<MaintenanceRequest>> getUpcomingRequestsByExpert(@PathVariable Integer expertId) {
        LocalDate today = LocalDate.now();  // Get today's date
        List<MaintenanceRequest> requests = maintenanceRequestService.getUpcomingRequestsByExpert(expertId, today);

        return ResponseEntity.ok(requests);
    }

    @GetMapping("/getMaintenanceHistory")
    public ResponseEntity getMaintenanceHistory(){
        return ResponseEntity.status(200).body(maintenanceRequestService.getMaintenanceHistory());
    }




}
