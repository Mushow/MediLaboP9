package uk.mushow.medilabo.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Data
@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    @Column(name = "birth_date")
    private Date birthDate;

    @NotBlank
    private char gender;

    @Column(name = "phone_number")
    private String phoneNumber;
    private String address;

}
