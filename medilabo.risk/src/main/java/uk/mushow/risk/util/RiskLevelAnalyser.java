package uk.mushow.risk.util;

import uk.mushow.risk.model.NotePatient;

import java.util.List;

public class RiskLevelAnalyser {

    private static final List<String> triggers = List.of(
            "Hémoglobine A1C",
            "Microalbumine",
            "Taille",
            "Poids",
            "Fumeur",
            "Fumeuse",
            "Anormal",
            "Cholestérol",
            "Vertiges",
            "Rechute",
            "Réaction",
            "Anticorps"
    );

    public static String getRiskLevel(List<NotePatient> notes, String gender, String age) {
        int triggerCount = (int) notes.stream()
                .map(NotePatient::getNote)
                .filter(triggers::contains)
                .count();
        int ageInt = Integer.parseInt(age);

        if (triggerCount == 0) {
            return RiskLevel.NONE.risk;
        }

        boolean isMale = gender.equals("M");
        boolean isFemale = gender.equals("F");

        if (triggerCount >= 2 && triggerCount <= 5 && ageInt > 30) {
            return RiskLevel.BORDERLINE.risk;
        }

        if ((triggerCount == 5 && isMale && ageInt < 30) ||
                (triggerCount == 7 && isFemale && ageInt < 30) ||
                (triggerCount >= 8 && ageInt > 30)) {
            return RiskLevel.EARLY_ONSET.risk;
        }

        if ((triggerCount == 3 && isMale && ageInt < 30) ||
                (triggerCount == 4 && isFemale && ageInt < 30) ||
                ((triggerCount >= 6 && triggerCount <= 7) && isMale && ageInt > 30)) {
            return RiskLevel.IN_DANGER.risk;
        }

        return RiskLevel.UNKNOWN.risk;
    }


}
