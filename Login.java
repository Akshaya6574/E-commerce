package com.example.ecommerce;

import java.sql.ResultSet;

public class Login {
    public Customer customerLogin(String userName,String password)
    {
        String query="SELECT * FROM customer WHERE name='"+userName+"' AND password='"+password+"'";
        DBConnection connection=new DBConnection();
        try{

            ResultSet  rs=connection.getQueryTable(query);
            if(rs.next())
            {
                return new Customer(rs.getInt("id"),rs.getString("name"),
                        rs.getString("email"),rs.getString("password"),rs.getString("mobile"),
                        rs.getString("address"));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
//    public int customerId(Customer customer)
//    {
//        String query="SELECT id FROM cutomer WHERE id=customer.get"
//        DBConnection dbConnection=new DBConnection();
//        try{
//            ResultSet rs=dbConnection.getQueryTable(query);
//        }
//        return 0;
//    }



}
