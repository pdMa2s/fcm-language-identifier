import models.LanguageModel;

import java.util.List;

public interface ModelLoader {
    List<LanguageModel> loadModels(String dirPath);
}
