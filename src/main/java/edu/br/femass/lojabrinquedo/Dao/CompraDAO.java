package edu.br.femass.lojabrinquedo.Dao;

import edu.br.femass.lojabrinquedo.Model.Brinquedo;
import edu.br.femass.lojabrinquedo.Model.Compra;
import edu.br.femass.lojabrinquedo.Model.Fornecedor;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CompraDAO extends PostgreDao implements Dao<Compra>{

            @Override
            public List<Compra> listar() throws Exception {
            String sql= "select * from compra order by data";
            PreparedStatement ps=getPreparedStatement(sql,false);
            ResultSet rs=ps.executeQuery();
            List<Compra> compras= new ArrayList<Compra>();
            while ((rs.next())){
                Compra compra= new Compra();
                compra.setValor_unitario(rs.getDouble("valor_unitario"));
                compra.setValor_total(rs.getDouble("valor_total"));
                compra.setId_compra(rs.getLong("id_compra"));
                compra.setQtd(rs.getInt("quantidade"));
                compra.setData(rs.getDate("data").toLocalDate());


                String sql2 = "select * from fornecedor where id_fornecedor = ?";
                ps = getPreparedStatement(sql2,false);
                ps.setLong(1, rs.getLong("id_fornecedor"));
                ResultSet rs2 = ps.executeQuery();
                rs2.next();
                Fornecedor fornecedor= new Fornecedor();
                fornecedor.setId_fornecedor(rs2.getLong("id_fornecedor"));
                fornecedor.setNome(rs2.getString("nome"));
                fornecedor.setEmail(rs2.getString("email"));
                fornecedor.setCnpj(rs2.getString("cnpj"));
                compra.setFornecedor(fornecedor);

                List<Brinquedo> list = new ArrayList<>();
                String sql3 = "select * from brinquedo WHERE id_brinquedo IN ( SELECT id_produto FROM detalhes_compra WHERE id_compra = ?);";
                ps = getPreparedStatement(sql3,false);
                ps.setLong(1, rs.getLong("id_compra"));
                ResultSet rs3 = ps.executeQuery();
                while ((rs3.next())){
                    Brinquedo brinquedo= new Brinquedo();
                    brinquedo.setNome(rs3.getString("nome"));
                    brinquedo.setEstoque(rs3.getInt("estoque"));
                    brinquedo.setId_brinquedo(rs3.getLong("id_brinquedo"));
                    brinquedo.setPreco_venda(rs3.getDouble("preco_venda"));
                    list.add(brinquedo);

                }
                compra.setBrinquedo(list);
                compras.add(compra);
            }
            return compras;
    }

    @Override
    public void gravar(Compra value) throws Exception {
        String sql=" INSERT INTO compra (valor_unitario,valor_total,quantidade,data,id_fornecedor) VALUES(?,?,?,?,?)";
        PreparedStatement ps = getPreparedStatement(sql,true);
        ps.setDouble(1,value.getValor_unitario());
        ps.setDouble(2,value.getValor_total());
        ps.setInt(3,value.getQtd());
        ps.setDate(4, Date.valueOf(value.getData()));
        ps.setLong(5, value.getFornecedor().getId_fornecedor());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        value.setId_compra(rs.getLong(1));

        for(Brinquedo brinquedo: value.getBrinquedo()){

            sql="insert into detalhes_compra (id_produto,id_compra) VALUES(?,?)";
            ps= getPreparedStatement(sql,true);
            ps.setLong(1,brinquedo.getId_brinquedo());
            ps.setLong(2,value.getId_compra());
            ps.executeUpdate();

            brinquedo.setEstoque(brinquedo.getEstoque() + value.getQtd());
            new BrinquedoDao().aLterar(brinquedo);
        }
    }

    @Override
    public void aLterar(Compra value) throws Exception {
        String sql="update compra set valor_unitario = ?,valor_total = ?, quantidade = ?,data = ?, id_compra = ? where id_fornecedor = ?";
        PreparedStatement ps = getPreparedStatement(sql,false);
        ps.setDouble(1,value.getValor_unitario());
        ps.setDouble(2,value.getValor_total());
        ps.setInt(3,value.getQtd());
        ps.setDate(4, Date.valueOf(value.getData()));
        ps.setLong(5,(value.getFornecedor().getId_fornecedor()));
        ps.setLong(6,value.getId_compra());
        ps.executeUpdate();
    }

    @Override
    public void excluir(Compra value) throws Exception {
        for(Brinquedo brinquedo: value.getBrinquedo()) {

           String sql = "delete from detalhes_compra where id_compra = ? and id_produto = ?";
            PreparedStatement ps= getPreparedStatement(sql,false);
            ps.setLong(1,value.getId_compra());
            ps.setLong(2,brinquedo.getId_brinquedo());
            ps.executeUpdate();

        }
        String sql =" delete from compra where id_compra = ?";
        PreparedStatement ps= getPreparedStatement(sql,false);
        ps.setLong(1,value.getId_compra());
        ps.executeUpdate();

    }
}
