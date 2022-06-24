package edu.br.femass.lojabrinquedo.Dao;

import edu.br.femass.lojabrinquedo.Model.Brinquedo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BrinquedoDao extends PostgreDao implements Dao<Brinquedo> {
    @Override
    public List<Brinquedo> listar() throws Exception {
        String sql= "select * from brinquedo order by nome";
        PreparedStatement ps=getPreparedStatement(sql,false);
        ResultSet rs=ps.executeQuery();
        List<Brinquedo> brinquedos= new ArrayList<Brinquedo>();
        while ((rs.next())){
            Brinquedo brinquedo= new Brinquedo();
            brinquedo.setNome(rs.getString("nome"));
            brinquedo.setEstoque(rs.getInt("estoque"));
            brinquedo.setId_brinquedo(rs.getLong("id_brinquedo"));
            brinquedo.setPreco_venda(rs.getDouble("preco_venda"));
            brinquedos.add(brinquedo);

        }
        return brinquedos;
    }

    @Override
    public void gravar(Brinquedo value) throws Exception {
        String sql=" INSERT INTO brinquedo (nome,estoque, preco_venda) VALUES(?,?,?)";
        PreparedStatement ps = getPreparedStatement(sql,true);
        ps.setString(1,value.getNome());
        ps.setInt(2,value.getEstoque());
        ps.setDouble(3,value.getPreco_venda());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        value.setId_brinquedo(rs.getLong(1));

    }

    @Override
    public void aLterar(Brinquedo value) throws Exception {
        String sql="update brinquedo set nome = ?, estoque = ?, preco_venda = ? where id_brinquedo = ?";
        PreparedStatement ps = getPreparedStatement(sql,false);
        ps.setString(1,value.getNome());
        ps.setInt(2,value.getEstoque());
        ps.setDouble(3,value.getPreco_venda());
        ps.setLong(4,value.getId_brinquedo());
        ps.executeUpdate();


    }

    @Override
    public void excluir(Brinquedo value) throws Exception {
        String sql =" delete from brinquedo where id_brinquedo=?";
        PreparedStatement ps= getPreparedStatement(sql,false);
        ps.setLong(1,value.getId_brinquedo());
        ps.executeUpdate();

    }
}
