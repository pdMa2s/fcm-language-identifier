import models.LanguageModel;
import textparser.ContextFileParser;
import textparser.LiberalParser;

import java.util.List;
import java.io.File;
public class language {

    public static void main(String[] args){

        ContextFileParser parser = new LiberalParser();
        ModelLoader modelLoader = new ParallelModelLoader(0, 0.001, parser);
        List<LanguageModel> languageModels = modelLoader.loadModels("languageModels");
        String textToAnalise = readText("languageModels/EN");
        System.out.println(languageModels.get(0).bitEstimate(textToAnalise));

    }

    private static String readText(String fileName){
        ContextFileParser parser = new LiberalParser();
        return parser.parse(new File(fileName));
    }
}
