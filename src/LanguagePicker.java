import models.LanguageModel;

import java.util.List;

public class LanguagePicker {

    private List<LanguageModel> languageModels;

    public LanguagePicker(List<LanguageModel> languageModels) {
        this.languageModels = languageModels;
    }

    public LanguageModel languageOfText(String textToAnalise){
        int minimumIndex = 0;
        double minimum = languageModels.get(0).bitEstimate(textToAnalise);
        for (int i = 1; i<languageModels.size(); i++ ){
            double bitEstimate = languageModels.get(i).bitEstimate(textToAnalise);
            if(bitEstimate < minimum){
                minimum = bitEstimate;
                minimumIndex = i;
            }

        }
        return languageModels.get(minimumIndex);
    }



}
