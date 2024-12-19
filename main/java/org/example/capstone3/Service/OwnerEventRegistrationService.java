package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiException;
import org.example.capstone3.Model.Event;
import org.example.capstone3.Model.Motorcycle;
import org.example.capstone3.Model.Owner;
import org.example.capstone3.Model.OwnerEventRegistration;
import org.example.capstone3.OutDTO.OwnerEventRegistrationOutDTO;
import org.example.capstone3.Repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnerEventRegistrationService {


    private final OwnerEventRegistrationRepository ownerEventRegistrationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final OwnerRepository ownerRepository;
    private final MotorcycleRepository motorcycleRepository;

    public List<OwnerEventRegistrationOutDTO> getAllOwnerEventRegistrations() {
        List<OwnerEventRegistration> ownerEventRegistrations = ownerEventRegistrationRepository.findAll();
        List<OwnerEventRegistrationOutDTO> ownerEventRegistrationOutDTOs = new ArrayList<>();
        for (OwnerEventRegistration ownerEventRegistration : ownerEventRegistrations) {
            ownerEventRegistrationOutDTOs.add(new OwnerEventRegistrationOutDTO(ownerEventRegistration.getStatus(),ownerEventRegistration.getEvent().getId(),ownerEventRegistration.getOwner().getId(),ownerEventRegistration.getMotorcycle().getId()));
        }
        return ownerEventRegistrationOutDTOs;
    }


    public void OwnerRegistration(Integer owner_id,Integer event_id,Integer motorcycle_id) {
        Event event = eventRepository.findEventById(event_id);
        Owner owner = ownerRepository.findOwnerById(owner_id);
        Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(motorcycle_id);
        if (event == null || owner == null || motorcycle == null) {
            throw new ApiException("event or owner or motorcycle not found");
        }
        OwnerEventRegistration ownerEventRegistration = new OwnerEventRegistration("Owner",event ,owner,motorcycle);
        ownerEventRegistrationRepository.save(ownerEventRegistration);

    }










}
