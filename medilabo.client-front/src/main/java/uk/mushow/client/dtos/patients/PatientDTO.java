package uk.mushow.client.dtos.patients;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {

    private Long id;
    private String name;
    private String surname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    private String gender;
    private String phoneNumber;
    private String address;

    public String getAge() {
        if (birthDate != null) {
            LocalDate birthDateLocal = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return String.valueOf(Period.between(birthDateLocal, LocalDate.now()).getYears());
        } else {
            return "0";
        }
    }
}
