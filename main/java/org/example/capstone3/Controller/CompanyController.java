package org.example.capstone3.Controller;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiResponse;
import org.example.capstone3.Model.Company;
import org.example.capstone3.Service.CompanyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/motorcycle-system/company")

public class CompanyController {

    private final CompanyService companyService;


    @GetMapping("/get")
    public ResponseEntity getCompanies(){
        return ResponseEntity.status(200).body(companyService.getCompanies());
    }

    @PostMapping("/add")
    public ResponseEntity addCompany(@RequestBody @Valid Company company){
        companyService.addCompany(company);
        return ResponseEntity.status(200).body(new ApiResponse("Company added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateCompany(@PathVariable Integer id, @RequestBody @Valid Company company){
        companyService.updateCompany(id,company);
        return ResponseEntity.status(200).body(new ApiResponse("Company updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCompany(@PathVariable Integer id){
        companyService.deleteCompany(id);
        return ResponseEntity.status(200).body(new ApiResponse("Company deleted"));
    }
}
