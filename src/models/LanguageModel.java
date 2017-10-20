package models;

public class LanguageModel {

    private ProbabilityModel probabilityModel;
    private String language;

    public LanguageModel(ProbabilityModel probabilityModel, String language) {
        this.probabilityModel = probabilityModel;
        this.language = language;
    }


    @Override
    public String toString() {
        return "language='" + language + '\'';
    }
}
