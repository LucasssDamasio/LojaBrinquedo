package edu.br.femass.lojabrinquedo.Controller;

import edu.br.femass.lojabrinquedo.Dao.FornecedorDao;
import edu.br.femass.lojabrinquedo.Model.Brinquedo;
import edu.br.femass.lojabrinquedo.Model.Fornecedor;
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

public class FornecedorController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AtualizarFornecedor();

    }
    private FornecedorDao fornecedorDao= new FornecedorDao();

    public void AtualizarFornecedor() {
        List<Fornecedor> fornecedores=new ArrayList<>();
        try {
            fornecedores=fornecedorDao.listar();
        }catch (Exception e){
            fornecedores= new ArrayList<Fornecedor>();
        }
        ObservableList<Fornecedor> fornededorObservableList= FXCollections.observableArrayList(fornecedores);
        LstFornecedor.setItems(fornededorObservableList);

    }



    @FXML
    private Button BtnMenu;
    @FXML
    private TextField TxtCNPJ;

    @FXML
    private TextField TxtEmail1;

    @FXML
    private TextField TxtNomeFornecedor;

    @FXML
    private ListView<Fornecedor> LstFornecedor;

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
    private Button BtnExcluirForn;

    @FXML
    private void BtnExcluirForn_Action(ActionEvent evento) throws Exception {
        if(LstFornecedor.getSelectionModel().getSelectedItem()!=null){
            fornecedorDao.excluir(LstFornecedor.getSelectionModel().getSelectedItem());
            AtualizarFornecedor();

        }

    }
    @FXML
    private Button BtnGravarForn;
    @FXML
    private void BtnGravarForn_Action(ActionEvent evento){
        Fornecedor fornecedor= new Fornecedor();
        fornecedor.setNome(TxtNomeFornecedor.getText());
        fornecedor.setCnpj(TxtCNPJ.getText());
        fornecedor.setEmail(TxtEmail1.getText());
        try{
            fornecedorDao.gravar(fornecedor);


        }catch (Exception e){
            e.printStackTrace();

        }
        AtualizarFornecedor();
        TxtNomeFornecedor.setText("");
        TxtCNPJ.setText("");
        TxtEmail1.setText("");

    }
    @FXML
    private Button BtnAlterarForn;

    @FXML

    private void BtnAlterarForn_Action(ActionEvent evento) throws Exception {

        if(LstFornecedor.getSelectionModel().getSelectedItem()!=null) {
            Fornecedor fornecedor= LstFornecedor.getSelectionModel().getSelectedItem();
            fornecedor.setEmail(TxtEmail1.getText());
            fornecedor.setNome(TxtNomeFornecedor.getText());
            fornecedor.setCnpj(TxtCNPJ.getText());
            fornecedorDao.aLterar(fornecedor);
            AtualizarFornecedor();
        }

    }

    @FXML
    private void LstFornecedorOnClick(){
        Fornecedor fornecedor= LstFornecedor.getSelectionModel().getSelectedItem();
        if(fornecedor != null){
            TxtCNPJ.setText(fornecedor.getCnpj());
            TxtNomeFornecedor.setText(fornecedor.getNome());
            TxtEmail1.setText(fornecedor.getEmail());
        }
    }



}
