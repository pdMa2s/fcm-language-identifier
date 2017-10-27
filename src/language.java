import models.LanguageModel;
import textparser.ContextFileParser;
import textparser.LiberalParser;

import java.util.List;
import java.io.File;
import java.util.Map;

public class language {

    public static void main(String[] args){
        checkParameterLength(args);
        String textFilename = args[0];
        int order = Integer.parseInt(args[1]);
        double alpha = Double.parseDouble(args[2]);
        String directory = "models40k";
        BarChartLanguages chart = new BarChartLanguages();

        if (args.length == 4)
            directory = args[3];
        
        ContextFileParser parser = new LiberalParser();
        ModelLoader modelLoader = new ParallelModelLoader(order, alpha, parser);
        List<LanguageModel> languageModels = modelLoader.loadModels(directory);
        String textToAnalise = readText(textFilename);

        LanguagePicker languagePicker = new LanguagePicker(languageModels);
        LanguageModel chosenLanguage = languagePicker.languageOfText(textToAnalise);

        Map<LanguageModel, Double> estimates = languagePicker.getBitEstimates();
        double sum = 0;
        double estimateOfChosen = 0;
        for(LanguageModel model: estimates.keySet()){
            double estimate = estimates.get(model);
            if(!model.equals(chosenLanguage))
                sum += estimate;
            else
                estimateOfChosen = estimate;
            System.out.println(model + ": " + estimate);
        }
        double average = sum/estimates.size();
        System.out.println("Language of the text: "+chosenLanguage.getLanguage());
        System.out.println("Average bit estimates for other languages: "+ average);
        System.out.println("Difference between average and chosen language estimate: "+ (average-estimateOfChosen));
        chart.addEstimates(estimates);
        chart.show();

    }

    private static String readText(String fileName){
        ContextFileParser parser = new LiberalParser();
        return parser.parse(new File(fileName));
    }
    
    private static void checkParameterLength(String[] args){
        if(args.length < 3 || args.length > 4){
            printUsage();
        }
    }
    
    private static void printUsage(){
        System.out.println("USAGE: java language <textFilename> <order> <alpha> <directory>(optional)\n"+
                            "<textFilename> - name of the file that contain the text under analysis\n"+
                            "<order> - The order of the finite-context model\n"+
                            "<alpha> - The level of creativity of the text generator\n"+
                            "<directory>(optional) - directory that contains all the text that representing a certain language"
                            );
        System.exit(1);
    }
}
