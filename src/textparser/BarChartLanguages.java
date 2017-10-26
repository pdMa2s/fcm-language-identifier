package textparser;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import models.LanguageModel;

import java.util.Map;

public class BarChartLanguages extends Application {
    private Map<LanguageModel, Double> estimates;

    public BarChartLanguages(Map<LanguageModel, Double> estimates, String textToAnalise) {
        this.estimates = estimates;
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Bar Chart Sample");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc =
                new BarChart<>(xAxis, yAxis);
        bc.setTitle("Country Summary");
        xAxis.setLabel("Country");
        yAxis.setLabel("Value");

        XYChart.Series series1 = new XYChart.Series();
        for(LanguageModel model : estimates.keySet()){
            series1.getData().add(new XYChart.Data(model.getLanguage(), estimates.get(model) ));
        }


        Scene scene  = new Scene(bc,700,500);
        bc.getData().addAll(series1);
        stage.setScene(scene);
        stage.show();
    }

    public void main(String[] args) {
        launch(args);
    }
}
