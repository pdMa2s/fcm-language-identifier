
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import models.LanguageModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BarChartLanguages extends Application {

    private static Map<LanguageModel, Double> estimates;

    public void addEstimates(Map<LanguageModel, Double> estimates) {
        this.estimates = estimates;
    }

    @Override public void start(Stage stage) {
        stage.setTitle("Bit estimates for each language model");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc =
                new BarChart<>(xAxis, yAxis);
        bc.setTitle("Bit Estimates");
        xAxis.setLabel("Language");
        yAxis.setLabel("Nº of bits");

        List<XYChart.Series> seriesList = new ArrayList<>();

        for(LanguageModel model : estimates.keySet()){
            XYChart.Series series  = new XYChart.Series();
            series.setName(model.toString());
            series.getData().add(new XYChart.Data("", estimates.get(model)));
            seriesList.add(series);
        }



        Scene scene  = new Scene(bc,600,450);
        for(XYChart.Series sr : seriesList)
            bc.getData().add(sr);
        stage.setScene(scene);
        stage.show();
    }

    public void show() {
        launch();
    }
}
