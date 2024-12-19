package org.example.capstone3.Repository;

import org.example.capstone3.Model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {

    Owner findOwnerById(Integer id);

    Owner findOwnerByMotorcyclesId(Integer id);


}
