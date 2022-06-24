package edu.br.femass.lojabrinquedo.Dao;

import java.sql.*;

public abstract  class PostgreDao {

    protected static String ENDERECO="localhost";
    protected static String BD="loja";
    protected static String PORTA="5432";
    protected static String USUARIO="postgres";
    protected static String SENHA="postgre123";

    private static Connection con;

    protected Connection getConexao() throws SQLException {
        if(con != null) return con;
        String url="jdbc:postgresql://"+ ENDERECO+":"+ PORTA+"/"+BD;
        return DriverManager.getConnection(url,USUARIO,SENHA);
    }

    protected PreparedStatement getPreparedStatement(String sql,Boolean insercao) throws Exception{
        PreparedStatement ps=null;
        if(insercao){
            return  getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        }else{
            return getConexao().prepareStatement(sql);

        }

    }



}
