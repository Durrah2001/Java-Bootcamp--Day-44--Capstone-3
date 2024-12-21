package org.example.capstone3.Repository;

import org.example.capstone3.Model.MaintenanceRequest;
import org.example.capstone3.Model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, Integer> {


    MaintenanceRequest findMaintenanceRequestById(Integer id);


    List<MaintenanceRequest> findByOwner(Owner owner);

    List<MaintenanceRequest> findMaintenanceRequestByStatus(String status);

    @Query("SELECT m FROM MaintenanceRequest m WHERE m.expert.id = :id AND m.pickupDate > :today And m.status = 'Pending'")
    List<MaintenanceRequest> findUpcomingRequestsByExpertId(Integer id,  LocalDate today);

}
