package edu.br.femass.lojabrinquedo.Dao;

import edu.br.femass.lojabrinquedo.Model.Brinquedo;
import edu.br.femass.lojabrinquedo.Model.Cliente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao extends PostgreDao implements Dao<Cliente> {

    @Override
    public List<Cliente> listar() throws Exception {
        String sql= "select * from cliente order by nome";
        PreparedStatement ps=getPreparedStatement(sql,false);
        ResultSet rs=ps.executeQuery();
        List<Cliente> clientes= new ArrayList<Cliente>();
        while ((rs.next())){
            Cliente cliente= new Cliente();
            cliente.setId_cliente(rs.getLong("id_cliente"));
            cliente.setNome(rs.getString("nome"));
            cliente.setCpf(rs.getString("cpf"));
            cliente.setEmail(rs.getString("email"));
            cliente.setTelefone(rs.getString("telefone"));
            clientes.add(cliente);

        }
        return clientes;
    }

    @Override
    public void gravar(Cliente value) throws Exception {
        String sql=" INSERT INTO cliente (nome,cpf,email,telefone) VALUES(?,?,?,?)";
        PreparedStatement ps = getPreparedStatement(sql,true);
        ps.setString(1,value.getNome());
        ps.setString(2,value.getCpf());
        ps.setString(3, value.getEmail());
        ps.setString(4,value.getTelefone());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        value.setId_cliente(rs.getLong("id_cliente"));


    }

    @Override
    public void aLterar(Cliente value) throws Exception {
        String sql=" update cliente set nome = ?,cpf = ?,email = ?,telefone = ?  where id_cliente = ?";
        PreparedStatement ps = getPreparedStatement(sql,false);
        ps.setString(1,value.getNome());
        ps.setString(2,value.getCpf());
        ps.setString(3, value.getEmail());
        ps.setString(4,value.getTelefone());
        ps.setLong(5,value.getId_cliente());
        ps.executeUpdate();


    }

    @Override
    public void excluir(Cliente value) throws Exception {
        String sql =" delete from cliente where id_cliente=?";
        PreparedStatement ps= getPreparedStatement(sql,false);
        ps.setLong(1,value.getId_cliente());
        ps.executeUpdate();

    }
}
