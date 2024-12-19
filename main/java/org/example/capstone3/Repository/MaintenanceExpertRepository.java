package org.example.capstone3.Repository;

import org.example.capstone3.Model.MaintenanceExpert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceExpertRepository extends JpaRepository<MaintenanceExpert, Integer> {
    MaintenanceExpert findMaintenanceExpertById(Integer id);

    MaintenanceExpert findMaintenanceExpertByName(String name);
}
