package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiResponse;
import org.example.capstone3.Model.Admin;
import org.example.capstone3.Service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/motorcycle-system/admin")
@RequiredArgsConstructor
public class AdminController {


    private final AdminService adminService;

    @GetMapping("/get")
    public ResponseEntity getAllAdmins(){
        return ResponseEntity.status(200).body(adminService.getAllAdmins());
    }

    @PostMapping("/add")
    public ResponseEntity addAdmin(@RequestBody @Valid Admin admin){
        adminService.addAdmin(admin);
        return ResponseEntity.status(200).body(new ApiResponse("Admin added"));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity updateAdmin(@PathVariable Integer id ,@RequestBody @Valid Admin admin){
        adminService.updateAdmin(id,admin);
        return ResponseEntity.status(200).body(new ApiResponse("Admin updated"));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAdmin(@PathVariable Integer id){
        adminService.deleteAdmin(id);
        return ResponseEntity.status(200).body(new ApiResponse("Admin deleted"));

    }


    @PutMapping("/approveCompany/{adminId}/{companyId}")
    public ResponseEntity approveCompany(@PathVariable Integer adminId,@PathVariable Integer companyId){
        adminService.approveCompany(adminId,companyId);
        return ResponseEntity.status(200).body(new ApiResponse("Company approved"));
    }

    @PutMapping("/approveExpert/{adminId}/{expertId}")
    public ResponseEntity approveExpert(@PathVariable Integer adminId,@PathVariable Integer expertId){
        adminService.approveExpert(adminId,expertId);
        return ResponseEntity.status(200).body(new ApiResponse("Expert approved"));
    }







}
