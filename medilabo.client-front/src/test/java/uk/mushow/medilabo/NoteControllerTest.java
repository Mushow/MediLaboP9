package uk.mushow.medilabo;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import uk.mushow.client.ClientFrontApplication;
import uk.mushow.client.controller.NoteController;
import uk.mushow.client.dtos.notes.NotePatientDTO;
import uk.mushow.client.dtos.patients.PatientDTO;
import uk.mushow.client.proxy.WebProxy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = ClientFrontApplication.class)
@AutoConfigureMockMvc
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WebProxy webProxy;

    @Test
    @WithMockUser(username = "doctor", roles = {"DOCTOR"})
    public void testShowPatients() throws Exception {
        PatientDTO patient1 = new PatientDTO();
        patient1.setId(1L);
        patient1.setName("John Doe");
        patient1.setGender("M");

        List<PatientDTO> patients = Arrays.asList(patient1);

        when(webProxy.getAllPatients()).thenReturn(patients);
        when(webProxy.getHealthRisk(1L, "M", "30")).thenReturn("None");

        mockMvc.perform(get("/doctor"))
                .andExpect(status().isOk())
                .andExpect(view().name("notes/doctor"))
                .andExpect(model().attributeExists("patients"))
                .andExpect(model().attributeExists("riskLevels"));
    }

    @Test
    @WithMockUser(username = "doctor", roles = {"DOCTOR"})
    public void testShowAddNotesForm() throws Exception {
        mockMvc.perform(get("/doctor/add-notes/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("notes/doctor_add"))
                .andExpect(model().attributeExists("notePatient"));
    }

    @Test
    @WithMockUser(username = "doctor", roles = {"DOCTOR"})
    public void testSaveNotes() throws Exception {
        mockMvc.perform(post("/doctor/add-notes/1")
                        .param("content", "New Note"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/doctor"));
    }

    @Test
    @WithMockUser(username = "doctor", roles = {"DOCTOR"})
    public void testShowNotes() throws Exception {
        NotePatientDTO note1 = new NotePatientDTO();
        note1.setNoteId("1");
        note1.setNote("First Note");

        List<NotePatientDTO> notes = Arrays.asList(note1);
        PatientDTO patient = new PatientDTO();
        patient.setId(1L);
        patient.setName("John Doe");

        when(webProxy.getNotesByPatientId(1L)).thenReturn(notes);
        when(webProxy.getPatientById(1L)).thenReturn(patient);

        mockMvc.perform(get("/doctor/view-notes/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("notes/doctor_view"))
                .andExpect(model().attributeExists("notes"))
                .andExpect(model().attributeExists("patientId"));
    }

    @Test
    @WithMockUser(username = "doctor", roles = {"DOCTOR"})
    public void testShowEditNotesForm() throws Exception {
        NotePatientDTO note1 = new NotePatientDTO();
        note1.setNoteId("1");
        note1.setNote("First Note");

        List<NotePatientDTO> notes = Arrays.asList(note1);

        when(webProxy.getNotesByPatientId(1L)).thenReturn(notes);

        mockMvc.perform(get("/doctor/edit-notes/1/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("notes/doctor_edit"))
                .andExpect(model().attributeExists("notes"))
                .andExpect(model().attributeExists("notePatient"));
    }

    @Test
    @WithMockUser(username = "doctor", roles = {"DOCTOR"})
    public void testUpdateNotes() throws Exception {
        mockMvc.perform(post("/doctor/edit-notes/1/1")
                        .param("content", "Updated Note"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/doctor"));
    }

    @Test
    @WithMockUser(username = "doctor", roles = {"DOCTOR"})
    public void testDeleteNotes() throws Exception {
        mockMvc.perform(post("/doctor/delete-notes/1/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/doctor"));
    }
}
