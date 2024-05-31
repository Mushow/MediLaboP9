package uk.mushow.medilabo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import uk.mushow.notes.MedilaboApplication;
import uk.mushow.notes.models.NotePatient;
import uk.mushow.notes.service.NoteService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = MedilaboApplication.class)
@AutoConfigureMockMvc
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    public void testGetNotesByPatientId() throws Exception {
        NotePatient note1 = new NotePatient("1", 1L, "Note 1");
        List<NotePatient> notes = Arrays.asList(note1);

        when(noteService.getNotesByPatient(anyLong())).thenReturn(notes);

        mockMvc.perform(get("/note/patient/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].note").value("Note 1"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    public void testGetNoteById() throws Exception {
        NotePatient note = new NotePatient("1", 1L, "Note 1");

        when(noteService.getNoteById(anyString())).thenReturn(note);

        mockMvc.perform(get("/note/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.note").value("Note 1"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    public void testCreateNote() throws Exception {
        NotePatient note = new NotePatient("1", 1L, "Note 1");

        doNothing().when(noteService).createNote(any(NotePatient.class));

        mockMvc.perform(post("/note")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"noteId\": \"1\", \"patientId\": 1, \"note\": \"Note 1\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    public void testUpdateNote() throws Exception {
        NotePatient note = new NotePatient("1", 1L, "Updated Note");

        doNothing().when(noteService).updateNote(any(NotePatient.class));

        mockMvc.perform(put("/note")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"noteId\": \"1\", \"patientId\": 1, \"note\": \"Updated Note\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    public void testDeleteNoteById() throws Exception {
        doNothing().when(noteService).deleteNoteById(anyString());

        mockMvc.perform(delete("/note/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    public void testDeleteNoteByPatient() throws Exception {
        doNothing().when(noteService).deleteNoteByPatientId(anyLong());

        mockMvc.perform(delete("/note/patient/1"))
                .andExpect(status().isNoContent());
    }
}
