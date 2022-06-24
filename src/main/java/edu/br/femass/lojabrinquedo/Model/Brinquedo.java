package edu.br.femass.lojabrinquedo.Model;

import lombok.Data;

@Data
public class Brinquedo {

    private String nome;
   private int estoque;
    private  double preco_venda;
    private  Long id_brinquedo;

    @Override
    public String toString() {
        return "id"+ id_brinquedo+ ","+ nome + ", qtd=" + estoque+"  R$"+ preco_venda;

    }
}
