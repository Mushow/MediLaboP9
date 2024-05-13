package uk.mushow.medilabo.controller;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.mushow.medilabo.model.Patient;
import uk.mushow.medilabo.service.PatientService;

import java.util.List;

@RestController
public class PatientController {

    Logger logger;

    @Autowired
    private PatientService patientService;

    @GetMapping("/patients")
    public String getPatients() {
        return "Patients";
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAll(){
        List<Patient> patients = patientService.findAll();
        if (!patients.isEmpty()) return ResponseEntity.ok(patients);
        logger.warn("[ERROR] No patients found.");
        return ResponseEntity.internalServerError().build();
    }

}
