package uk.mushow.client.dtos.notes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotePatientDTO {

    private String noteId;
    private Long patientId;
    private String note;

}
