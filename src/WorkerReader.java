import models.ContextModel;
import models.LanguageModel;
import models.ProbabilityModel;
import textparser.ContextFileParser;

import java.io.File;
import java.util.List;

public class WorkerReader extends Thread {

    private String filename;
    private String documentText;
    private int order;
    private double alpha;
    private List<LanguageModel> probabilityModelList;

    public WorkerReader(String filename, int order, double alpha, String documentText ,List<LanguageModel> probabilityModelList) {
        this.filename = filename;
        this.order = order;
        this.alpha = alpha;
        this.documentText = documentText;
        this.probabilityModelList = probabilityModelList;
    }

    @Override
    public void run() {
        ContextModel contextModel = new ContextModel(order,documentText);
        ProbabilityModel probabilityModel = new ProbabilityModel(contextModel, alpha);
        LanguageModel language = new LanguageModel(probabilityModel,filename);
        probabilityModelList.add(language);
    }
    
}
