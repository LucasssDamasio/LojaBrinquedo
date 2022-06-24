package edu.br.femass.lojabrinquedo.Controller;

import edu.br.femass.lojabrinquedo.Dao.BrinquedoDao;
import edu.br.femass.lojabrinquedo.Dao.CompraDAO;
import edu.br.femass.lojabrinquedo.Dao.FornecedorDao;
import edu.br.femass.lojabrinquedo.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CompraController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Fornecedor> listafornecedor = new ArrayList<>();
        try {
            listafornecedor= new FornecedorDao().listar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObservableList<Fornecedor> fornecedorObservableList = FXCollections.observableArrayList(listafornecedor);
        CboFornecedor.setItems(fornecedorObservableList);

        List<Brinquedo> listabrinquedo = new ArrayList<>();
        try {
            listabrinquedo= new BrinquedoDao().listar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObservableList<Brinquedo> brinquedoObservableList = FXCollections.observableArrayList(listabrinquedo);
        CboBrinquedo.setItems(brinquedoObservableList);


        AtualizarCompra();


    }
    private BrinquedoDao brinquedoDao= new BrinquedoDao();

    public void AtualizarCompra() {
        List<Compra> compras = new ArrayList<>();

        try {
            compras=compraDAO.listar();
        }catch (Exception e){
            e.printStackTrace();
        }
        ObservableList<Compra> compraObservableList= FXCollections.observableArrayList(compras);
        LstCompras.setItems(compraObservableList);
    }


    @FXML
    private Button BtnMenu;
    @FXML
    private TextField Txtnome;

    @FXML
    private TextField TxtUnidades;

    @FXML
    private TextField TxtValorTotal;

    @FXML
    private TextField TxtValorUnidade;
    private CompraDAO compraDAO= new CompraDAO();

    public void AtualizarCliente() {
        List<Compra> compras=  new ArrayList<>();
        try {

            compras=compraDAO.listar();
        }catch (Exception e){
            compras= new ArrayList<Compra>();
        }
        ObservableList<Compra> compraObservableList= FXCollections.observableArrayList(compras);
        LstCompras.setItems(compraObservableList);
    }


    @FXML
    private void  BtnMenu_Action(ActionEvent evento){
        FXMLLoader fx= new FXMLLoader(CompraController.class.getResource("/edu/br/femass/lojabrinquedo/main.fxml"));
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
    private ListView<Compra> LstCompras;

    @FXML
    private Button BtnExcluir;

    @FXML
    private void BtnExcluir_Action(ActionEvent evento) throws Exception {
        if(LstCompras.getSelectionModel().getSelectedItem()!=null){
            compraDAO.excluir(LstCompras.getSelectionModel().getSelectedItem());
            AtualizarCompra();


        }


    }

    @FXML
    private Button BtnGravar;
    @FXML
    private void BtnGravar_Action(ActionEvent evento){
        Compra compra = new Compra();
        List<Brinquedo> listabrinquedo = new ArrayList<>();
        listabrinquedo.add(CboBrinquedo.getSelectionModel().getSelectedItem());
        compra.setBrinquedo(listabrinquedo);
        compra.setFornecedor(CboFornecedor.getSelectionModel().getSelectedItem());
        compra.setQtd(Integer.valueOf(TxtUnidades.getText()));
        compra.setValor_unitario(Double.valueOf(TxtValorUnidade.getText()));
        compra.setValor_total(Double.valueOf(TxtValorTotal.getText()));
        compra.setData(LocalDate.now());
        try {

            compraDAO.gravar(compra);
            CboBrinquedo.getSelectionModel().clearSelection();
            CboFornecedor.getSelectionModel().clearSelection();
            TxtUnidades.setText("");
            TxtValorUnidade.setText("");
            TxtValorTotal.setText("");

        } catch (Exception e) {
            e.printStackTrace();
        }

        AtualizarCompra();





    }
    @FXML
    private Button BtnAlterar;

    @FXML
    private void BtnAlterar_Action(ActionEvent evento) throws Exception {
        if(LstCompras.getSelectionModel().getSelectedItem()!=null) {
            Compra compra = LstCompras.getSelectionModel().getSelectedItem();
            List<Brinquedo> listabrinquedo1 = new ArrayList<>();
            listabrinquedo1.add(CboBrinquedo.getSelectionModel().getSelectedItem());
            compra.setBrinquedo(listabrinquedo1);
            compra.setFornecedor(CboFornecedor.getSelectionModel().getSelectedItem());
            compra.setQtd(Integer.valueOf(TxtUnidades.getText()));
            compra.setValor_unitario(Double.valueOf(TxtValorUnidade.getText()));
            compra.setValor_total(Double.valueOf(TxtValorTotal.getText()));
            compra.setData(LocalDate.now());
            compraDAO.aLterar(compra);
            AtualizarCompra();
        }

    }
    @FXML
    private ComboBox<Fornecedor> CboFornecedor;

    @FXML
    private ComboBox<Brinquedo> CboBrinquedo;


    @FXML
    private void lstCompraOnClick(){
        Compra compra = LstCompras.getSelectionModel().getSelectedItem();
        if( compra != null){
            TxtUnidades.setText(compra.getQtd().toString());
            TxtValorTotal.setText(compra.getValor_total().toString());
            TxtValorUnidade.setText(compra.getValor_unitario().toString());
            CboFornecedor.getSelectionModel().select(compra.getFornecedor());
            CboBrinquedo.getSelectionModel().select(compra.getBrinquedo().get(0));
        }
    }

}
