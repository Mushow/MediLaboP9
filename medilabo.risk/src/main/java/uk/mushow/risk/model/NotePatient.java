package uk.mushow.risk.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class NotePatient {

    private Long noteId;
    private Long patientId;
    private String note;

}
