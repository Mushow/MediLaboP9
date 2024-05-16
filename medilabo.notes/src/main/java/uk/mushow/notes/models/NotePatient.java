package uk.mushow.notes.models;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "notePatient")
public class NotePatient {

    @Id
    private Long noteId;
    private Long patientId;
    private List<Note> listNote = new ArrayList<>();

}
