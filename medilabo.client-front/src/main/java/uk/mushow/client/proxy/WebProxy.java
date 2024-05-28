package uk.mushow.client.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import uk.mushow.client.dtos.notes.NotePatientDTO;
import uk.mushow.client.dtos.patients.PatientDTO;

import java.util.List;

@FeignClient(name = "gateway", url = "${gateway}")
public interface WebProxy {

    @GetMapping(value = "/note/patient/{id}")
    List<NotePatientDTO> getNotesByPatientId(@PathVariable("id") Long patientId);

    @GetMapping(value = "/note/{id}")
    NotePatientDTO getNoteById(@PathVariable("id") Long id);

    @PostMapping(value = "/note")
    void createNote(@RequestBody NotePatientDTO note);

    @PutMapping(value = "/note")
    void updateNote(@RequestBody NotePatientDTO note);

    @DeleteMapping(value = "/note/{id}")
    void deleteNoteById(@PathVariable("id") String id);

    @DeleteMapping(value = "/note/patient/{id}")
    void deleteNoteByPatientId(@PathVariable("id") Long patientId);



    @GetMapping("/patients")
    List<PatientDTO> getAllPatients();

    @GetMapping("/patients/{id}")
    PatientDTO getPatientById(@PathVariable("id") Long id);

    @PostMapping("/patients")
    PatientDTO createPatient(@RequestBody PatientDTO patientDTO);

    @PutMapping("/patients")
    PatientDTO updatePatient(@RequestBody PatientDTO patientDTO);

    @DeleteMapping("/patients/{id}")
    void deletePatientById(@PathVariable("id") Long id);

    @PostMapping("/patients")
    void savePatient(PatientDTO patientDto);


    @GetMapping("{patientId}/{gender}/{age}")
    String getRisk(@PathVariable("patientId") Long patient, @PathVariable String gender, @PathVariable String age);

}
