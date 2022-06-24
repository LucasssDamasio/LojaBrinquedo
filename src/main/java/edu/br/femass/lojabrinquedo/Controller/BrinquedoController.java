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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BrinquedoController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AtualizarBrinquedo();

    }
    public void AtualizarBrinquedo() {
        List<Brinquedo> brinquedos=new ArrayList<>();
        try {
            brinquedos=brinquedoDao.listar();
        }catch (Exception e){
            brinquedos= new ArrayList<Brinquedo>();
        }
        ObservableList<Brinquedo> brinquedoObservableListObservableList= FXCollections.observableArrayList(brinquedos);
        LstBrinquedo.setItems(brinquedoObservableListObservableList);

    }

    private BrinquedoDao brinquedoDao= new BrinquedoDao();
    @FXML
    private ListView<Brinquedo>  LstBrinquedo;

    @FXML
    private Button BtnMenu;
    @FXML
    private TextField TxtNomeBrinquedo;

    @FXML
    private TextField  TxtPrecoVenda;

    @FXML
    private void  BtnMenu_Action(ActionEvent evento){
        FXMLLoader fx= new FXMLLoader(VendaController.class.getResource("/edu/br/femass/lojabrinquedo/main.fxml"));
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

    @FXML
    private Button BtnExcluirBrin;

    @FXML
    private void BtnExcluirBrin_Action(ActionEvent evento) throws Exception {
        if(LstBrinquedo.getSelectionModel().getSelectedItem()!=null){
            brinquedoDao.excluir(LstBrinquedo.getSelectionModel().getSelectedItem());
            AtualizarBrinquedo();


        }

    }
    @FXML
    private Button BtnGravarBrin;
    @FXML
    private void BtnGravarBrin_Action(ActionEvent evento){
        Brinquedo brinquedo= new Brinquedo();
        brinquedo.setNome(TxtNomeBrinquedo.getText());
        brinquedo.setPreco_venda(Double.parseDouble(TxtPrecoVenda.getText()));

        try{
            brinquedoDao.gravar(brinquedo);


        }catch (Exception e){
            e.printStackTrace();

        }
        AtualizarBrinquedo();
        TxtNomeBrinquedo.setText("");
        TxtPrecoVenda.setText("");

    }
    @FXML
    private Button BtnAlterarBrin;

    @FXML
    private void BtnAlterarBrin_Action(ActionEvent evento) throws Exception {

            if(LstBrinquedo.getSelectionModel().getSelectedItem() !=null) {
                Brinquedo brinquedo = LstBrinquedo.getSelectionModel().getSelectedItem();
                brinquedo.setNome(TxtNomeBrinquedo.getText());
                brinquedo.setPreco_venda(Double.parseDouble(TxtPrecoVenda.getText()));

                brinquedoDao.aLterar(brinquedo);
                AtualizarBrinquedo();
            }

    }

    @FXML
    private void LstBrinquedoOnClick(){
        Brinquedo brinquedo = LstBrinquedo.getSelectionModel().getSelectedItem();
        if(brinquedo != null){
            TxtNomeBrinquedo.setText(brinquedo.getNome());
            TxtPrecoVenda.setText(String.valueOf(brinquedo.getPreco_venda()));
        }
    }
}
