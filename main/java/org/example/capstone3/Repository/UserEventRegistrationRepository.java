package org.example.capstone3.Repository;

import org.example.capstone3.Model.UserEventRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEventRegistrationRepository extends JpaRepository<UserEventRegistration, Integer> {
    UserEventRegistrationRepository findUserEventRegistrationById(Integer id);

}
