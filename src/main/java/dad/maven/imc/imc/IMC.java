package dad.maven.imc.imc;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class IMC extends Application{
	
	private Label peso;
	private Label kg;
	private Label altura;
	private Label cm;
	private Label IMC;
	private Label formula;
	private Label resultado;
	private TextField alturaText;
	private TextField pesoText;	
	private DoubleProperty alturaBinding = new SimpleDoubleProperty();
	private DoubleProperty pesoBinding = new SimpleDoubleProperty();
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		peso = new Label("Peso: ");
		kg = new Label(" kg");
		altura = new Label("Altura: ");
		cm = new Label(" cm");
		IMC = new Label("IMC: ");
		formula = new Label();
		resultado = new Label();
		
		pesoText = new TextField();
		pesoText.setMaxWidth(75);
		alturaText = new TextField();
		alturaText.setMaxWidth(75);
		
		HBox pesoBox = new HBox(5, peso, pesoText, kg);
		HBox alturaBox = new HBox(5, altura, alturaText, cm);
		HBox imcBox = new HBox(5, IMC, formula);	
		pesoBox.setAlignment(Pos.CENTER);
		alturaBox.setAlignment(Pos.CENTER);
		imcBox.setAlignment(Pos.CENTER);
		
		VBox root = new VBox(5, pesoBox, alturaBox, imcBox, resultado);
		root.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(root, 320, 200);
		primaryStage.setTitle("IMC.fxml");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Bindings.bindBidirectional(pesoText.textProperty(), pesoBinding, new NumberStringConverter());
		Bindings.bindBidirectional(alturaText.textProperty(), alturaBinding, new NumberStringConverter());
		DoubleBinding alturaDivisionBinding = alturaBinding.divide(100);
		DoubleBinding alturaCuadradoBinding = alturaDivisionBinding.multiply(alturaDivisionBinding); 
		DoubleBinding formulaResultadoBinding = pesoBinding.divide(alturaCuadradoBinding);
		
		formula.textProperty().bind(Bindings.concat(formulaResultadoBinding.asString()));
		formula.textProperty().addListener((o, ov, nv) -> {
		double formulaResultado = formulaResultadoBinding.doubleValue();
		if(formulaResultado<18.5) {
			resultado.setText("Bajo peso");
		}
		if(formulaResultado>=18.5 && formulaResultado<25) {
			resultado.setText("Normal");
		}
		if(formulaResultado>=25 && formulaResultado<30) {
			resultado.setText("Sobrepeso");
		}
		if(formulaResultado >= 30) {
			resultado.setText("Obeso");
		}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}
