package org.example.capstone3.Repository;

import org.example.capstone3.Model.OwnerEventRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerEventRegistrationRepository extends JpaRepository<OwnerEventRegistration, Integer> {
    OwnerEventRegistrationRepository findOwnerEventRegistrationById(Integer id);

}
