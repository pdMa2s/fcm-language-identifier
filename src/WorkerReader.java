import models.ContextModel;
import models.LanguageModel;
import models.ProbabilityModel;
import textparser.ContextFileParser;

import java.io.File;
import java.util.List;

public class WorkerReader extends Thread {

    private File documentFile;
    private ContextFileParser parser;
    private int order;
    private double alpha;
    private List<LanguageModel> probabilityModelList;

    public WorkerReader(File documentFile, int order, double alpha, ContextFileParser parser ,List<LanguageModel> probabilityModelList) {
        this.documentFile = documentFile;
        this.order = order;
        this.alpha = alpha;
        this.parser = parser;
        this.probabilityModelList = probabilityModelList;
    }

    @Override
    public void run() {
        String documentText = parser.parse(documentFile);
        ContextModel contextModel = new ContextModel(order,documentText);
        ProbabilityModel probabilityModel = new ProbabilityModel(contextModel, alpha);
        LanguageModel language = new LanguageModel(probabilityModel,parseFileName(documentFile));
        probabilityModelList.add(language);
    }

    private String parseFileName(File documentFile){
        /*String[] parsedName = documentFile.getName().split("_");
        if (parsedName.length < 1){
            System.err.println("ERROR file with invalid name!");
            System.exit(3);
        }*/
        return documentFile.getName();
    }

}
