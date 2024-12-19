package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiResponse;
import org.example.capstone3.Model.Event;
import org.example.capstone3.Service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/motorcycle-system/event")

public class EventController {

    private final EventService eventService;


    @GetMapping("/get")
    public ResponseEntity getEvents(){
        return ResponseEntity.status(200).body(eventService.getEvents());
    }

    @PostMapping("/add/{companyId}")
    public ResponseEntity addEvent(@PathVariable Integer companyId, @RequestBody @Valid Event event){
        eventService.addEvent(companyId,event);
        return ResponseEntity.status(200).body(new ApiResponse("Event added"));
    }

    @PutMapping("/update/{id}/{company_id}")
    public ResponseEntity updateEvent(@PathVariable Integer id,@PathVariable Integer company_id, @RequestBody @Valid Event event){
        eventService.updateEvent(id,company_id,event);
        return ResponseEntity.status(200).body(new ApiResponse("Event updated"));
    }

    @DeleteMapping("/delete/{id}/{company_id}")
    public ResponseEntity deleteEvent(@PathVariable Integer id, @PathVariable Integer company_id){
        eventService.deleteEvent(id,company_id);
        return ResponseEntity.status(200).body(new ApiResponse("Event deleted"));

    }







}
