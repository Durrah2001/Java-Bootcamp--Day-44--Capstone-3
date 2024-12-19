package org.example.capstone3.Controller;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiResponse;
import org.example.capstone3.Service.UserEventRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-event-registration")
@RequiredArgsConstructor
public class UserEventRegistrationController {



    private final UserEventRegistrationService userEventRegistrationService;

    @GetMapping("/get")
    public ResponseEntity getAllUserEventRegistrations() {
        return ResponseEntity.status(200).body(userEventRegistrationService.getAllUserEventRegistrations());
    }

    @PostMapping("/user-registration/{userId}/{event_id}")
    public ResponseEntity userRegistration(@PathVariable Integer userId, @PathVariable Integer event_id) {
        userEventRegistrationService.UserRegistration(userId,event_id);
        return ResponseEntity.status(200).body(new ApiResponse("user registration"));
    }

    @PostMapping("/send-reminder")
    public ResponseEntity userReminder() {
        userEventRegistrationService.sendReminders();
        return ResponseEntity.status(200).body(new ApiResponse("Reminder send to user!"));
    }







}
