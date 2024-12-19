package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiException;
import org.example.capstone3.Model.Course;
import org.example.capstone3.Model.Motorcycle;
import org.example.capstone3.Model.Owner;
import org.example.capstone3.OutDTO.CourseOutDTO;
import org.example.capstone3.OutDTO.MotorcycleOutDTO;
import org.example.capstone3.OutDTO.OwnerOutDTO;
import org.example.capstone3.Repository.CourseRepository;
import org.example.capstone3.Repository.MotorcycleRepository;
import org.example.capstone3.Repository.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final MotorcycleRepository motorcycleRepository;
    private final CourseRepository courseRepository;


    public List<OwnerOutDTO> getAllOwners() {
        // Step 1: Fetch all owners
        List<Owner> owners = ownerRepository.findAll();

        // Step 2: Map each owner to OwnerDTO
        List<OwnerOutDTO> ownerDTOList = new ArrayList<>();
        for (Owner owner : owners) {
            // Fetch motorcycles for the owner
            List<Motorcycle> motorcycles = motorcycleRepository.findMotorcycleByOwnerId(owner.getId());

            // Map motorcycles to MotorcycleDTOs
            List<MotorcycleOutDTO> motorcycleDTOs = motorcycles.stream().map(motorcycle -> new MotorcycleOutDTO(
                    motorcycle.getBrand(),
                    motorcycle.getModel(),
                    motorcycle.getYear(),
                    motorcycle.getPrice(),
                    motorcycle.getColor(),
                    motorcycle.getIsForSale(),
                    motorcycle.getIsAvailable(),
                    motorcycle.getHasOffer()
            )).collect(Collectors.toList());

            // Fetch courses for the owner
            List<Course> courses = courseRepository.findCoursesByOwnerId(owner.getId());

            // Map courses to CourseDTOs
            List<CourseOutDTO> courseDTOs = courses.stream().map(course -> new CourseOutDTO(
                    course.getName(),
                    course.getDescription(),
                    course.getPrice(),
                    course.getDuration()
            )).collect(Collectors.toList());

            // Map Owner to OwnerDTO
            OwnerOutDTO ownerDTO = new OwnerOutDTO(
                    owner.getName(),
                    owner.getEmail(),
                    owner.getPhoneNumber(),
                    owner.getAddress(),
                    motorcycleDTOs,
                    courseDTOs
            );

            ownerDTOList.add(ownerDTO);
        }

        return ownerDTOList;
    }


    public void createOwner(Owner owner) {
        ownerRepository.save(owner);
    }

    public void updateOwner(Integer id, Owner owner){

        Owner o = ownerRepository.findOwnerById(id);

        if(o == null)
            throw new ApiException("Owner not found!");

        o.setName(owner.getName());
        o.setEmail(owner.getEmail());
        o.setPhoneNumber(owner.getPhoneNumber());
        o.setPassword(owner.getPassword());

        ownerRepository.save(o);
    }

    public void deleteOwner(Integer id){

        Owner o = ownerRepository.findOwnerById(id);

        if(o == null)
            throw new ApiException("Owner not found!");

        ownerRepository.delete(o);

    }
    ////////////////













}
