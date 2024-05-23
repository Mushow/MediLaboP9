package uk.mushow.risk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.mushow.risk.service.RiskService;

@Controller
@RequestMapping("/risk")
public class RiskController {

    @Autowired
    private RiskService riskService;
    @GetMapping("{patientId}/{gender}/{age}")
    public ResponseEntity<String> getHealthRisk(@PathVariable String patientId, @PathVariable String gender, @PathVariable String age){
        String riskLevel = riskService.getRiskLevelOfPatient(patientId, gender, age);
        return ResponseEntity.ok(riskLevel);
    }

}
