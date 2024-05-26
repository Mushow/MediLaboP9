package uk.mushow.client.dtos.patients;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {

    private Long id;
    private String name;
    private String surname;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;
    private String gender;
    private String phoneNumber;
    private String address;

}
