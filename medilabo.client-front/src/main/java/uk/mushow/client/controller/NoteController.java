package uk.mushow.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import uk.mushow.client.dtos.notes.NotePatientDTO;
import uk.mushow.client.dtos.patients.PatientDTO;
import uk.mushow.client.proxy.WebProxy;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class NoteController {

    @Autowired
    private WebProxy webProxy;

    @GetMapping("/doctor")
    public String showPatients(Model model) {
        List<PatientDTO> patients;
        try {
            patients = webProxy.getAllPatients();
        } catch (Exception e) {
            patients = Collections.emptyList();
        }
        model.addAttribute("patients", patients);
        return "notes/doctor";
    }

    @GetMapping("/doctor/add-notes/{id}")
    public String showAddNotesForm(@PathVariable Long id, Model model) {
        NotePatientDTO note = new NotePatientDTO();
        note.setPatientId(id);
        model.addAttribute("notePatient", note);
        return "notes/doctor_add";
    }

    @PostMapping("/doctor/add-notes/{id}")
    public String saveNotes(@PathVariable Long id, NotePatientDTO note) {
        note.setPatientId(id);
        webProxy.createNote(note);
        return "redirect:/doctor";
    }

    @GetMapping("/doctor/view-notes/{id}")
    public String showNotes(@PathVariable Long id, Model model) {
        List<NotePatientDTO> notes;
        try {
            notes = webProxy.getNotesByPatientId(id);
        } catch (Exception e) {
            notes = Collections.emptyList();
        }
        model.addAttribute("patientId", id);
        model.addAttribute("notes", notes);
        return "notes/doctor_view";
    }

    @GetMapping("/doctor/edit-notes/{patientId}/{id}")
    public String showEditNotesForm(@PathVariable Long patientId, @PathVariable String id, Model model) {
        List<NotePatientDTO> notes;
        try {
            notes = webProxy.getNotesByPatientId(patientId);
        } catch (Exception e) {
            notes = Collections.emptyList();
        }
        model.addAttribute("notes", notes);
        Optional<NotePatientDTO> optionalNote = notes.stream()
                .filter(note -> note.getNoteId().equals(id))
                .findFirst();
        if (optionalNote.isEmpty()) {
            return "redirect:/doctor";
        }

        model.addAttribute("notePatient", optionalNote.get());
        return "notes/doctor_edit";
    }

    @PostMapping("/doctor/edit-notes/{patientId}/{id}")
    public String updateNotes(@PathVariable String id, NotePatientDTO note) {
        note.setNoteId(id);
        webProxy.updateNote(note);
        return "redirect:/doctor";
    }

    @PostMapping("/doctor/delete-notes/{id}")
    public String deleteNotes(@PathVariable String id) {
        webProxy.deleteNoteById(id);
        return "redirect:/doctor";
    }

}
