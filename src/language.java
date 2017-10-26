import models.LanguageModel;
import textparser.ContextFileParser;
import textparser.LiberalParser;

import java.util.List;
import java.io.File;

public class language {

    public static void main(String[] args){
        checkParameterLength(args);
        String directory = "languageModels";
        String textFilename = args[0];
        int order = Integer.parseInt(args[1]);
        double alpha = Double.parseDouble(args[2]);
        
        ContextFileParser parser = new LiberalParser();
        ModelLoader modelLoader = new ParallelModelLoader(order, alpha, parser);
        List<LanguageModel> languageModels = modelLoader.loadModels(directory);
        String textToAnalise = readText(textFilename);
        System.out.println("Loaded languages:"+ languageModels);

        LanguagePicker languagePicker = new LanguagePicker(languageModels);
        LanguageModel languageModel = languagePicker.languageOfText(textToAnalise);
        System.out.println("Language of the text: "+languageModel.getLanguage());
    }

    private static String readText(String fileName){
        ContextFileParser parser = new LiberalParser();
        return parser.parse(new File(fileName));
    }
    
    private static void checkParameterLength(String[] args){
        if(args.length != 3){
            printUsage();
        }
    }
    
    private static void printUsage(){
        System.out.println("USAGE: java language <textFilename> <order> <alpha>\n"+
                            "<textFilename> - name of the file that contain the text under analysis\n"+
                            "<order> - The order of the finite-context model\n"+
                            "<alpha> - The level of creativity of the text generator"
                            );
        System.exit(1);
    }
}
