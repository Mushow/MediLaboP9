package uk.mushow.client.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import uk.mushow.client.dtos.notes.NotePatient;

import java.util.List;

@FeignClient(name = "gateway", url = "${gateway.url}")
public interface WebProxy {

    @GetMapping(value = "/note/patient/{id}")
    List<NotePatient> getNotesByPatientId(@PathVariable("id") Long patientId);

    @GetMapping(value = "/note/{id}")
    NotePatient getNoteById(@PathVariable("id") Long id);

    @PostMapping(value = "/note")
    void createNote(@RequestBody NotePatient note);

    @PutMapping(value = "/note")
    void updateNote(@RequestBody NotePatient note);

    @DeleteMapping(value = "/note/{id}")
    void deleteNoteById(@PathVariable("id") Long id);

    @DeleteMapping(value = "/note/patient/{id}")
    void deleteNoteByPatientId(@PathVariable("id") Long patientId);

}
