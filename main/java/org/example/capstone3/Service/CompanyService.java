package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiException;
import org.example.capstone3.Model.Company;
import org.example.capstone3.OutDTO.CompanyOutDTO;
import org.example.capstone3.Repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;


    public List<CompanyOutDTO> getCompanies(){
        List<Company> companies = companyRepository.findAll();
        List<CompanyOutDTO> companyDTOS= new ArrayList<>();

        for(Company company : companies){
            CompanyOutDTO companyDTO = new CompanyOutDTO(company.getName(),company.getEmail(),company.getContactInfo(), company.getIsApproved());
            companyDTOS.add(companyDTO);
        }
        return companyDTOS;
    }



    public void addCompany(Company company){
        companyRepository.save(company);
    }


    public void updateCompany(Integer id,Company company){
        Company oldCompany=companyRepository.findCompanyById(id);
        if(oldCompany==null){
            throw new ApiException("Company not found");
        }
        oldCompany.setName(company.getName());
        oldCompany.setEmail(company.getEmail());
        oldCompany.setPassword(company.getPassword());
        oldCompany.setContactInfo(company.getContactInfo());
        oldCompany.setIsApproved(company.getIsApproved());

        companyRepository.save(oldCompany);

    }


    public void deleteCompany(Integer id){
        Company company=companyRepository.findCompanyById(id);
        if(company==null){
            throw new ApiException("Company not found");
        }
        companyRepository.delete(company);
    }


}
