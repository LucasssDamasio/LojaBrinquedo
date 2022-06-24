package edu.br.femass.lojabrinquedo.Model;

import lombok.Data;

@Data
public class Fornecedor {
    private long id_fornecedor;
    private String cnpj;
    private String nome;
    private String email;

    @Override
    public String toString() {
        return  "ID="+ id_fornecedor+"," + nome+ ","+ cnpj ;

    }

}


