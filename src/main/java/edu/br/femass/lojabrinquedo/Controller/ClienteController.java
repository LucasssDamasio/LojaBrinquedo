package edu.br.femass.lojabrinquedo.Controller;

import edu.br.femass.lojabrinquedo.Dao.ClienteDao;
import edu.br.femass.lojabrinquedo.Model.Brinquedo;
import edu.br.femass.lojabrinquedo.Model.Cliente;
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

public class ClienteController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AtualizarCliente();

    }
    @FXML
    private TextField TxtNomeCliente;

    @FXML
    private TextField TxtCPF;

    @FXML
    private TextField TxtEmail;

    @FXML
    private TextField TxtTelefone;

    private ClienteDao clienteDao= new ClienteDao();
    @FXML
    private Button BtnMenu;

    public void AtualizarCliente() {
        List<Cliente> clientes=new ArrayList<>();
        try {
            clientes=clienteDao.listar();
        }catch (Exception e){
            clientes= new ArrayList<Cliente>();
        }
        ObservableList<Cliente> clienteObservableList= FXCollections.observableArrayList(clientes);
        LstCliente.setItems(clienteObservableList);
    }
    @FXML
    private void  BtnMenu_Action(ActionEvent evento){
        FXMLLoader fx= new FXMLLoader(ClienteController.class.getResource("/edu/br/femass/lojabrinquedo/main.fxml"));
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
    private ListView<Cliente> LstCliente;

    @FXML
    private Button BtnExcluirCli;

    @FXML
    private void BtnExcluirCli_Action(ActionEvent evento) throws Exception {

        if(LstCliente.getSelectionModel().getSelectedItem()!=null){
            clienteDao.excluir(LstCliente.getSelectionModel().getSelectedItem());
            AtualizarCliente();

        }

    }
    @FXML
    private Button BtnGravarCli;

    @FXML
    private void BtnGravarCli_Action(ActionEvent evento){
        Cliente cliente= new Cliente();
        cliente.setCpf(TxtCPF.getText());
        cliente.setNome(TxtNomeCliente.getText());
        cliente.setEmail(TxtEmail.getText());
        cliente.setTelefone(TxtTelefone.getText());
        try {

            clienteDao.gravar(cliente);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AtualizarCliente();
        TxtCPF.setText("");
        TxtEmail.setText("");
        TxtNomeCliente.setText("");
        TxtTelefone.setText("");



    }
    @FXML
    private Button BtnALterarCli;

    @FXML
    private void BtnALterarCli_Action(ActionEvent evento) throws Exception {
        if(LstCliente.getSelectionModel().getSelectedItem()!=null) {
            Cliente cliente = LstCliente.getSelectionModel().getSelectedItem();
            cliente.setNome(TxtNomeCliente.getText());
            cliente.setEmail(TxtEmail.getText());
            cliente.setTelefone(TxtTelefone.getText());
            cliente.setCpf(TxtCPF.getText());
            clienteDao.aLterar(cliente);
            AtualizarCliente();
        }

    }
    @FXML
    private void LstClienteOnClick(){
        Cliente cliente=LstCliente.getSelectionModel().getSelectedItem();
        if(cliente != null){
            TxtCPF.setText(cliente.getCpf());
            TxtNomeCliente.setText(cliente.getNome());
            TxtEmail.setText(cliente.getEmail());
            TxtTelefone.setText(cliente.getTelefone());

        }
    }

}
