package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiException;
import org.example.capstone3.Model.MaintenanceExpert;
import org.example.capstone3.Model.MaintenanceRequest;
import org.example.capstone3.Model.Motorcycle;
import org.example.capstone3.Model.Owner;
import org.example.capstone3.OutDTO.MaintenanceRequestHistoryDTO;
import org.example.capstone3.OutDTO.MaintenanceRequestOutDTO;
import org.example.capstone3.OutDTO.MotorcycleOutDTO;
import org.example.capstone3.Repository.MaintenanceExpertRepository;
import org.example.capstone3.Repository.MaintenanceRequestRepository;
import org.example.capstone3.Repository.MotorcycleRepository;
import org.example.capstone3.Repository.OwnerRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaintenanceRequestService {

    private final MaintenanceRequestRepository maintenanceRequestRepository;
    private final OwnerRepository ownerRepository;
    private final MotorcycleRepository motorcycleRepository;
    private final MaintenanceExpertRepository maintenanceExpertRepository;
    private final EmailService emailService;



    public List<MaintenanceRequestOutDTO> getAllMaintenanceRequest() {
        // Step 1: Fetch all maintenance requests
        List<MaintenanceRequest> maintenanceRequests = maintenanceRequestRepository.findAll();

        // Step 2: Map each maintenance request to a DTO with its motorcycle details
        return maintenanceRequests.stream().map(maintenanceRequest -> {
            // Fetch motorcycle details
            Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(maintenanceRequest.getMotorcycle_id());
            if (motorcycle == null) {
                throw new ApiException("Motorcycle not found for MaintenanceRequest ID " + maintenanceRequest.getId());
            }

            // Map motorcycle to MotorcycleDTO
            MotorcycleOutDTO motorcycleDTO = new MotorcycleOutDTO(
                    motorcycle.getBrand(),
                    motorcycle.getModel(),
                    motorcycle.getYear(),
                    motorcycle.getPrice(),
                    motorcycle.getColor(),
                    motorcycle.getIsForSale(),
                    motorcycle.getIsAvailable(),
                    motorcycle.getHasOffer()
            );

            // Map MaintenanceRequest to MaintenanceRequestDTO
            return new MaintenanceRequestOutDTO(
                    maintenanceRequest.getRequestDate(),
                    maintenanceRequest.getTotalPrice(),
                    maintenanceRequest.getExpert().getName(),
                    maintenanceRequest.getStatus(),
                    maintenanceRequest.getPickupDate(),
                    motorcycleDTO
            );
        }).collect(Collectors.toList());

    }

    //Durrah
    public void addMaintenanceRequest(Integer ownerId, Integer expertId, MaintenanceRequest maintenanceRequest){
        MaintenanceExpert maintenanceExpert = maintenanceExpertRepository.findMaintenanceExpertById(expertId);

        if (maintenanceExpert == null || !maintenanceExpert.getIsApproved()) {
            throw new ApiException("Expert not found or not approved!");
        }

        // Find the owner
        Owner owner = ownerRepository.findOwnerById(ownerId);
        if (owner == null) {
            throw new ApiException("Owner not found!");
        }

        // Validate the pickup date
        LocalDate pickupDate = maintenanceRequest.getPickupDate();
        if (pickupDate.isBefore(LocalDate.now())) {
            throw new ApiException("Pickup date cannot be in the past!");
        }

        // Calculate total price
        Double totalPrice = calculateTotalPrice(maintenanceExpert, pickupDate);

        // Set fields on the maintenance request
        maintenanceRequest.setStatus("Pending");
        maintenanceRequest.setTotalPrice(totalPrice);
        maintenanceRequest.setExpert(maintenanceExpert);
        maintenanceRequest.setOwner(owner);

        // Save the maintenance request
        maintenanceRequestRepository.save(maintenanceRequest);


    }


    //method to calculate total price
    private Double calculateTotalPrice(MaintenanceExpert expert, LocalDate pickupDate) {
        // calc price based on expert daily rate and number of days
        Double numberOfDays = (double) Duration.between(LocalDate.now().atStartOfDay(), pickupDate.atStartOfDay()).toDays();

        // Calculate total price as the daily rate times the number of days
        return expert.getMaintenancePrice() * numberOfDays;
    }


   //Durrah
   public void updateMaintenanceRequest(Integer maintenanceRequest_id, MaintenanceRequest maintenanceRequest) {
       MaintenanceRequest existingRequest = maintenanceRequestRepository.findMaintenanceRequestById(maintenanceRequest_id);

       if (existingRequest == null) {
           throw new ApiException("MaintenanceRequest not found!");
       }

       // Update the fields of the existing request
       if (maintenanceRequest.getExpert_name() != null) {
           existingRequest.setExpert_name(maintenanceRequest.getExpert_name());
       }

       if (maintenanceRequest.getPickupDate() != null) {
           existingRequest.setPickupDate(maintenanceRequest.getPickupDate());
       }

       if (maintenanceRequest.getOwner() != null) {
           Owner owner = ownerRepository.findOwnerById(maintenanceRequest.getOwner().getId());
           if (owner == null) {
               throw new ApiException("Owner not found!");
           }
           existingRequest.setOwner(owner);
       }

       if (maintenanceRequest.getMotorcycle_id() != null) {
           existingRequest.setMotorcycle_id(maintenanceRequest.getMotorcycle_id());
       }

       // Recalculate the total price if the expert or pickupDate was updated
       if (maintenanceRequest.getExpert_name() != null || maintenanceRequest.getPickupDate() != null) {
           MaintenanceExpert expert = maintenanceExpertRepository.findMaintenanceExpertByName(maintenanceRequest.getExpert_name());
           if (expert == null || !expert.getIsApproved()) {
               throw new ApiException("Expert not found or not approved!");
           }
           Double newTotalPrice = calculateTotalPrice(expert, maintenanceRequest.getPickupDate());
           existingRequest.setTotalPrice(newTotalPrice);
       }

       // Save the updated request
       maintenanceRequestRepository.save(existingRequest);
   }



    //Durrah

    public void updateMaintenanceRequestStatusToCompleted(Integer maintenanceRequest_id, Integer expertId) {
        MaintenanceRequest maintenanceRequest = maintenanceRequestRepository.findMaintenanceRequestById(maintenanceRequest_id);

        if (maintenanceRequest == null) {
            throw new ApiException("MaintenanceRequest not found!");
        }

        // Check if the current expert ID is the one assigned to the request
        if (maintenanceRequest.getExpert() == null || !maintenanceRequest.getExpert().getId().equals(expertId)) {
            throw new ApiException("Only the assigned expert can mark the maintenance request as completed!");
        }

        // Only allow the status to be updated if the request is in 'Pending' status
        if (!"Pending".equalsIgnoreCase(maintenanceRequest.getStatus())) {
            throw new ApiException("Maintenance request is not in a Pending status, it cannot be marked as completed!");
        }

        // Update status
        maintenanceRequest.setStatus("Completed");
        maintenanceRequestRepository.save(maintenanceRequest);
    }



    public void deleteMaintenanceRequest(Integer maintenanceRequest_id ){

        MaintenanceRequest maintenanceRequest = maintenanceRequestRepository.findMaintenanceRequestById(maintenanceRequest_id);

        if(maintenanceRequest == null)
            throw new ApiException("MaintenanceRequest not found!");

        // Check if the pickupDate is after the current date (meaning the expert has completed their work)
        if (maintenanceRequest.getPickupDate() != null && maintenanceRequest.getPickupDate().isAfter(LocalDate.now())) {
            throw new ApiException("Cannot delete this Maintenance Request !");
        }

        maintenanceRequestRepository.delete(maintenanceRequest);

    }



    //Durrah
    public Map<String, Object> generateMaintenanceRequestInvoice(Integer maintenanceRequest_id) {
        // Retrieve the maintenance request by ID
        MaintenanceRequest request = maintenanceRequestRepository.findMaintenanceRequestById(maintenanceRequest_id);
        if (request == null) {
            throw new ApiException("Request not found!");
        }

        // Check if the request's status is "Completed"
        if (!"Completed".equalsIgnoreCase(request.getStatus())) {
            throw new ApiException("Cannot generate invoice for pending requests!"); // Only allow invoice generation for completed requests
        }



        Map<String, Object> invoiceData = new HashMap<>();
        invoiceData.put("Request Id", request.getId());
        invoiceData.put("Owner Name", request.getOwner().getName());
        invoiceData.put("Owner Email", request.getOwner().getEmail());
        invoiceData.put("Total Price", request.getTotalPrice());
        invoiceData.put("Date Generated", LocalDate.now());
        invoiceData.put("Request Status", "Completed");

        return invoiceData;
    }



    //Durrah
    public void notifyOwnerOnCompletion(Integer maintenanceRequest_Id) {
        MaintenanceRequest request = maintenanceRequestRepository.findMaintenanceRequestById(maintenanceRequest_Id);

        if (request == null) {
            throw new RuntimeException("Maintenance Request not found!");
        }

        if (!"Completed".equalsIgnoreCase(request.getStatus())) {
            throw new RuntimeException("Request is not completed yet!");
        }

        // Retrieve the owner's email
        Owner owner = request.getOwner();
        String ownerEmail = owner.getEmail();

        // build the email message
        String subject = "Your Maintenance Request is Completed!";
        String text = "Dear " + owner.getName() + ",\n\n" +
                "Your maintenance request for motorcycle: " + request.getMotorcycle_id() + " has been completed successfully and ready for pickup.\n\n" +
                "Thank you for using our services!";

        // Send the email notification
        emailService.sendEmail(ownerEmail, subject, text);
    }



    //Durrah
    public List<MaintenanceRequest> getMaintenanceHistoryByOwner(Integer ownerId) {
        Owner owner = ownerRepository.findOwnerById(ownerId);
        if(owner==null)
                throw  new ApiException("Owner not found!");

        return maintenanceRequestRepository.findByOwner(owner);
    }


    //Durrah
    public void notifyExpert(Integer maintenanceId) {

        MaintenanceRequest request = maintenanceRequestRepository.findMaintenanceRequestById(maintenanceId);
        if (request == null) {
            throw new ApiException("Maintenance request not found!");
        }

        if (!request.getStatus().equalsIgnoreCase("Pending")) {
            throw new ApiException("Can not send notification for completed requests!!");
        }

        MaintenanceExpert expert = maintenanceExpertRepository.findMaintenanceExpertByName(request.getExpert_name());
        if (expert == null) {
            throw new ApiException("Expert not found.");
        }


        String subject = "New Maintenance Request Assigned!";
        String text = String.format("Dear %s,\n\nYou have been assigned a new maintenance request for motorcycle ID: %d.\n\nPlease review the request.\n\nBest regards,\nMaintenance System",
                expert.getName(), request.getMotorcycle_id());

        // Sending the email to the expert
        emailService.sendEmail(expert.getEmail(), subject, text);

    }


    //Durrah
    public List<MaintenanceRequest> getUpcomingRequestsByExpert(Integer expertId, LocalDate today) {
        return maintenanceRequestRepository.findUpcomingRequestsByExpertId(expertId, today);
    }


    public List<MaintenanceRequestHistoryDTO> getMaintenanceHistory() {
        // Step 1: Fetch all maintenance requests
        List<MaintenanceRequest> maintenanceRequests = maintenanceRequestRepository.findAll();

        // Step 2: Map MaintenanceRequest to MaintenanceRequestDTO
        return maintenanceRequests.stream().map(request -> {
            // Fetch motorcycle information based on motorcycle_id
            Motorcycle motorcycle = motorcycleRepository.findById(request.getMotorcycle_id())
                    .orElseThrow(() -> new ApiException("Motorcycle not found for maintenance request"));

            // Map motorcycle to DTO
            MotorcycleOutDTO motorcycleDTO = new MotorcycleOutDTO(
                    motorcycle.getBrand(),
                    motorcycle.getModel(),
                    motorcycle.getYear(),
                    motorcycle.getPrice(),
                    motorcycle.getColor(),
                    motorcycle.getIsAvailable(),
                    motorcycle.getIsForSale(),
                    motorcycle.getHasOffer()
            );

            // Map maintenance request to DTO
            MaintenanceRequestHistoryDTO dto = new MaintenanceRequestHistoryDTO();
            dto.setRequestDate(request.getRequestDate());
            dto.setTotalPrice(request.getTotalPrice());
            dto.setExpertName(request.getExpert_name());
            dto.setStatus(request.getStatus());
            dto.setPickupDate(request.getPickupDate());
            dto.setMotorcycleS(List.of(motorcycleDTO)); // Add the motorcycle information

            return dto;
        }).collect(Collectors.toList());
    }

















}
