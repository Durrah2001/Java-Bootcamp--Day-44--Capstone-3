package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiException;
import org.example.capstone3.Model.Company;
import org.example.capstone3.Model.Event;
import org.example.capstone3.OutDTO.EventOutDTO;
import org.example.capstone3.Repository.CompanyRepository;
import org.example.capstone3.Repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final CompanyRepository companyRepository;

    public List<EventOutDTO> getEvents(){
        List<Event> events = eventRepository.findAll();
        List<EventOutDTO> eventOutDTOS = new ArrayList<>();

        for(Event event:events){
            EventOutDTO eventOutDTO = new EventOutDTO(event.getName(),event.getLocation(),event.getDetails(),event.getDate());
            eventOutDTOS.add(eventOutDTO);
        }
        return eventOutDTOS;
    }


    public void addEvent(Integer companyId ,Event event){
        Company company = companyRepository.findCompanyById(companyId);
        if(company==null || !company.getIsApproved()){
            throw new ApiException("Company can not add event");
        }
        event.setCompany(company);
        eventRepository.save(event);
    }

    public void updateEvent(Integer id,Integer company_id, Event event){
        Event event1 = eventRepository.findEventById(id);
        if(event1==null){
            throw new ApiException("Event not found");
        }
        Company company = companyRepository.findCompanyById(company_id);
        if(company==null || !company.getIsApproved()){
            throw new ApiException("Company not found");
        }
        if (event1.getCompany() == company) {
            event1.setDate(event.getDate());
            event1.setDetails(event.getDetails());
            event1.setName(event.getName());
            event1.setLocation(event.getLocation());
            eventRepository.save(event1);
        }
    }


    public void deleteEvent(Integer id,Integer company_id){
        Event event = eventRepository.findEventById(id);
        if(event ==null){
            throw new ApiException("Event not found");
        }
        Company company = companyRepository.findCompanyById(company_id);
        if(company==null){
            throw new ApiException("Company not found");
        }
        if (event.getCompany() == company) {
            eventRepository.delete(event);
        }
    }





}
