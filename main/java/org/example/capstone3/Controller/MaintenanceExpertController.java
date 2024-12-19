package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiResponse;
import org.example.capstone3.Model.MaintenanceExpert;
import org.example.capstone3.Service.MaintenanceExpertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/motorcycle-system/maintenance-expert")

public class MaintenanceExpertController {


    private final MaintenanceExpertService maintenanceExpertService;

    @GetMapping("/get")
    public ResponseEntity getMaintenanceExpert(){
        return ResponseEntity.status(200).body(maintenanceExpertService.getMaintenanceExpert());
    }

    @PostMapping("/add")
    public ResponseEntity addMaintenanceExpert(@RequestBody @Valid MaintenanceExpert maintenanceExpert){
        maintenanceExpertService.addMaintenanceExpert(maintenanceExpert);
        return ResponseEntity.status(200).body(new ApiResponse("Maintenance Expert added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateMaintenanceExpert(@PathVariable Integer id,@RequestBody @Valid MaintenanceExpert maintenanceExpert){
        maintenanceExpertService.updateMaintenanceExpert(id,maintenanceExpert);
        return ResponseEntity.status(200).body(new ApiResponse("Maintenance Expert updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMaintenanceExpert(@PathVariable Integer id){
        maintenanceExpertService.deleteMaintenanceExpert(id);
        return ResponseEntity.status(200).body(new ApiResponse("Maintenance Expert Deleted"));
    }












}
