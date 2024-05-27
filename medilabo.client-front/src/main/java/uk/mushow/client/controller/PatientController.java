package uk.mushow.client.controller;

import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import uk.mushow.client.dtos.patients.PatientDTO;
import uk.mushow.client.proxy.WebProxy;

import java.util.Collections;
import java.util.List;

@Controller
public class PatientController {

    private final WebProxy proxy;

    public PatientController(WebProxy proxy) {
        this.proxy = proxy;
    }

    @GetMapping("/organizer")
    public String showPatients(Model model) {
        List<PatientDTO> patients;
        try {
            patients = proxy.getAllPatients();
        } catch (FeignException e) {
            patients = Collections.emptyList();
        }
        model.addAttribute("patients", patients);
        return "patients/organizer";
    }

    @GetMapping("/organizer/add")
    public String showSavePatientForm(Model model) {
        model.addAttribute("patient", new PatientDTO());
        return "patients/organizer_add";
    }

    @GetMapping("/organizer/edit/{id}")
    public String showEditPatientForm(Model model, @PathVariable String id) {
        model.addAttribute("patient", proxy.getPatientById(Long.parseLong(id)));
        return "patients/organizer_edit";
    }

    @PostMapping("/organizer/delete/{id}")
    public String deletePatient(@PathVariable("id") Long id) {
        proxy.deletePatientById(id);
        return "redirect:/organizer";
    }

    @PostMapping("/organizer/validate")
    public String savePatient(@Valid @ModelAttribute("patient") PatientDTO patientDto, BindingResult result, Model model) {
        if ((patientDto.getAddress().equalsIgnoreCase(""))) {
            patientDto.setAddress(null);
        }
        if (!result.hasErrors()) {
            proxy.savePatient(patientDto);
            return "redirect:/organizer";
        }
        model.addAttribute("patient", patientDto);
        return "patients/organizer_add";
    }

    @PostMapping("/organizer/update/{id}")
    public String updatePatient(@PathVariable("id") Long id, @Valid @ModelAttribute("patient") PatientDTO patientDto, BindingResult result, Model model) {
        patientDto.setId(id);
        if (patientDto.getAddress().equalsIgnoreCase("")) {
            patientDto.setAddress(null);
        }
        if (!result.hasErrors()) {
            proxy.updatePatient(patientDto);
            return "redirect:/organizer";
        }
        model.addAttribute("patient", patientDto);
        return "patients/organizer_edit";
    }

}
