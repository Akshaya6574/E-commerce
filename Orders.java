package com.example.ecommerce;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

import java.util.Date;
public class Orders {
    private SimpleIntegerProperty id;
    private SimpleStringProperty product_name;
    private  SimpleIntegerProperty quantity;
    private SimpleObjectProperty<Date> order_date;
    private SimpleStringProperty order_status;
    public Orders(int id, String productName, int quantity, Date order_date, String order_status) {
        this.id = new SimpleIntegerProperty(id);
        this.product_name = new SimpleStringProperty(productName);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.order_date = new SimpleObjectProperty<>(order_date);
        this.order_status = new SimpleStringProperty(order_status);
    }




    public  static boolean placeOrder(Product product,Customer customer)
   {
       DBConnection dbConnection=new DBConnection();
       String get_group_order_id_query="SELECT max(group_order_id)+1 id FROM orders;";
       try{
           ResultSet rs=dbConnection.getQueryTable(get_group_order_id_query);
           if(rs.next())
           {
               String query="Insert into orders(product_id,customer_id,group_order_id) values ("+product.getId()+"," +
                       ""+customer.getId()+","+rs.getInt("id")+");";
               return dbConnection.updateDataBase(query)!=0;

           }
       }
       catch(Exception e)
       {
           e.printStackTrace();
       }

       return false;
   }
   public static int placeMultipleOrders(ObservableList<Product> product,Customer customer)
   {
       DBConnection dbConnection=new DBConnection();
       String get_group_order_id_query="SELECT max(group_order_id)+1 id FROM orders;";
       int cnt=0;
       try{
           ResultSet rs=dbConnection.getQueryTable(get_group_order_id_query);
           if(rs.next())
           {
               for(Product i:product) {
                   String query = "Insert into orders(product_id,customer_id,group_order_id) values (" + i.getId() + "," +
                           "" + customer.getId() + "," + rs.getInt("id") + ");";
                   cnt+=dbConnection.updateDataBase(query);
               }
               return cnt;

           }
       }
       catch(Exception e)
       {
           e.printStackTrace();
       }

       return 0;
   }

   public static ObservableList<Orders> showOrders(int customer_id)
   {
       ObservableList<Orders> data= FXCollections.observableArrayList();
      String query="SELECT orders.id,product.name,orders.quantity,orders.order_date,orders.order_status FROM  orders JOIN product ON orders.product_id=product.id WHERE customer_id="+customer_id+";";
      DBConnection dbConnection=new DBConnection();
      try{
          ResultSet rs=dbConnection.getQueryTable(query);
          while(rs.next())
          {
             data.add(new Orders(rs.getInt("id"),rs.getString("name"),rs.getInt("quantity"), rs.getDate("order_date"),rs.getString("order_status")));
          }
          return data;
      }
      catch(Exception e)
      {
          e.printStackTrace();
      }
      return  null;
   }

    public int getId() {
        return id.get();
    }



    public void setId(int id) {
        this.id.set(id);
    }

    public String getProduct_name() {
        return product_name.get();
    }



    public void setProduct_name(String product_name) {
        this.product_name.set(product_name);
    }

    public int getQuantity() {
        return quantity.get();
    }



    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public Date getOrder_date() {
        return order_date.get();
    }



    public void setOrder_date(Date order_date) {
        this.order_date.set(order_date);
    }

    public String getOrder_status() {
        return order_status.get();
    }



    public void setOrder_status(String order_status) {
        this.order_status.set(order_status);
    }
}
