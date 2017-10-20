import models.LanguageModel;
import textparser.ContextFileParser;
import textparser.LiberalParser;

import java.util.List;

public class language {

    public static void main(String[] args){

        ContextFileParser parser = new LiberalParser();
        ModelLoader modelLoader = new ParallelModelLoader(2, 0, parser);
        List<LanguageModel> languageModels = modelLoader.loadModels("docs");
        System.out.println(languageModels);
    }
}
