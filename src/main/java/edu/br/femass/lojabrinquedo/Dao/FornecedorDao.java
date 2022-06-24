package edu.br.femass.lojabrinquedo.Dao;

import edu.br.femass.lojabrinquedo.Model.Cliente;
import edu.br.femass.lojabrinquedo.Model.Fornecedor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FornecedorDao extends PostgreDao implements  Dao<Fornecedor>{

    @Override
    public List<Fornecedor> listar() throws Exception {
        String sql= "select * from fornecedor order by nome";
        PreparedStatement ps=getPreparedStatement(sql,false);
        ResultSet rs=ps.executeQuery();
        List<Fornecedor> fornecedores= new ArrayList<Fornecedor>();
        while ((rs.next())){
            Fornecedor fornecedor= new Fornecedor();
            fornecedor.setId_fornecedor(rs.getLong("id_fornecedor"));
            fornecedor.setNome(rs.getString("nome"));
            fornecedor.setEmail(rs.getString("email"));
            fornecedor.setCnpj(rs.getString("cnpj"));
            fornecedores.add(fornecedor);

        }
        return fornecedores;
    }

    @Override
    public void gravar(Fornecedor value) throws Exception {
        String sql=" INSERT INTO fornecedor (nome,cnpj ,email) VALUES(?,?,?)";
        PreparedStatement ps = getPreparedStatement(sql,true);
        ps.setString(1,value.getNome());
        ps.setString(2,value.getCnpj());
        ps.setString(3, value.getEmail());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        value.setId_fornecedor(rs.getLong(1));



    }

    @Override
    public void aLterar(Fornecedor value) throws Exception {
        String sql="update fornecedor set nome=?,cnpj=?,email=? where id_fornecedor=?";
        PreparedStatement ps = getPreparedStatement(sql,true);
        ps.setString(1,value.getNome());
        ps.setString(2,value.getCnpj());
        ps.setString(3, value.getEmail());
        ps.setLong(4,value.getId_fornecedor());
        ps.executeUpdate();

    }

    @Override
    public void excluir(Fornecedor value) throws Exception {
        String sql =" delete from fornecedor where id_fornecedor=?";
        PreparedStatement ps= getPreparedStatement(sql,false);
        ps.setLong(1,value.getId_fornecedor());
        ps.executeUpdate();

    }
}
