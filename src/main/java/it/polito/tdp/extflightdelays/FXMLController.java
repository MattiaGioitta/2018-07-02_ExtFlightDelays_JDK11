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

//controller turno B --> switchare ai branch master_turnoA o master_turnoC per turno A o C

public class FXMLController {
	
	private Model model;

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextField voliMinimo;

    @FXML
    private Button btnAnalizza;

    @FXML
    private ComboBox<Airport> cmbBoxAeroportoPartenza;

    @FXML
    private Button btnAeroportiConnessi;

    @FXML
    private TextField numeroOreTxtInput;

    @FXML
    private Button btnOttimizza;

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {
    	this.txtResult.clear();
    	Integer x;
    	try {
    		x = Integer.parseInt(this.voliMinimo.getText());
    		this.model.createGraph(x);
    		this.txtResult.appendText("Grafo creato con:\n");
    		this.txtResult.appendText(String.format("#Vertici: %d\n#Archi: %d\n",this.model.nVertici(), this.model.nArchi()));
    		this.cmbBoxAeroportoPartenza.getItems().addAll(this.model.getVertici());
    	}catch(NumberFormatException e) {    

    		this.txtResult.setText("Inserisci un numero valido per i voli minimo");
    		return;
    	}

    }

    @FXML
    void doCalcolaAeroportiConnessi(ActionEvent event) {
    	this.txtResult.clear();
    	Airport scelto = this.cmbBoxAeroportoPartenza.getValue();
    	if(scelto == null) {
    		this.txtResult.setText("Scegli un aeroporto");
    		return;
    	}
    	List<Vicino> vicini = this.model.getVicini(scelto);
    	if(vicini.size() == 0 || vicini == null) {
    		this.txtResult.setText("nessun vicino per l'aeroporto scelto");
    		return;
    	}
    	for(Vicino v : vicini) {
    		this.txtResult.appendText(v.toString()+"\n");
    	}

    	
    }

    @FXML
    void doCercaItinerario(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert voliMinimo != null : "fx:id=\"voliMinimo\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxAeroportoPartenza != null : "fx:id=\"cmbBoxAeroportoPartenza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAeroportiConnessi != null : "fx:id=\"btnAeroportiConnessi\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert numeroOreTxtInput != null : "fx:id=\"numeroOreTxtInput\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnOttimizza != null : "fx:id=\"btnOttimizza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
