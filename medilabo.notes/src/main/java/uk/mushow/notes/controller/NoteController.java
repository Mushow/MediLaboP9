package uk.mushow.notes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.mushow.notes.models.NotePatient;
import uk.mushow.notes.service.NoteService;

import java.util.List;

@Controller
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/note/patient/{id}")
    public ResponseEntity<List<NotePatient>> getNotesByPatientId(@PathVariable("id") Long patientId) {
        return ResponseEntity.status(HttpStatus.OK).body(noteService.getNotesByPatient(patientId));
    }

    @GetMapping("/note/{id}")
    public ResponseEntity<NotePatient> getNoteById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(noteService.getNoteById(id));
    }

    @PostMapping("/note")
    public ResponseEntity<NotePatient> createNote(@RequestBody NotePatient note) {
        noteService.createNote(note);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/note")
    public ResponseEntity<NotePatient> updateNote(@RequestBody NotePatient note) {
        noteService.updateNote(note);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/note/{id}")
    public ResponseEntity<NotePatient> deleteNoteById(@PathVariable("id") Long id) {
        noteService.deleteNoteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/note/patient/{id}")
    public ResponseEntity<NotePatient> deleteNoteByPatient(@PathVariable("id") Long patientId) {
        noteService.deleteNoteByPatientId(patientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
