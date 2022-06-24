package edu.br.femass.lojabrinquedo.testes;

import edu.br.femass.lojabrinquedo.Dao.BrinquedoDao;
import edu.br.femass.lojabrinquedo.Model.Brinquedo;

import java.util.List;

public class BrinquedoTest {

    public static void main(String[] args) {
        testarAlterar();
        testarListar();

    }

    private static void testarGravar(){
        Brinquedo brinquedo= new Brinquedo();
        brinquedo.setNome("Carro BMW brinquedo");
        brinquedo.setEstoque(5);
        brinquedo.setPreco_venda(1299.99);
        try {
            new BrinquedoDao().gravar(brinquedo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(brinquedo.getId_brinquedo());
    }
    private static void testarListar(){
        try {
            List<Brinquedo> brinquedos= new  BrinquedoDao().listar();
            for(Brinquedo b: brinquedos){
                System.out.println(b.getId_brinquedo().toString()+"- "+ b.toString());

            }

        } catch( Exception e){
            e.printStackTrace();

        }


    }

    private static void testarAlterar(){
        Brinquedo brinquedo= new Brinquedo();
        brinquedo.setNome("Carro Camaro brinquedo");
        brinquedo.setEstoque(5);
        brinquedo.setPreco_venda(1299.99);
        brinquedo.setId_brinquedo(4L);

        try {
            new BrinquedoDao().aLterar(brinquedo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        testarListar();
    }

}
