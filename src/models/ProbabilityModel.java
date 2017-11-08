package models;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ProbabilityModel {
    private Map<String, Map<Character, Double>> probabilityMultiModel;
    private Map<Character, Double> probabilityUniModel;

    private Set<Character> alphabet;
    private ContextModel contextModel;
    private double alpha;
    private Character unknownCharacter = '\u0000';
    
    public ProbabilityModel(ContextModel model, double alpha){
        this.alphabet = model.getAlphabet();
        this.alphabet.add(unknownCharacter);
        this.contextModel = model;

        if(alpha < 0 || alpha > 1)
            throw new IllegalArgumentException("Alpha cant be less than zero or greater than one");
        this.alpha = alpha;

        if (contextModel.getOrder() == 0) {
            probabilityUniModel = new HashMap<>();
            fillProbabilityUniModel();
        }
        else{
            probabilityMultiModel = new HashMap<>();
            fillProbabilityMultiModel();
        }
    }


    public double getProbability(String term, char character){
        Map<Character, Double> probabilityFollow = probabilityMultiModel.get(term);
        if (probabilityFollow == null)
            return (double) 1/alphabet.size()-1;
        else {
            Double probability = probabilityFollow.get(character);
            return probability == null ? probabilityFollow.get(unknownCharacter) : probability;
        }
    }

    public double getProbability(char character){
        Double probability = probabilityUniModel.get(character);
        return probability == null ? probabilityUniModel.get(unknownCharacter) : probability;
    }
    
    private void fillProbabilityMultiModel(){
        for(String term : contextModel.getTermsForOrderHigherThanZero()) {

            Map<Character, Integer> ocurrences = contextModel.getOcurrencesForOrderHigherThanZero(term);
            int totalOcurrences = getTotalOcurencesOfRow(ocurrences);
            for (char c : alphabet) {
                Integer nrOcurrences = ocurrences.get(c);
                if(nrOcurrences == null)
                    nrOcurrences = 0;
                fillCharProbabilities(term, c, totalOcurrences, nrOcurrences);
            }
        }

    }

    private void fillProbabilityUniModel(){
        Map<Character, Integer> ocurrences = contextModel.getOcurrencesForOrderEqualToZero();
        int totalOcurrences = getTotalOcurencesOfRow(ocurrences);
        for(Character term : alphabet) {
            int nrOccurrences = ocurrences.get(term) == null ? 0 : ocurrences.get(term); 
            probabilityUniModel.put(term, probabilityOfAChar(totalOcurrences, nrOccurrences));
        }
    }

    private void fillCharProbabilities(String term ,char c,int total, int nrOcurrences){
        Map<Character, Double> probabilities;
        if(probabilityMultiModel.containsKey(term)) 
            probabilities = probabilityMultiModel.get(term);
        
        else
            probabilities = new HashMap<>();
        
        probabilities.put(c,probabilityOfAChar(total, nrOcurrences));
        probabilityMultiModel.put(term,probabilities);
    }
    private int getTotalOcurencesOfRow(Map<Character, Integer> ocurrences){
        int sum = 0;
        for(char k : ocurrences.keySet()){
            sum += ocurrences.get(k);
        }
        return sum;
    }


    private double probabilityOfAChar(int total, int nrOcurrences){
        return (nrOcurrences + alpha)/(total +(alpha *alphabet.size()));
    }
    
    public int getOrder() {
        return contextModel.getOrder();
    }

    @Override
    public String toString(){
        if(contextModel.getOrder() == 0)
            return probabilityUniModel.toString();
        StringBuilder sb = new StringBuilder();
        for(String k : probabilityMultiModel.keySet()){
            sb.append(k+":"+probabilityMultiModel.get(k)+"\n");
        }
        return sb.toString();
    }
}
