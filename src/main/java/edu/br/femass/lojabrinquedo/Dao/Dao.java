package edu.br.femass.lojabrinquedo.Dao;

import java.util.List;

public interface Dao<T> {
    public List<T> listar()throws Exception;
    public void gravar(T value) throws Exception;
    public void aLterar(T value) throws Exception;
    public void excluir(T value) throws Exception;
}
