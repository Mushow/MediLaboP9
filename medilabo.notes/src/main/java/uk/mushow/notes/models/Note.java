package uk.mushow.notes.models;

import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note {

    @Id
    private String noteId;
    private String note;

}
