package edu.br.femass.lojabrinquedo.Model;

import lombok.Data;

@Data
public class Cliente {
    private long id_cliente;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    @Override
    public String toString() {
        return "ID=" +id_cliente+","+ nome+","+"CPF:"+cpf;

    }
}
