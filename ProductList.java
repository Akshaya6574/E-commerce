package com.example.ecommerce;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ProductList {
    public TableView<Product> productTableView;

    public  VBox createTable(ObservableList<Product> data)
    {
        TableColumn id = new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name=new TableColumn("NAME");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price=new TableColumn("PRICE");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn quantity=new TableColumn("QUANTITY");
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));



        productTableView=new TableView<>();
        productTableView.getColumns().addAll(id,name,price);
        productTableView.setItems(data);
        productTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox vbox=new VBox();
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(productTableView);
        return vbox;
    }

    public VBox  getAllProducts()
    {
        ObservableList<Product> data=Product.fetchAllProducts();
       return createTable(data);
    }
    public Product getSelectedProduct()
    {
        return productTableView.getSelectionModel().getSelectedItem();
    }
}
