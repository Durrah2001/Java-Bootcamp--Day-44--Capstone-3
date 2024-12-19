//package org.example.capstone3.Controller;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.example.capstone3.ApiResponse.ApiResponse;
//import org.example.capstone3.Model.EventRegistration;
//import org.example.capstone3.Service.EventRegistrationService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/v1/motorcycle-system/event-registration")
//@RequiredArgsConstructor
//public class EventRegistrationController {
//
//    private final EventRegistrationService eventRegistrationService;
//
//    @GetMapping("/get")
//    public ResponseEntity getEventRegistrations() {
//        return ResponseEntity.status(200).body(eventRegistrationService.getEventRegistrations());
//    }
//
//    @PostMapping("/user-registration/{user_id}/{event_id}")
//    public ResponseEntity userRegistration(@PathVariable Integer user_id,@PathVariable Integer event_id){
//        eventRegistrationService.UserRegistration(user_id,event_id);
//        return ResponseEntity.status(200).body(new ApiResponse("User Registration"));
//    }
//
//    @PostMapping("/owner-registration/{owner_id}/{event_id}/{motorcycle_id}")
//    public ResponseEntity ownerRegistration(@PathVariable Integer owner_id,@PathVariable Integer event_id,@PathVariable Integer motorcycle_id) {
//        eventRegistrationService.OwnerRegistration(owner_id,event_id,motorcycle_id);
//        return ResponseEntity.status(200).body(new ApiResponse("Owner Registration"));
//    }
//
//
//}
