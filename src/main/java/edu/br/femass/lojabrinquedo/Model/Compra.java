package edu.br.femass.lojabrinquedo.Model;

import lombok.Data;


import java.time.LocalDate;
import java.util.List;

@Data

public class Compra {

    private long id_compra;
    private LocalDate data;
    private Integer qtd;
    private Double valor_total;
    private Double valor_unitario;
    private Fornecedor fornecedor;
    private List<Brinquedo> brinquedo;

    @Override
    public String toString() {
        return "id=" + id_compra + ", data "+ data +"  "+ qtd +" unidades"+ brinquedo;

    }
}
