package edu.br.femass.lojabrinquedo.Model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class Venda {
    private long id_venda;
    private LocalDate data;
    private Integer qtd;
    private Double valor_total;
    private Cliente cliente;

    @Override
    public String toString() {
        return "id_venda=" + id_venda + ", data "+  qtd +" unidades"+ brinquedo;

    }

    private List<Brinquedo> brinquedo;


}
