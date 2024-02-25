package com.example.ecommerce;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Product {

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleIntegerProperty price;
    private SimpleIntegerProperty quantity;

    public Product(int id, String name, int price) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleIntegerProperty(price);

    }

    public int getId() {
        return id.get();
    }



    public String getName() {
        return name.get();
    }



    public int getPrice() {
        return price.get();
    }



    public int getQuantity() {
        return quantity.get();
    }
    public static ObservableList<Product> fetchAllProducts()
    {
        ObservableList<Product> data= FXCollections.observableArrayList();
        try{
            DBConnection connection=new DBConnection();
            ResultSet rs=connection.getQueryTable("SELECT id,name,price FROM product");
            while(rs.next())
            {
                data.add(new Product(rs.getInt("id"),rs.getString("name"),rs.getInt("price"))) ;
            }
            return data;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        return null;
    }


}
