package graphPackage;
	
import java.util.Random;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	ObservableList<PieChart.Data> pieChartData;
	static Random r = new Random();
	int appleNumber=10;
	PieChart chart;
	Scene scene;
	
	@Override
	public void start(Stage stage) {
        scene = new Scene(new Group());
        stage.setTitle("Imported Fruits");
        stage.setWidth(500);
        stage.setHeight(500);		
        pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Grapefruit", 13),
                new PieChart.Data("Oranges", 25),
                new PieChart.Data("Plums", 10),
                new PieChart.Data("Pears", 22),
                new PieChart.Data("Apples", appleNumber));
        chart = new PieChart(pieChartData);
        chart.setTitle("Imported Fruits");
        
        
        Button button = new Button();
        button.setText("APPLE");
        button.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		chart.getData().removeAll(pieChartData);
        		appleNumber = r.nextInt(200);
        		chart.setTitle("Imported Fruits");
        		pieChartData=FXCollections.observableArrayList(
                        new PieChart.Data("Grapefruit", 13),
                        new PieChart.Data("Oranges", 25),
                        new PieChart.Data("Plums", 10),
                        new PieChart.Data("Pears", 22),
                        new PieChart.Data("Apples", appleNumber));
        		 chart.getData().addAll(pieChartData);
        	}
        });
        BorderPane borderPane = new BorderPane();
        borderPane.setBottom(button);
        borderPane.setAlignment(button, Pos.BOTTOM_CENTER);
        borderPane.setTop(chart);
        
        ((Group) scene.getRoot()).getChildren().add(borderPane);

        stage.setScene(scene);
        stage.show();
        
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
