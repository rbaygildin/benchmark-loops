package org.rbaygildin.controller;

import org.rbaygildin.benchmark.BenchmarkLog;
import org.rbaygildin.benchmark.BenchmarkMeasurer;
import org.rbaygildin.benchmark.MethodTestLog;
import org.rbaygildin.infrastructure.annotation.JavaFXController;
import org.rbaygildin.launcher.abstract_launcher.IStreamLauncher;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import org.rbaygildin.infrastructure.annotation.JavaFXController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Main controller provides UI-interface for user and handles events
 *
 * @author Yarnykh Roman (rvyarnykh@edu.hse.ru, egdeveloper@mail.ru)
 * @version 1.0
 */
@JavaFXController
public class MainController implements Initializable{

    @Autowired
    private ConfigurableApplicationContext context;

    @Autowired
    @Qualifier("benchmarkMeasurer")
    private BenchmarkMeasurer benchmark;

    @Autowired
    @Qualifier("parallelStreams")
    private IStreamLauncher psTester;

    @Autowired
    @Qualifier("streams")
    private IStreamLauncher sTester;

    @Autowired
    @Qualifier("classicLoop")
    private IStreamLauncher clTester;

    @Autowired
    @Qualifier("forEach")
    private IStreamLauncher feTester;

    @Autowired
    @Qualifier("forkJoins")
    private IStreamLauncher fjTester;

    @FXML
    private TextArea benchmarkLog_TextArea;

    @FXML
    private NumberAxis totalTime_Axis;

    @FXML
    private CategoryAxis method_Axis;

    @FXML
    private BarChart<String, Number> benchmark_BarChart;

    @FXML
    public void onExitMenuItem_Clicked(){
        Platform.exit();
    }

    @FXML
    public void onAboutMenuItem_Clicked(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О приложении");
        alert.setHeaderText(null);
        alert.setContentText("©Angie Benchmark - программа для тестирования и профайлинга " +
                "коллекций и стримов\n" +
                "Автор: Ярных Роман, студент БПИ141(2)\n" +
                "Проект: домашняя работа за 4 модуль\n" +
                "Версия: 1.0\n" +
                "Использованные технологии: Java 8, JavaFX, Spring, Maven, JUnit, JDBC");
        alert.showAndWait();
    }

    @FXML
    public void onStartBenchmarkButton_Clicked()
    {
        benchmark.clearLog();
        clTester.launch();
        feTester.launch();
        sTester.launch();
        psTester.launch();
        fjTester.launch();
        Map<Class, BenchmarkLog> report = benchmark.retrieveReport();
        visualizeBenchmarkReport(report);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void visualizeBenchmarkReport(Map<Class, BenchmarkLog> report){
        benchmarkLog_TextArea.clear();
        benchmark_BarChart.getData().clear();
        method_Axis.setLabel("Метод");
        totalTime_Axis.setLabel("Общее время выполнения (наносекунды)");
        for(BenchmarkLog benchmarkLog : report.values()){
            XYChart.Series series = new XYChart.Series();
            series.setName(benchmarkLog.getMessage());
            benchmarkLog_TextArea.appendText("=== " + benchmarkLog.getMessage() + " ===\n");
            for(MethodTestLog methodTestLog : benchmarkLog.getMethodTestLogs()){
                benchmarkLog_TextArea.appendText("> " + methodTestLog.getMethod().getName() + "()\n");
                benchmarkLog_TextArea.appendText("| Message = " + methodTestLog.getMessage() + "\n");
                benchmarkLog_TextArea.appendText("| Total time = " + methodTestLog.getTotalTime() + "\n");
                benchmarkLog_TextArea.appendText("------------------------------------\n");
                series.getData().add(new XYChart.Data(methodTestLog.getMethod().getName(), methodTestLog.getTotalTime()));
            }
            benchmarkLog_TextArea.appendText("\n\n");
            benchmark_BarChart.getData().add(series);
        }
    }
}
