package uk.mushow.medilabo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import uk.mushow.medilabo.model.Patient;
import uk.mushow.medilabo.service.PatientService;
import uk.mushow.medilabo.MedilaboApplication;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = MedilaboApplication.class)
@AutoConfigureMockMvc
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    public void testGetAllPatients() throws Exception {
        Patient patient1 = new Patient();
        patient1.setId(1L);
        patient1.setName("John");
        patient1.setSurname("Doe");
        patient1.setBirthDate(sdf.parse("1990-01-01"));
        patient1.setGender("Male");
        patient1.setAddress("123 Main St");

        List<Patient> patients = Arrays.asList(patient1);

        when(patientService.findAll()).thenReturn(patients);

        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].surname").value("Doe"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    public void testGetPatientById() throws Exception {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("John");
        patient.setSurname("Doe");
        patient.setBirthDate(sdf.parse("1990-01-01"));
        patient.setGender("Male");
        patient.setAddress("123 Main St");

        when(patientService.findById(anyLong())).thenReturn(Optional.of(patient));

        mockMvc.perform(get("/patients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    public void testCreatePatient() throws Exception {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("John");
        patient.setSurname("Doe");
        patient.setBirthDate(sdf.parse("1990-01-01"));
        patient.setGender("Male");
        patient.setAddress("123 Main St");

        when(patientService.save(any(Patient.class))).thenReturn(patient);

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John\", \"surname\": \"Doe\", \"birthDate\": \"1990-01-01\", \"gender\": \"Male\", \"address\": \"123 Main St\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    public void testUpdatePatient() throws Exception {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("John");
        patient.setSurname("Doe");
        patient.setBirthDate(sdf.parse("1990-01-01"));
        patient.setGender("Male");
        patient.setAddress("123 Main St");

        when(patientService.findById(anyLong())).thenReturn(Optional.of(patient));
        when(patientService.save(any(Patient.class))).thenReturn(patient);

        mockMvc.perform(put("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"John\", \"surname\": \"Doe\", \"birthDate\": \"1990-01-01\", \"gender\": \"Male\", \"address\": \"123 Main St\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    public void testDeletePatient() throws Exception {
        doNothing().when(patientService).deleteById(anyLong());

        mockMvc.perform(delete("/patients/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    public void testCreatePatientValidationFailure() throws Exception {
        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"\", \"surname\": \"\", \"birthDate\": null, \"gender\": null, \"address\": \"123 Main St\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("must not be blank"))
                .andExpect(jsonPath("$.surname").value("must not be blank"))
                .andExpect(jsonPath("$.birthDate").value("must not be null"))
                .andExpect(jsonPath("$.gender").value("must not be null"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    public void testUpdatePatientNotFound() throws Exception {
        when(patientService.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(put("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"John\", \"surname\": \"Doe\", \"birthDate\": \"1990-01-01\", \"gender\": \"Male\", \"address\": \"123 Main St\"}"))
                .andExpect(status().isNotFound());
    }
}
