package org.example.capstone3.Repository;

import org.example.capstone3.Model.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FineRepository extends JpaRepository<Fine, Integer> {

    List<Fine> findFineByUserId(Integer userId);

    @Query("SELECT COUNT(f) FROM Fine f WHERE f.user.id = :userId")
    long countFinesByUserId(@Param("userId") Integer userId);

    @Query("SELECT f FROM Fine f WHERE f.user.id = :userId AND f.isPaid = false")
    List<Fine> findUnpaidFinesByUserId(@Param("userId") Integer userId);
}
