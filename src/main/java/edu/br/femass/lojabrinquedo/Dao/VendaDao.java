package edu.br.femass.lojabrinquedo.Dao;

import edu.br.femass.lojabrinquedo.Model.*;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VendaDao extends PostgreDao implements Dao<Venda>{
    @Override
    public List<Venda> listar() throws Exception {
        String sql= "select * from venda order by data";
        PreparedStatement ps=getPreparedStatement(sql,false);
        ResultSet rs=ps.executeQuery();
        List<Venda> vendas= new ArrayList<Venda>();

        while ((rs.next())){
            Venda venda= new Venda();
            venda.setId_venda(rs.getLong("id_venda"));
            venda.setData(rs.getDate("data").toLocalDate());
            venda.setQtd(rs.getInt("quantidade"));
            venda.setValor_total(rs.getDouble("valor_total_venda"));

            String sql2="select * from cliente where id_cliente = ?";
            PreparedStatement ps2 = getPreparedStatement(sql2,false);
            ps2.setLong(1, rs.getLong("id_cliente"));
            ResultSet rs2 = ps2.executeQuery();

            rs2.next();

            Cliente cliente = new Cliente();
            cliente.setId_cliente(rs2.getLong("id_cliente"));
            cliente.setNome(rs2.getString("nome"));
            cliente.setCpf(rs2.getString("cpf"));
            cliente.setEmail(rs2.getString("email"));
            cliente.setTelefone(rs2.getString("telefone"));
            venda.setCliente(cliente);

            List<Brinquedo> list = new ArrayList<>();
            String sql3 = "select * from brinquedo WHERE id_brinquedo IN (SELECT id_produto FROM detalhes_venda WHERE id_venda = ?);";
            PreparedStatement ps3 = getPreparedStatement(sql3,false);
            ps3.setLong(1, rs.getLong("id_venda"));
            ResultSet rs3 = ps3.executeQuery();
            while ((rs3.next())){
                Brinquedo brinquedo= new Brinquedo();
                brinquedo.setNome(rs3.getString("nome"));
                brinquedo.setEstoque(rs3.getInt("estoque"));
                brinquedo.setId_brinquedo(rs3.getLong("id_brinquedo"));
                brinquedo.setPreco_venda(rs3.getDouble("preco_venda"));
                list.add(brinquedo);
            }
            venda.setBrinquedo(list);
            vendas.add(venda);
        }
        return vendas;
    }

    @Override
    public void gravar(Venda value) throws Exception {
        if(value.getBrinquedo().get(0).getEstoque() >= value.getQtd()){
            String sql=" INSERT INTO venda (valor_total_venda,quantidade,data,id_cliente) VALUES(?,?,?,?)";
            PreparedStatement ps = getPreparedStatement(sql,true);
            ps.setDouble(1,value.getValor_total());
            ps.setInt(2,value.getQtd());
            ps.setDate(3, Date.valueOf(value.getData()));
            ps.setLong(4, value.getCliente().getId_cliente());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            value.setId_venda(rs.getLong(1));

            for(Brinquedo brinquedo: value.getBrinquedo()){

                sql="insert into detalhes_venda (id_produto,id_venda) VALUES(?,?)";
                ps= getPreparedStatement(sql,true);
                ps.setLong(1,brinquedo.getId_brinquedo());
                ps.setLong(2,value.getId_venda());
                ps.executeUpdate();

                brinquedo.setEstoque(brinquedo.getEstoque() - value.getQtd());
                new BrinquedoDao().aLterar(brinquedo);

            }
        }else {
            throw new Exception("Item não disponível");
        }
    }

    @Override
    public void aLterar(Venda value) throws Exception {
        String sql= "update venda set valor_total_venda = ?, quantidade = ?, data = ?, id_cliente = ? where id_venda = ?";
        PreparedStatement ps = getPreparedStatement(sql,false);
        ps.setDouble(1,value.getValor_total());
        ps.setInt(2,value.getQtd());
        ps.setDate(3, Date.valueOf(value.getData()));
        ps.setLong(4, value.getCliente().getId_cliente());
        ps.setLong(5, value.getId_venda());
        ps.executeUpdate();

    }

    @Override
    public void excluir(Venda value) throws Exception {
        for(Brinquedo brinquedo: value.getBrinquedo()) {

            String sql = "delete from venda where id_venda =? and id_produto = ?";
            PreparedStatement ps= getPreparedStatement(sql,false);
            ps.setLong(1,value.getId_venda());
            ps.setLong(2,brinquedo.getId_brinquedo());
            ps.executeUpdate();

        }
        String sql ="delete from venda where id_venda = ?";
        PreparedStatement ps= getPreparedStatement(sql,false);
        ps.setLong(1,value.getId_venda());
        ps.executeUpdate();

    }

    }

