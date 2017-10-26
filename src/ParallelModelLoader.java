import models.LanguageModel;
import textparser.ContextFileParser;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParallelModelLoader implements ModelLoader {
    private File[] modelFiles;
    private List<LanguageModel> models;
    private int order;
    private double alpha;
    private ContextFileParser parser;

    public ParallelModelLoader(int order, double alpha, ContextFileParser parser) {
        this.order = order;
        this.alpha = alpha;
        this.parser = parser;
        this.models = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public List<LanguageModel> loadModels(String dirPath) {
        File modelsDirectory = new File(dirPath);
        checkDirectoryExists(modelsDirectory);
        modelFiles = modelsDirectory.listFiles();
        checkFileLength(modelFiles);
        Thread[] readers = initializeThreads(modelFiles.length);
        launchThreadsAndWait(readers);
        return models;
    }

    private Thread[] initializeThreads(int threadNumber){
        Thread[] workers = new  WorkerReader[threadNumber];
        for(int i = 0; i< workers.length; i++){
            workers[i] = new WorkerReader(modelFiles[i], order, alpha,parser ,models);
        }
        return workers;
    }

    private void launchThreadsAndWait(Thread[] threads){
        for(Thread th : threads){
            th.start();
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkFileLength(File[] files){
        if(files.length == 0){
            System.err.println("There is no language files to process!");
            System.exit(2);
        }
    }

    private void checkDirectoryExists(File dir){
        if(!dir.isDirectory() || !dir.exists()){
            System.err.println(dir.getName() +" is not a directory!");
            System.exit(2);
        }

    }



}
