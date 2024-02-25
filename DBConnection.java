package com.example.ecommerce;
import java.sql.*;

public class DBConnection {
    private final String  url="jdbc:mysql://localhost:3306/ecommerce";
    private final String userName="root";
    private final String password="Akshaya6574";

    private Statement getStatement() {
        try{
            Connection connection=DriverManager.getConnection(url,userName,password);
            return connection.createStatement();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;

    }

    public ResultSet getQueryTable(String query) {
        try{
            Statement statement=getStatement();
            return statement.executeQuery(query);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;


    }
    public int updateDataBase(String query)
    {
        try{
        Statement statement=getStatement();
        return statement.executeUpdate(query);
           }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return 0;
    }


    public static void main(String[] args) {
        DBConnection conn=new DBConnection();
        ResultSet rs=conn.getQueryTable("Select * from customer");
        if(rs!=null)
        {
            System.out.println("Connection Successful");
        }
        else {
            System.out.println("Connection Failure");
        }

    }



}
