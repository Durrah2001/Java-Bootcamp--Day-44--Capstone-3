package org.example.capstone3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
public class MaintenanceExpert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(20) not null unique")
    @NotEmpty(message = "Empty name")
    private String name;

    @Column(columnDefinition = "varchar(35) not null")
    @NotEmpty(message = "Empty email")
    @Email(message = "Enter valid email")
    private String email;

    @Column(columnDefinition = "varchar(20) not null unique")
    @NotEmpty(message = "Username cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username must contain only letters, numbers, and underscores")
    private String username;

    @NotEmpty(message = "Password is required!")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
    )
    @Column(columnDefinition = "varchar(20) not null")
    private String password;



    @Column(columnDefinition = "boolean default false ")
    private Boolean isApproved=false;

    @Column(columnDefinition = "varchar(20) not null")
    @NotEmpty(message = "Empty specialty")
    private String specialty;


    @Positive(message = "Price must be a positive number!")
    @Column(columnDefinition = "DOUBLE not null")
    private Double maintenancePrice;

    @Positive(message = "Experience must be a positive number!")
    @Column(columnDefinition = "int not null")
    private Integer yearsOfExperience;

    @Column(columnDefinition = "varchar(500) not null")
    @NotEmpty(message = "Description not valid")
    private String description;


    /////Relations

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "expert")
    private Set<MaintenanceRequest> maintenanceRequestSet;


}
