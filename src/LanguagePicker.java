import models.LanguageModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanguagePicker {

    private List<LanguageModel> languageModels;
    private Map<LanguageModel, Double> bitEstimates;
    public LanguagePicker(List<LanguageModel> languageModels) {
        bitEstimates = new HashMap<>();
        this.languageModels = languageModels;
    }

    public LanguageModel languageOfText(String textToAnalise){
        int minimumIndex = 0;
        double minimum = languageModels.get(0).bitEstimate(textToAnalise);
        for (int i = 1; i<languageModels.size(); i++ ){
            LanguageModel model = languageModels.get(i);
            double bitEstimate = model.bitEstimate(textToAnalise);
            bitEstimates.put(model, bitEstimate);
            if(bitEstimate < minimum){
                minimum = bitEstimate;
                minimumIndex = i;
            }

        }
        return languageModels.get(minimumIndex);
    }

    public Map<LanguageModel, Double> getBitEstimates() {
        return bitEstimates;
    }
}
