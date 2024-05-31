package uk.mushow.medilabo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import uk.mushow.risk.MedilaboApplication;
import uk.mushow.risk.service.RiskService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MedilaboApplication.class)
@AutoConfigureMockMvc
public class RiskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RiskService riskService;

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    public void testGetHealthRisk() throws Exception {
        String patientId = "1";
        String gender = "M";
        String age = "30";
        String riskLevel = "None";

        when(riskService.getRiskLevelOfPatient(patientId, gender, age)).thenReturn(riskLevel);

        mockMvc.perform(get("/risk/{patientId}/{gender}/{age}", patientId, gender, age))
                .andExpect(status().isOk())
                .andExpect(content().string(riskLevel));
    }

}
