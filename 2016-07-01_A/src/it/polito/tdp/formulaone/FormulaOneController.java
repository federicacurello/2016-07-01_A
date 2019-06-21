package it.polito.tdp.formulaone;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.formulaone.model.Driver;
import it.polito.tdp.formulaone.model.Model;
import it.polito.tdp.formulaone.model.Season;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FormulaOneController {
	
	Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Integer> boxAnno;

    @FXML
    private TextField textInputK;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	if(boxAnno.getValue()==null) {
    		txtResult.setText("Devi selezionare un anno!");
    		return;
    	}
    	model.creaGrafo(boxAnno.getValue());
    	txtResult.setText("Miglior pilota nell'anno selezionato: "+model.trovaMigliore() +" con ben "+ model.trovaMigliore().getPunti()+" punti");
    }

    @FXML
    void doTrovaDreamTeam(ActionEvent event) {
    	if(textInputK.getText()==null) {
    		txtResult.setText("Devi scrivere un numero!");
    		return;
    	}
    	try {
    		int K= Integer.parseInt(textInputK.getText());
    	txtResult.appendText("\nDream team : \n");
    	for(Driver d: model.dreamTeam(K)) {
    		txtResult.appendText(d.toString() + " - ");
    		
    	}} catch( NumberFormatException e) {
    		txtResult.appendText("Errore, inserire un numero!");
    		return;
    	}
    	

    }

    @FXML
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK != null : "fx:id=\"textInputK\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FormulaOne.fxml'.";

    }
    
    public void setModel(Model model){
    	this.model = model;
    	setBoxAnno();
    }

	private void setBoxAnno() {
		boxAnno.getItems().addAll(model.anni());
		
	}
}
