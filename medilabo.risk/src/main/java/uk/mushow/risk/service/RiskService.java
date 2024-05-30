package uk.mushow.risk.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import uk.mushow.risk.model.NotePatient;
import uk.mushow.risk.util.RiskLevelAnalyser;

import java.util.List;

@Service
public class RiskService {

    @Value("${baseUrl}")
    private String baseUrl;

    private final WebClient webClient;

    public RiskService() {
        this.webClient = WebClient.builder().baseUrl("http://localhost:10003")
                .defaultHeaders(headers -> headers.setBasicAuth("root", "rootroot"))
                .build();
    }

    private List<NotePatient> getPatientNotes(String patientId) {
        return webClient.get()
                .uri("/note/patient/{patientId}", patientId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<NotePatient>>() {})
                .block();
    }

    public String getRiskLevelOfPatient(String patientId, String gender, String age) {
        List<NotePatient> notes = this.getPatientNotes(patientId);
        return RiskLevelAnalyser.getRiskLevel(notes, gender, age);
    }

}
