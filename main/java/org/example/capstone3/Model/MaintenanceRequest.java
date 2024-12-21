package org.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MaintenanceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String expert_name;

    @Column(columnDefinition = "DATE")
    private LocalDate requestDate = LocalDate.now();  // Date when the maintenance request was created by owner

    @Column(columnDefinition = "Double")
    private Double totalPrice;

    @Pattern(regexp = "^(Pending|Completed)$")
    @Column(columnDefinition = "varchar(10) default 'Pending'")
    private String status;

    @Column(columnDefinition = "DATE not null")
    private LocalDate pickupDate;

    @Column(columnDefinition = "int not null")
    private Integer motorcycle_id;


    //Relations

    @ManyToOne

    @JsonIgnore
    private MaintenanceExpert expert;

    @ManyToOne
    @JsonIgnore
    private Owner owner;  // The owner who made the maintenance request

    public MaintenanceRequest( LocalDate pickupDate, Owner owner, Integer motorcycle_id){

        this.pickupDate=pickupDate;
        this.owner=owner;
        this.motorcycle_id=motorcycle_id;
    }



}
