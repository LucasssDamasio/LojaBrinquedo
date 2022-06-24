package edu.br.femass.lojabrinquedo.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private Label welcomeText;

    @FXML
    private ImageView IVlego;



   @FXML
    private Button BtnCompra;

    @FXML
    private void  BtnCompra_Action(ActionEvent evento){
        FXMLLoader fx= new FXMLLoader(MainController.class.getResource("/edu/br/femass/lojabrinquedo/compra.fxml"));
        try {
            Scene s = new Scene(fx.load());
            Stage st = new Stage();
            st = (Stage)((Node)evento.getSource()).getScene().getWindow();
            st.setTitle("Cadastro de compra de brinquedo");
            st.setScene(s);
            st.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button BtnEstoque;

    @FXML
    private void  BtnEstoque_Action(ActionEvent evento){
        FXMLLoader fx= new FXMLLoader(MainController.class.getResource("/edu/br/femass/lojabrinquedo/estoque.fxml"));
        try {
            Scene s = new Scene(fx.load());
            Stage st = new Stage();
            st = (Stage)((Node)evento.getSource()).getScene().getWindow();
            st.setTitle("Controle de estoque");
            st.setScene(s);
            st.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private Button BtnVenda;

    @FXML
    private void  BtnVenda_Action(ActionEvent evento){
        FXMLLoader fx= new FXMLLoader(MainController.class.getResource("/edu/br/femass/lojabrinquedo/venda.fxml"));
        try {
            Scene s = new Scene(fx.load());
            Stage st = new Stage();
            st = (Stage)((Node)evento.getSource()).getScene().getWindow();
            st.setTitle("Cadastro de Vendas");
            st.setScene(s);
            st.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button BtnCliente;

    @FXML
    private void  BtnCliente_Action(ActionEvent evento){
        FXMLLoader fx= new FXMLLoader(MainController.class.getResource("/edu/br/femass/lojabrinquedo/cliente.fxml"));
        try {
            Scene s = new Scene(fx.load());
            Stage st = new Stage();
            st = (Stage)((Node)evento.getSource()).getScene().getWindow();
            st.setTitle("Cadastro de Clientes");
            st.setScene(s);
            st.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private Button BtnFornecedor;
    @FXML
    private void  BtnFornecedor_Action(ActionEvent evento){
        FXMLLoader fx= new FXMLLoader(MainController.class.getResource("/edu/br/femass/lojabrinquedo/fornecedor.fxml"));
        try {
            Scene s = new Scene(fx.load());
            Stage st = new Stage();
            st = (Stage)((Node)evento.getSource()).getScene().getWindow();
            st.setTitle("Cadastro de Fornecedor");
            st.setScene(s);
            st.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private Button BtnBrinquedo;

    @FXML
    private void  BtnBrinquedo_Action(ActionEvent evento){
        FXMLLoader fx= new FXMLLoader(MainController.class.getResource("/edu/br/femass/lojabrinquedo/brinquedo.fxml"));
        try {
            Scene s = new Scene(fx.load());
            Stage st = new Stage();
            st = (Stage)((Node)evento.getSource()).getScene().getWindow();
            st.setTitle("Cadastro de compra de brinquedo");
            st.setScene(s);
            st.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}




