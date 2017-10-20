package models;

public class LanguageModel {

    private ProbabilityModel probabilityModel;
    private String language;

    public LanguageModel(ProbabilityModel probabilityModel, String language) {
        this.probabilityModel = probabilityModel;
        this.language = language;
    }

    public double bitEstimate(String text){
        int order =  probabilityModel.getOrder();
        if(order == 0)
            return bitEstimateForOrderZero(text);
        return bitEstimateForOrderGreaterThanZero(text, order);
    }
    public double bitEstimateForOrderZero(String text){
        double sum = 0;
        for (int i = 0; i < text.length(); i++){
            char character = text.charAt(i);
            sum -= log2(probabilityModel.getProbability(character));
        }
        return sum;
    }
    public double bitEstimateForOrderGreaterThanZero(String text, int order){
        double sum = 0;
        for (int i = 0; i < text.length() - (order+1); i++){
            String symbol = text.substring(i,i+order);
            char character = text.charAt(i+order);
            sum -= log2(probabilityModel.getProbability(symbol, character));
        }
        return sum;
    }
    private double log2( double a )
    {
        return a == 0 ? 0.00001 : logb(a,2);
    }
    private double logb( double a, double b )
    {
        return Math.log(a) / Math.log(b);
    }

    @Override
    public String toString() {
        return "language='" + language + '\'';
    }
}
