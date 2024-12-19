package org.example.capstone3.OutDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OwnerOutDTO {

    private String name;

    private String email;


    private String phoneNumber;

    private String address;

    private List<MotorcycleOutDTO> motorcycles;

    private List<CourseOutDTO> courses;












}
