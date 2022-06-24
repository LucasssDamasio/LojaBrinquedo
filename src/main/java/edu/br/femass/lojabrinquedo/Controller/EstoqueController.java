package edu.br.femass.lojabrinquedo.Controller;

import edu.br.femass.lojabrinquedo.Dao.BrinquedoDao;
import edu.br.femass.lojabrinquedo.Model.Brinquedo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EstoqueController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AtualizarBrinquedo();
    }
    @FXML
    private Button BtnMenu;

    @FXML
    private ListView<Brinquedo> LstBrinquedo;

    public void AtualizarBrinquedo() {
        List<Brinquedo> brinquedos=new ArrayList<>();
        try {
            brinquedos= new BrinquedoDao().listar();
        }catch (Exception e){
            brinquedos= new ArrayList<Brinquedo>();
        }
        ObservableList<Brinquedo> brinquedoObservableListObservableList= FXCollections.observableArrayList(brinquedos);
        LstBrinquedo.setItems(brinquedoObservableListObservableList);

    }

    @FXML
    private void  BtnMenu_Action(ActionEvent evento){
        FXMLLoader fx= new FXMLLoader(EstoqueController.class.getResource("/edu/br/femass/lojabrinquedo/main.fxml"));
        try {
            Scene s = new Scene(fx.load());
            Stage st = new Stage();
            st = (Stage)((Node)evento.getSource()).getScene().getWindow();
            st.setTitle("Menu Inicial");
            st.setScene(s);
            st.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
