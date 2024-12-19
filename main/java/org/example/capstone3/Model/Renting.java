package org.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Renting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Positive(message = "Rental price per day must be a positive number!")
    @Column(columnDefinition = "DOUBLE not null")
    private Double pricePerDay;

    @NotEmpty(message = "Pickup location is required!")
    @Column(columnDefinition = "varchar(20) not null")
    private String pickupLocation;


    @Column(columnDefinition = "varchar(20)")
    private String dropOffLocation;

    @Column(columnDefinition = "int not null")
    private Integer motorcycle_id;

    //Relations

    @ManyToOne
    @JsonIgnore
    private Owner owner;  // Owner of the motorcycle being rented


    @OneToMany(mappedBy = "renting", cascade = CascadeType.ALL)
    private Set<RentingRequest> rentingRequests;



}
