package uk.mushow.risk.util;

public enum RiskLevel {

    NONE("None"),
    BORDERLINE("Borderline"),
    IN_DANGER("In Danger"),
    EARLY_ONSET("Early Onset"),
    UNKNOWN("Unknown Risk Level");

    public final String risk;
    RiskLevel(String s) {
        this.risk = s;
    }

}
