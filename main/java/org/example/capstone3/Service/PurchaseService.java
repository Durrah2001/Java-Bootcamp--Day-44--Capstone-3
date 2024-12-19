package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiException;
import org.example.capstone3.InDTO.PurchaseDTO_In;
import org.example.capstone3.Model.Motorcycle;
import org.example.capstone3.Model.Owner;
import org.example.capstone3.Model.Purchase;
import org.example.capstone3.Model.User;
import org.example.capstone3.OutDTO.PurchaseOutDTO;
import org.example.capstone3.OutDTO.PurchaseMotorcycleOutDTO;
import org.example.capstone3.OutDTO.PurchaseUserOutDTO;
import org.example.capstone3.Repository.MotorcycleRepository;
import org.example.capstone3.Repository.OwnerRepository;
import org.example.capstone3.Repository.PurchaseRepository;
import org.example.capstone3.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PurchaseService {


    private final PurchaseRepository purchaseRepository;
    private final MotorcycleRepository motorcycleRepository;
    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;

    public List<PurchaseOutDTO> getAllPurchases() {
        List<Purchase> purchases = purchaseRepository.findAll();
        List<PurchaseOutDTO> purchaseOutDTOs = new ArrayList<>();
        for (Purchase purchase : purchases) {
            Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(purchase.getMotorcycleId());
            purchaseOutDTOs.add(new PurchaseOutDTO(purchase.getPurchaseDate(),new PurchaseUserOutDTO(purchase.getUser().getName(),purchase.getUser().getEmail(),purchase.getUser().getPhoneNumber(),purchase.getUser().getAge(),purchase.getUser().getAddress()),new PurchaseMotorcycleOutDTO(motorcycle.getBrand(),motorcycle.getModel(),motorcycle.getYear(),motorcycle.getPrice(),motorcycle.getColor(),motorcycle.getIsAvailable())));
        }
        return purchaseOutDTOs;
    }

    public void addPurchase(PurchaseDTO_In purchaseInDTO) {
        Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(purchaseInDTO.getMotorcycleId());
        if (motorcycle == null) {
            throw new ApiException("Motorcycle not found");
        }
        User user = userRepository.findUserById(purchaseInDTO.getUserId());
        if (user == null) {
            throw new ApiException("User not found");
        }
        Owner owner = ownerRepository.findOwnerById(purchaseInDTO.getOwnerId());
        if (owner == null) {
            throw new ApiException("Owner not found");
        }
        if (motorcycle.getIsForSale() == true && motorcycle.getIsAvailable() == true) {
            Purchase purchase = new Purchase(user, motorcycle.getId());
            purchase.setOwner(owner);
            purchaseRepository.save(purchase);

            motorcycle.setIsAvailable(false);
            motorcycle.setIsForSale(false);
            motorcycle.setOwner(null);
            Set<Motorcycle> motorcycles = owner.getMotorcycles();

            if (motorcycles.contains(motorcycle)) {
                motorcycles.remove(motorcycle);
            }else{
                throw new ApiException("Motorcycle not found");
            }

            ownerRepository.save(owner);
            motorcycleRepository.save(motorcycle);

        }else{
            throw new ApiException("This motorcycle not for sale");
        }


    }




}
