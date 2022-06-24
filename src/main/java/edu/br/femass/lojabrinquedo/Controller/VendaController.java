package edu.br.femass.lojabrinquedo.Controller;

import edu.br.femass.lojabrinquedo.Dao.*;
import edu.br.femass.lojabrinquedo.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class VendaController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Cliente> listacliente = new ArrayList<>();
        try {

            listacliente= new ClienteDao().listar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObservableList<Cliente> clienteObservableList = FXCollections.observableArrayList(listacliente);
        CboClientes.setItems(clienteObservableList);


        List<Brinquedo> listabrinquedo = new ArrayList<>();
        try {
            listabrinquedo= new BrinquedoDao().listar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObservableList<Brinquedo> brinquedoObservableList = FXCollections.observableArrayList(listabrinquedo);
        CboBrinquedo.setItems(brinquedoObservableList);

        AtualizarVenda();

    }
    public void AtualizarVenda() {
        List<Venda> vendas=new ArrayList<>();
        try {
            vendas=vendaDao.listar();
        }catch (Exception e){
            e.printStackTrace();
        }
        ObservableList<Venda> vendaObservableList= FXCollections.observableArrayList(vendas);
        LstVendas.setItems(vendaObservableList);
        LstVendas.refresh();

    }
    @FXML
    private ComboBox<Cliente> CboClientes;

    @FXML
    private ComboBox<Brinquedo> CboBrinquedo;


    @FXML
    private TextField TxtUnidades1;

    @FXML
    private TextField TxtValorTotal1;
    @FXML
    private Button BtnMenu;

    private VendaDao vendaDao= new VendaDao();
    private BrinquedoDao brinquedoDao= new BrinquedoDao();
    private ClienteDao clienteDao= new ClienteDao();

    @FXML
    private void lstVendasOnClick(){
        Venda venda = LstVendas.getSelectionModel().getSelectedItem();
        if( venda != null){
            TxtUnidades1.setText(venda.getQtd().toString());
            TxtValorTotal1.setText(venda.getValor_total().toString());
            CboClientes.getSelectionModel().select(venda.getCliente());
            CboBrinquedo.getSelectionModel().select(venda.getBrinquedo().get(0));
        }
    }

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
    private ListView<Venda> LstVendas;

    @FXML
    private Button BtnExcluir;


    @FXML Button BtnCaixa;

    @FXML
    private Button BtnAlterar;


    @FXML
    private void BtnAlterar_Action(ActionEvent evento) throws Exception {
        if(LstVendas.getSelectionModel().getSelectedItem() != null) {
            Venda venda = LstVendas.getSelectionModel().getSelectedItem();
            List<Brinquedo> listabrinquedo = new ArrayList<>();
            listabrinquedo.add(CboBrinquedo.getSelectionModel().getSelectedItem());
            venda.setBrinquedo(listabrinquedo);
            venda.setCliente(CboClientes.getSelectionModel().getSelectedItem());
            venda.setQtd(Integer.valueOf(TxtUnidades1.getText()));
            venda.setValor_total(Double.valueOf(TxtValorTotal1.getText()));
            venda.setData(LocalDate.now());
            vendaDao.aLterar(venda);
            AtualizarVenda();
        }

    }

    @FXML
    private void BtnCaixa_Action(ActionEvent evento){
            Double totalCompra = 0d;
            Double totalVenda = 0d;

            List<Compra> compras = new ArrayList<>();
            List<Venda> vendas = new ArrayList<>();

        try {
            compras = new CompraDAO().listar();
            vendas = new VendaDao().listar();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(Venda venda : vendas){
            totalVenda += venda.getValor_total();
        }

        for (Compra compra : compras){
            totalCompra += compra.getValor_total();
        }

        double saldo = totalVenda - totalCompra;


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Total Gasto de Compras = " + totalCompra);
        stringBuilder.append("\nTotal Lucro de Vendas = " + totalVenda);
        stringBuilder.append("\nSaldo = " + saldo);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fechamento de caixa");
        alert.setHeaderText("FECHAMENTO DO CAIXA");
        alert.setContentText(stringBuilder.toString());
        alert.showAndWait();
    }



    @FXML
    private void BtnExcluir_Action(ActionEvent evento) throws Exception {

        if(LstVendas.getSelectionModel().getSelectedItem()!=null){
            vendaDao.excluir(LstVendas.getSelectionModel().getSelectedItem());
            AtualizarVenda();


        }

    }
    @FXML
    private Button BtnGravar;
    @FXML
    private void BtnGravar_Action(ActionEvent evento){
        Venda venda = new Venda();
        List<Brinquedo> listabrinquedo = new ArrayList<>();
        listabrinquedo.add(CboBrinquedo.getSelectionModel().getSelectedItem());
        System.out.println(listabrinquedo);
        venda.setBrinquedo(listabrinquedo);
        venda.setCliente(CboClientes.getSelectionModel().getSelectedItem());
        venda.setQtd(Integer.valueOf(TxtUnidades1.getText()));
        venda.setValor_total(Double.valueOf(TxtValorTotal1.getText()));
        venda.setData(LocalDate.now());
        try {
            vendaDao.gravar(venda);
            CboBrinquedo.getSelectionModel().clearSelection();
            CboClientes.getSelectionModel().clearSelection();
            TxtUnidades1.setText("");
            TxtValorTotal1.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        AtualizarVenda();
    }


}
