package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiException;
import org.example.capstone3.Model.Company;
import org.example.capstone3.Model.Motorcycle;
import org.example.capstone3.Model.Owner;
import org.example.capstone3.OutDTO.MotorcycleOutDTO;
import org.example.capstone3.Repository.CompanyRepository;
import org.example.capstone3.Repository.MotorcycleRepository;
import org.example.capstone3.Repository.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MotorcycleService {

    private final MotorcycleRepository motorcycleRepository;
    private final OwnerRepository ownerRepository;
    private final CompanyRepository companyRepository;

    public List<MotorcycleOutDTO> getAllMotorcycles(){

        List<Motorcycle> motorcycles = motorcycleRepository.findAll();

        List<MotorcycleOutDTO> motorcycleDTOS = new ArrayList<>();

        for(Motorcycle motorcycle : motorcycles){

            MotorcycleOutDTO motorcycleDTO = new MotorcycleOutDTO(motorcycle.getBrand(), motorcycle.getModel(), motorcycle.getYear(), motorcycle.getPrice(), motorcycle.getColor(), motorcycle.getIsAvailable(), motorcycle.getIsForSale(),motorcycle.getHasOffer());

            motorcycleDTOS.add(motorcycleDTO);
        }

        return motorcycleDTOS;
    }


    public void addMotorcycleByOwner(Integer owner_id, Motorcycle motorcycle) {

        Owner owner = ownerRepository.findOwnerById(owner_id);

        if (owner == null)
            throw new ApiException("Owner not found!");

        //assign motorcycle to one owner
        motorcycle.setOwner(owner);
        motorcycleRepository.save(motorcycle);
    }

    public void addMotorcycleByCompany(Integer company_id, Motorcycle motorcycle) {

        Company company = companyRepository.findCompanyById(company_id);

        if (company == null)
            throw new ApiException("Company not found!");

        //assign motorcycle to one company
        motorcycle.setCompany(company);
        motorcycleRepository.save(motorcycle);
    }


    public void updateMotorcycle(Integer id, Motorcycle motorcycle) {

        Motorcycle m = motorcycleRepository.findMotorcycleById(id);
        if (m == null)
            throw new ApiException("Motorcycle not found!");

        m.setBrand(motorcycle.getBrand());
        m.setModel(motorcycle.getModel());
        m.setPrice(motorcycle.getPrice());
        m.setColor(motorcycle.getColor());
        m.setYear(motorcycle.getYear());
        m.setIsAvailable(motorcycle.getIsAvailable());
        m.setIsForSale(motorcycle.getIsForSale());
        m.setHasOffer(motorcycle.getHasOffer());

        motorcycleRepository.save(m);
    }

    public void deleteMotorcycle(Integer id){

        Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(id);
        if(motorcycle == null)
            throw new ApiException("Motorcycle not found!");

        motorcycleRepository.delete(motorcycle);

    }


    ////


    //Durrah
    public List<Motorcycle> filterByPrice(Double minPrice, Double maxPrice) {
        if (minPrice == null && maxPrice == null) {
            return motorcycleRepository.findAll(); // No filter, return all motorcycles
        }
        if (minPrice != null && maxPrice != null) {
            return motorcycleRepository.findByPriceBetween(minPrice, maxPrice);
        }
        if (minPrice != null) {
            return motorcycleRepository.findByPriceGreaterThanEqual(minPrice);
        }
        if (maxPrice != null) {
            return motorcycleRepository.findByPriceLessThanEqual(maxPrice);
        }
        return motorcycleRepository.findAll();
    }

    public List<Motorcycle> getAvailableMotorcycles(){
        List<Motorcycle> availableMotorcycles = motorcycleRepository.findMotorcycleByIsAvailable(true);
        return availableMotorcycles;
    }

    public List<Motorcycle> getMotorcyclesForSale(){
        List<Motorcycle> MotorcyclesForSale = motorcycleRepository.findMotorcycleByIsForSale(true);
        return MotorcyclesForSale;
    }

    public void changeForSaleStatus(Integer id,Integer owner_id,Boolean forSale,Double price){
        Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(id);
        if (motorcycle == null){
            throw new ApiException("Motorcycle not found!");
        }

        Owner owner = ownerRepository.findOwnerById(owner_id);
        if (owner == null){
            throw new ApiException("Owner not found");
        }
        if (owner.getMotorcycles().contains(motorcycle)) {
            motorcycle.setPrice(price);
            motorcycle.setIsForSale(forSale);
            motorcycleRepository.save(motorcycle);
        }else {
            throw new ApiException("The owner does not own this Motorcycle");
        }
    }


    public void changeAvailableStatus(Integer id,Integer owner_id,Boolean Available){
        Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(id);
        if (motorcycle == null){
            throw new ApiException("Motorcycle not found!");
        }
        Owner owner = ownerRepository.findOwnerById(owner_id);
        if (owner == null){
            throw new ApiException("Owner not found");
        }
        if (owner.getMotorcycles().contains(motorcycle)) {
            motorcycle.setIsAvailable(Available);
            motorcycleRepository.save(motorcycle);
        }else {
            throw new ApiException("The owner does not own this Motorcycle");
        }
    }


    public void discountMotorcycle(Integer motorcycle_id, Integer owner_id,Double discountRate){
        Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(motorcycle_id);
        if (motorcycle == null){
            throw new ApiException("Motorcycle not found!");
        }
        Owner owner = ownerRepository.findOwnerById(owner_id);
        Owner ownerMotorcycle = ownerRepository.findOwnerByMotorcyclesId(motorcycle.getId());
        if (owner == null ){
            throw new ApiException("Owner not found!");
        }
        ArrayList<Double> allowedDiscounts = new ArrayList<>();
        allowedDiscounts.add(20.0);
        allowedDiscounts.add(35.0);
        allowedDiscounts.add(50.0);
        if (motorcycle.getIsForSale()){
            if (motorcycle.getIsAvailable()){
                if(allowedDiscounts.contains(discountRate)){
                    double newPrice = motorcycle.getPrice() * (1 - discountRate / 100);
                    motorcycle.setPrice(newPrice);
                    motorcycle.setHasOffer(true);
                    motorcycleRepository.save(motorcycle);
                }else {
                    throw new ApiException("Not valid discount rate!");
                }
            }else {
                throw new ApiException("Motorcycle not Available!");
            }
        }else {
            throw new ApiException("Motorcycle not ForSale!");
        }

    }

    public Double CalculateAveragePriceForSameBrandAndYear(String brand,Integer year){
        List<Motorcycle> SameBrandAndYear = motorcycleRepository.findMotorcycleByBrandAndYear(brand,year);
        List<Motorcycle> forSale = new ArrayList<>();

        Double price =0.0;
        for (Motorcycle motorcycle : SameBrandAndYear){
            if (motorcycle.getIsForSale()){
                forSale.add(motorcycle);
                price += motorcycle.getPrice();
            }
        }
        return price/forSale.size();
    }



    public List<MotorcycleOutDTO> byBrandAndModel(String brand,String model){
        List<Motorcycle> BrandAndModel = motorcycleRepository.findMotorcycleByBrandAndModel(brand,model);
        List<MotorcycleOutDTO> motorcycleOutDTOS = new ArrayList<>();

        for(Motorcycle motorcycle : BrandAndModel){

            MotorcycleOutDTO motorcycleOutDTO = new MotorcycleOutDTO(motorcycle.getBrand(), motorcycle.getModel(), motorcycle.getYear(), motorcycle.getPrice(), motorcycle.getColor(), motorcycle.getIsAvailable(),motorcycle.getIsForSale(),motorcycle.getHasOffer());

            motorcycleOutDTOS.add(motorcycleOutDTO);
        }
        return motorcycleOutDTOS;
    }









}
