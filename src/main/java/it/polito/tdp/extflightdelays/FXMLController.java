package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Model;
import it.polito.tdp.extflightdelays.model.Vicino;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

//controller turno A --> switchare ai branch master_turnoB o master_turnoC per turno B o C

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextField distanzaMinima;

    @FXML
    private Button btnAnalizza;

    @FXML
    private ComboBox<Airport> cmbBoxAeroportoPartenza;

    @FXML
    private Button btnAeroportiConnessi;

    @FXML
    private TextField numeroVoliTxtInput;

    @FXML
    private Button btnCercaItinerario;

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {
    	this.txtResult.clear();
    	Integer miglia;
    	try {
    		miglia = Integer.parseInt(this.distanzaMinima.getText());
    		this.model.createGraph(miglia);
    		this.txtResult.appendText("Grafo creato\n");
    		this.txtResult.appendText("#Vertici: "+this.model.nVertici()+"\n");
    		this.txtResult.appendText("#Archi: "+this.model.nArchi()+"\n");
    		this.cmbBoxAeroportoPartenza.getItems().addAll(this.model.vertici());
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Inserisci numero miglia con formato corretto!");
    		return;
    	}

    }

    @FXML
    void doCalcolaAeroportiConnessi(ActionEvent event) {
    	this.txtResult.clear();
    	Airport a = this.cmbBoxAeroportoPartenza.getValue();
    	if(a==null) {
    		this.txtResult.setText("Scegli un aeroporto");
    		return;
    	}
    	List<Vicino> vicini = this.model.vicini(a);
    	for(Vicino v : vicini) {
    		this.txtResult.appendText(v.toString()+"\n");
    	}

    }

    @FXML
    void doCercaItinerario(ActionEvent event) {
    	this.txtResult.clear();
    	Airport a = this.cmbBoxAeroportoPartenza.getValue();
    	if(a==null) {
    		this.txtResult.setText("Scegli un aeroporto");
    		return;
    	}
    	Integer migliaDisponibili;
    	try {
    		migliaDisponibili = Integer.parseInt(this.numeroVoliTxtInput.getText());
    		this.model.cercaCammino(a,migliaDisponibili);
    		List<Airport> cammino = this.model.getCammino();
    		if(cammino.size() == 0) {
    			this.txtResult.appendText("Cammino non trovato");
    			return;
    		}
    		for(Airport ar : cammino) {
    			this.txtResult.appendText(ar.toString()+"\n");
    		}
    		this.txtResult.appendText("Miglia percorsi: "+this.model.getMigliaPercorsi());
    		
    		
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Inserisci numero miglia disponibili con formato corretto!");
    		return;
    	}

    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert distanzaMinima != null : "fx:id=\"distanzaMinima\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxAeroportoPartenza != null : "fx:id=\"cmbBoxAeroportoPartenza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAeroportiConnessi != null : "fx:id=\"btnAeroportiConnessi\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert numeroVoliTxtInput != null : "fx:id=\"numeroVoliTxtInput\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnCercaItinerario != null : "fx:id=\"btnCercaItinerario\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
