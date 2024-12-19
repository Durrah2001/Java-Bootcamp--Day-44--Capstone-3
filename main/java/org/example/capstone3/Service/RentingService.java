package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiException;
import org.example.capstone3.Model.Motorcycle;
import org.example.capstone3.Model.Owner;
import org.example.capstone3.Model.Renting;
import org.example.capstone3.OutDTO.MotorcycleOutDTO;
import org.example.capstone3.OutDTO.RentingOutDTO;
import org.example.capstone3.Repository.MotorcycleRepository;
import org.example.capstone3.Repository.OwnerRepository;
import org.example.capstone3.Repository.RentingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentingService {

    private final RentingRepository rentingRepository;
    private final OwnerRepository ownerRepository;
    private final MotorcycleService motorcycleService;
    private final MotorcycleRepository motorcycleRepository;

    public List<RentingOutDTO> getAllRentings(){

        List<Renting> rentings = rentingRepository.findAll();

        List<RentingOutDTO> rentingDTOS = new ArrayList<>();


        for(Renting renting : rentings){
            Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(renting.getMotorcycle_id());
            MotorcycleOutDTO motorcycleDTO = new MotorcycleOutDTO(motorcycle.getBrand(),motorcycle.getModel(),motorcycle.getYear(),motorcycle.getPrice(),motorcycle.getColor(),motorcycle.getIsAvailable(),motorcycle.getIsForSale(),motorcycle.getHasOffer());
            RentingOutDTO rentingDTO = new RentingOutDTO(renting.getPricePerDay(), renting.getPickupLocation(), renting.getDropOffLocation(), motorcycleDTO);

            rentingDTOS.add(rentingDTO);
        }
        return rentingDTOS;
    }

    public void addRenting(Integer owner_id, Renting renting) {

        Owner owner = ownerRepository.findOwnerById(owner_id);

        if (owner == null)
            throw new ApiException("Owner not found!");

        Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(renting.getMotorcycle_id());
        if(!owner.getMotorcycles().contains(motorcycle)){
            throw new ApiException("Owner didn't have this motorcycle");
        }

        renting.setOwner(owner);
        rentingRepository.save(renting);
    }


    public void updateRenting(Integer id, Renting renting) {

        Renting r = rentingRepository.findRentingById(id);

        if (r == null)
            throw new ApiException("Renting not found!");

        r.setPricePerDay(renting.getPricePerDay());
        r.setPickupLocation(renting.getPickupLocation());
        r.setDropOffLocation(renting.getDropOffLocation());
        rentingRepository.save(r);
    }

    public void deleteRenting(Integer id){

        Renting renting = rentingRepository.findRentingById(id);
        if(renting == null)
            throw new ApiException("Renting not found!");

        rentingRepository.delete(renting);

    }








}
