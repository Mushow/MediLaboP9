package uk.mushow.client.dtos.risk;

public enum RiskLevelDTO {

    NONE("None"),
    BORDERLINE("Borderline"),
    IN_DANGER("In Danger"),
    EARLY_ONSET("Early Onset"),
    UNKNOWN("Unknown Risk Level");

    public final String risk;
    RiskLevelDTO(String s) {
        this.risk = s;
    }

}