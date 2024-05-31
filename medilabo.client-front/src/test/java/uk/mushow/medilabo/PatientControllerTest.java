package uk.mushow.client.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import uk.mushow.client.ClientFrontApplication;
import uk.mushow.client.dtos.patients.PatientDTO;
import uk.mushow.client.proxy.WebProxy;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ClientFrontApplication.class)
@AutoConfigureMockMvc
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WebProxy proxy;

    @Test
    @WithMockUser(username = "organizer", roles = {"ORGANIZER"})
    public void testShowPatients() throws Exception {
        PatientDTO patient1 = new PatientDTO();
        patient1.setId(1L);
        patient1.setName("John Doe");

        List<PatientDTO> patients = Arrays.asList(patient1);

        when(proxy.getAllPatients()).thenReturn(patients);

        mockMvc.perform(get("/organizer"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/organizer"))
                .andExpect(model().attributeExists("patients"));
    }

    @Test
    @WithMockUser(username = "organizer", roles = {"ORGANIZER"})
    public void testShowSavePatientForm() throws Exception {
        mockMvc.perform(get("/organizer/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/organizer_add"))
                .andExpect(model().attributeExists("patient"));
    }

    @Test
    @WithMockUser(username = "organizer", roles = {"ORGANIZER"})
    public void testShowEditPatientForm() throws Exception {
        PatientDTO patient = new PatientDTO();
        patient.setId(1L);
        patient.setName("John Doe");

        when(proxy.getPatientById(1L)).thenReturn(patient);

        mockMvc.perform(get("/organizer/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/organizer_edit"))
                .andExpect(model().attributeExists("patient"));
    }

    @Test
    @WithMockUser(username = "organizer", roles = {"ORGANIZER"})
    public void testDeletePatient() throws Exception {
        mockMvc.perform(post("/organizer/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/organizer"));
    }

    @Test
    @WithMockUser(username = "organizer", roles = {"ORGANIZER"})
    public void testSavePatient() throws Exception {
        mockMvc.perform(post("/organizer/validate")
                        .param("name", "John Doe")
                        .param("address", "123 Main St"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/organizer"));
    }

    @Test
    @WithMockUser(username = "organizer", roles = {"ORGANIZER"})
    public void testUpdatePatient() throws Exception {
        mockMvc.perform(post("/organizer/update/1")
                        .param("name", "John Doe")
                        .param("address", "123 Main St"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/organizer"));
    }
}
