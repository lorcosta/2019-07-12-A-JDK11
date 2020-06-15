/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.FoodCalories;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtPorzioni"
    private TextField txtPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalorie"
    private Button btnCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="boxFood"
    private ComboBox<Food> boxFood; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Creazione grafo...\n");
    	String porzioniString=this.txtPorzioni.getText();
    	Integer porzioni=null;
    	try {
    		porzioni=Integer.parseInt(porzioniString);
    	}catch(NumberFormatException e) {
    		e.printStackTrace();
    		this.txtResult.appendText("ATTENZIONE! Il valore inserito per il campo porzioni non e' un numero.");
    		throw new NumberFormatException();
    	}
    	model.creaGrafo(porzioni);
    	List<Food> cibi=model.getCibi();
    	if(cibi!=null) {
    		this.boxFood.getItems().addAll(cibi);
    		this.txtResult.appendText("GRAFO CREATO CON SUCCESSO!\n "+model.getNumArchi()+" ARCHI e "+model.getNumVertici()+" VERTICI.\n");
    	}
    	
    }
    
    @FXML
    void doCalorie(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Analisi calorie...\n");
    	Food source=this.boxFood.getValue();
    	if(source==null) {
    		this.txtResult.appendText("ATTENZIONE! Nessun cibo selezionato nel menu' a tendina.\n");
    		return;
    	}
    	List<FoodCalories> congiunti=model.analisiCalorie(source);
    	if(congiunti==null) {
    		this.txtResult.appendText("Errore nell'analisi dei cibi congiunti a quello selezionato.\n");
    		return;
    	}
    	if(congiunti.size()==0) {
    		this.txtResult.appendText("Nessun cibo con calorie congiunte.\n");
    		return;
    	}
    	this.txtResult.appendText("Ecco i cibi congiunti con le loro calorie congiunte:\n");
    	for(int i=0;i<5 && i<congiunti.size();i++) {
    		this.txtResult.appendText("-"+congiunti.get(i).toString()+"\n");
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Simulazione...\n");
    	String kString=this.txtK.getText();
    	Integer k=null;
    	try {
    		k=Integer.parseInt(kString);
    	}catch(NumberFormatException e) {
    		e.printStackTrace();
    		this.txtResult.appendText("ATTENZIONE! Il valore inserito per il campo K non e' un numero intero.");
    		throw new NumberFormatException();
    	}
    	if(k<1 || k>10) {
    		this.txtResult.appendText("ATTENZIONE! Il numero K deve essere un intero compreso tra 1 e 10.");
    		return;
    	}
    	Food source=this.boxFood.getValue();
    	if(source==null) {
    		this.txtResult.appendText("ATTENZIONE! Nessun cibo selezionato nel menu' a tendina.\n");
    		return;
    	}
    	String messaggio=model.simula(k,source);
    	if(messaggio!=null) {
    		this.txtResult.appendText(messaggio);
    		return;
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtPorzioni != null : "fx:id=\"txtPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCalorie != null : "fx:id=\"btnCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxFood != null : "fx:id=\"boxFood\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
