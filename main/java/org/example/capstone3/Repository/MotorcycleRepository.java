package org.example.capstone3.Repository;

import org.example.capstone3.Model.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotorcycleRepository extends JpaRepository<Motorcycle, Integer> {

    Motorcycle findMotorcycleById(Integer id);

    List<Motorcycle> findMotorcycleByOwnerId(Integer owenrId);


    // Find motorcycles by price range
    List<Motorcycle> findByPriceBetween(Double minPrice, Double maxPrice);

    // Find motorcycles by minimum price
    List<Motorcycle> findByPriceGreaterThanEqual(Double minPrice);

    // Find motorcycles by maximum price
    List<Motorcycle> findByPriceLessThanEqual(Double maxPrice);

    List<Motorcycle> findMotorcycleByIsAvailable(Boolean isAvailable);

    List<Motorcycle> findMotorcycleByIsForSale(Boolean isForSale);

    List<Motorcycle> findMotorcycleByBrandAndYear(String brand, Integer year);
    List<Motorcycle> findMotorcycleByBrandAndModel(String brand, String model);
}
