package com.example.ecommerce;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class OrderList {
    public static TableView<Orders> ordersTableView=new TableView<>();
    public static VBox createOrderTable(ObservableList<Orders> data)
    {
        TableColumn id = new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name=new TableColumn("PRODUCT_NAME");
        name.setCellValueFactory(new PropertyValueFactory<>("product_name"));


        TableColumn quantity=new TableColumn("QUANTITY");
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn order_date=new TableColumn("ORDER_DATE");
        order_date.setCellValueFactory(new PropertyValueFactory<>("order_date"));

        TableColumn order_status=new TableColumn("ORDER_STATUS");
        order_status.setCellValueFactory(new PropertyValueFactory<>("order_status"));

        ordersTableView=new TableView<>();

        ordersTableView.setItems(data);
        ordersTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ordersTableView.getColumns().addAll(id,name,quantity,order_date,order_status);
        ordersTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox vbox=new VBox();
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(ordersTableView);
        return vbox;

    }
    public  static VBox fetchOrders(int customer_id)
    {
        ObservableList<Orders> data=Orders.showOrders(customer_id);

        return createOrderTable(data);
    }
}
