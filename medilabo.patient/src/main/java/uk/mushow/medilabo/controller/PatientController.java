package uk.mushow.medilabo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uk.mushow.medilabo.model.Patient;
import uk.mushow.medilabo.service.PatientService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/patients")
public class PatientController {

    Logger logger;

    @Autowired
    private PatientService patientService;

    @GetMapping
    public ResponseEntity<List<Patient>> getAll() {
        List<Patient> patients = patientService.findAll();
        if (!patients.isEmpty()) return ResponseEntity.ok(patients);
        logger.warn("[ERROR] No patients found.");
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Optional<Patient> optionalPatient = patientService.findById(id);
        if (optionalPatient.isPresent()) return ResponseEntity.ok(optionalPatient.get());
        logger.info("[INFO] Entity not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@Valid @RequestBody Patient patient) {
        logger.info("[INFO] Creating patient");
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.save(patient));
    }

    @PutMapping
    public ResponseEntity<Patient> updatePatient(@Valid @RequestBody Patient patient){
        Optional<Patient> optionalPatient = patientService.findById(patient.getId());
        if (optionalPatient.isPresent()) {
            logger.info("[INFO] Updating patient");
            return ResponseEntity.ok(patientService.save(patient));
        }
        logger.info("[INFO] Entity not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        patientService.deleteById(id);
        logger.info("[INFO] Deleting patient");
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) ->{

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
