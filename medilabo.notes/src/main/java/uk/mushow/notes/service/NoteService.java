package uk.mushow.notes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.mushow.notes.models.NotePatient;
import uk.mushow.notes.repository.NoteRepository;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public List<NotePatient> getNotesByPatient(Long patientId){
        return noteRepository.findByPatientId(patientId);
    }

    public void deleteNoteByPatientId(Long patientId) {
        List<NotePatient> allPatientsNotes = noteRepository.findByPatientId(patientId);
        noteRepository.deleteAll(allPatientsNotes);
    }

    public NotePatient getNoteById (String id) {
        return noteRepository.findById(id).get();
    }

    public void createNote(NotePatient note){
        noteRepository.insert(note);
    }

    public void updateNote(NotePatient note){
        noteRepository.save(note);
    }

    public void deleteNoteById(String id) {
        noteRepository.deleteById(id);
    }

}
