package uk.mushow.notes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uk.mushow.notes.models.NotePatient;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<NotePatient, String> {

    List<NotePatient> findByPatientId(Long patientId);

}
