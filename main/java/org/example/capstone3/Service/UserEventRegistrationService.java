package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiException;
import org.example.capstone3.Model.Event;
import org.example.capstone3.Model.User;
import org.example.capstone3.Model.UserEventRegistration;
import org.example.capstone3.OutDTO.UserEventRegistrationOutDTO;
import org.example.capstone3.Repository.EventRepository;
import org.example.capstone3.Repository.UserEventRegistrationRepository;
import org.example.capstone3.Repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserEventRegistrationService {

    private final UserEventRegistrationRepository userEventRegistrationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
   private final EmailService emailService;

    public List<UserEventRegistrationOutDTO> getAllUserEventRegistrations() {
        List<UserEventRegistration> userEventRegistrations = userEventRegistrationRepository.findAll();
        List<UserEventRegistrationOutDTO> userEventRegistrationOutDTOs = new ArrayList<>();
        for (UserEventRegistration userEventRegistration : userEventRegistrations) {
            userEventRegistrationOutDTOs.add(new UserEventRegistrationOutDTO(userEventRegistration.getStatus(),userEventRegistration.getEvent().getId(),userEventRegistration.getUser().getId()));
        }
        return userEventRegistrationOutDTOs;
    }

    public void UserRegistration(Integer user_id,Integer event_id) {
        Event event = eventRepository.findEventById(event_id);
        User user = userRepository.findUserById(user_id);
        UserEventRegistration userEventRegistration = new UserEventRegistration("Visitor",event ,user);
        userEventRegistrationRepository.save(userEventRegistration);
    }


    public void sendReminders() {

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Event> events = eventRepository.findAllByDate(tomorrow);
        for (Event event : events) {
            for (UserEventRegistration registration : event.getUserEventRegistrations()) {
                User user = registration.getUser();
                if (user != null) {
                    String emailMessage = "Reminder: You are registered for the event "
                            + event.getName() + " which will take place at "
                            + event.getLocation() + " on " + event.getDate() + ".";
                    emailService.sendEmail(user.getEmail(), "Event Reminder", emailMessage);
                }
            }
        }
    }







}
