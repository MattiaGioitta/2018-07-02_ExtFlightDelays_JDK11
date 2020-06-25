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

//controller turno C --> switchare ai branch master_turnoA o master_turnoB per turno A o B

public class FXMLController {
	
	private Model model;

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextField compagnieMinimo;

    @FXML
    private Button btnAnalizza;

    @FXML
    private ComboBox<Airport> cmbBoxAeroportoPartenza;

    @FXML
    private Button btnAeroportiConnessi;

    @FXML
    private ComboBox<Airport> cmbBoxAeroportoDestinazione;

    @FXML
    private TextField numeroTratteTxtInput;

    @FXML
    private Button btnCercaItinerario;

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {
    	this.txtResult.clear();
    	Integer x;
    	try {
    		x = Integer.parseInt(this.compagnieMinimo.getText());
    		this.model.createGraph(x);
    		this.txtResult.appendText("Grafo creato con\n");
    		this.txtResult.appendText(String.format("#Vertici: %d\n#Archi: %d\n", this.model.nVertici(),this.model.nArchi()));
    		this.cmbBoxAeroportoPartenza.getItems().addAll(this.model.getVertici());
    		
    	}catch(NumberFormatException e) {
    		this.txtResult.appendText("Inserisci un x con formato corretto!");
    		return;
    	}

    }

    @FXML
    void doCalcolaAeroportiConnessi(ActionEvent event) {
    	this.txtResult.clear();
    	Airport scelto = this.cmbBoxAeroportoPartenza.getValue();
    	if(scelto == null) {
    		this.txtResult.appendText("Scegli un aeroporto dal menu a tendina!");
    		return;
    	}
    	List<Vicino> vicini = this.model.vicini(scelto);
    	for(Vicino v : vicini) {
    		this.txtResult.appendText(v.toString()+"\n");
    	}
    	this.cmbBoxAeroportoDestinazione.getItems().addAll(this.model.getVertici());

    }

    @FXML
    void doCercaItinerario(ActionEvent event) {
    	this.txtResult.clear();
    	Airport source = this.cmbBoxAeroportoPartenza.getValue();
    	Airport destination = this.cmbBoxAeroportoDestinazione.getValue();
    	if(source == null) {
    		this.txtResult.appendText("Scegli un aeroporto dal menu a tendina!");
    		return;
    	}
    	if(destination == null) {
    		this.txtResult.appendText("Scegli un aeroporto dal menu a tendina!");
    		return;
    	}
    	Integer t;
    	try {
    		t = Integer.parseInt(this.numeroTratteTxtInput.getText());
    		this.model.findPath(source,destination,t);
    		List<Airport> path = this.model.getPath();
    		if(path == null || path.size()==0) {
    			this.txtResult.appendText("Cammino non trovato");
    			return;
    		}
    		this.txtResult.appendText("Numero totale voili: "+this.model.getPeso()+"\n");
    		for(Airport a : path) {
    			this.txtResult.appendText(a.toString()+"\n");
    		}
    	}catch(NumberFormatException e) {
    		this.txtResult.appendText("Inserisci un t con formato corretto!");
    		return;
    	}

    	
    	

    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert compagnieMinimo != null : "fx:id=\"compagnieMinimo\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxAeroportoPartenza != null : "fx:id=\"cmbBoxAeroportoPartenza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAeroportiConnessi != null : "fx:id=\"btnAeroportiConnessi\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxAeroportoDestinazione != null : "fx:id=\"cmbBoxAeroportoDestinazione\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert numeroTratteTxtInput != null : "fx:id=\"numeroTratteTxtInput\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnCercaItinerario != null : "fx:id=\"btnCercaItinerario\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
