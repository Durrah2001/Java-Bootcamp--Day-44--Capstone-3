package org.example.capstone3.Repository;

import org.example.capstone3.Model.Company;
import org.example.capstone3.Service.CompanyService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Company findCompanyById(Integer id);
}
