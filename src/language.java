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
        String directory = "models150k";
        BarChartLanguages chart = new BarChartLanguages();

        if (args.length == 4)
            directory = args[3];
        
        ContextFileParser parser = new LiberalParser();
        ModelLoader modelLoader = new ParallelModelLoader(order, alpha, parser);
        List<LanguageModel> languageModels = modelLoader.loadModels(directory);
        String textToAnalise = readText(textFilename, parser);

        LanguagePicker languagePicker = new LanguagePicker(languageModels);
        LanguageModel chosenLanguage = languagePicker.languageOfText(textToAnalise);

        LanguageModel closest = null;
        Map<LanguageModel, Double> estimates = languagePicker.getBitEstimates();
        double estimateOfChosen = estimates.get(chosenLanguage);
        double estimateOfClosest = Double.MAX_VALUE;
        for(LanguageModel model: estimates.keySet()){
            double estimate = estimates.get(model);
            if(estimate < estimateOfClosest && estimate != estimateOfChosen){
                estimateOfClosest = estimate;
                closest = model;
            }

            System.out.println(model + ": " + estimate);
        }
        System.out.println("Language of the text: "+chosenLanguage.getLanguage()+ " whit a bit estimate of: "+ estimateOfChosen);
        if(closest != null){
            System.out.println("Closest language: "+ closest.getLanguage()+ " whit a bit estimate of: "+ estimateOfClosest);
            System.out.println("Difference between the chosen language and the closest: " + (estimateOfClosest - estimateOfChosen));
        }


        chart.addEstimates(estimates);
        chart.show();

    }

    private static String readText(String fileName, ContextFileParser parser){
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
