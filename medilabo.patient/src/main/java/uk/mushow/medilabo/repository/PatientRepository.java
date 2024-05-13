package uk.mushow.medilabo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.mushow.medilabo.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {



}
