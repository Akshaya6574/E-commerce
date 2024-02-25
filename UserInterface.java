package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import static javax.swing.JOptionPane.showMessageDialog;

public class UserInterface {
    GridPane loginPage;
    HBox headerBar;
    HBox footerBar;

    Customer loggedIn;

    ProductList productList=new ProductList();
    VBox productPage;

    VBox orderPage;
    VBox cartPage;

    VBox body;

    Label welcomeLabel;
    ObservableList<Product> itemsInCart= FXCollections.observableArrayList();
    ObservableList<Orders> itemsInOrders=FXCollections.observableArrayList();


    public UserInterface() {
       createLoginPage();
       createHeaderBar();
       createFooterBar();
    }

    BorderPane createPane(){
        BorderPane root=new BorderPane();
        root.setPrefSize(600,400);
        productPage=productList.getAllProducts();

        body=new VBox();
        body.setAlignment(Pos.CENTER);
        body.setPadding(new Insets(10));

        body.getChildren().add(productPage);
        root.setTop(headerBar);
        root.setBottom(footerBar);
        root.setCenter(body);
        return  root;
    }
    private void createLoginPage()
    {
        Text userName=new Text("User Name");
        Text password=new Text("Password");
        TextField userNameField=new TextField();
        userNameField.setPromptText("Enter the User Name");
        PasswordField passwordField= new PasswordField();
        passwordField.setPromptText("Enter the Password");
        Button loginButton=new Button("Login");
        Label label=new Label();



        loginPage=new GridPane();
        loginPage.setStyle("-fx-background-color: lightyellow ");
        loginPage.setAlignment(Pos.CENTER);
        loginPage.setPrefHeight(500);

        loginPage.add(userName,0,0);
        loginPage.add(userNameField,1,0);
        loginPage.add(password,0,1);
        loginPage.add(passwordField,1,1);
        loginPage.add(loginButton,1,2);
        loginPage.add(label,0,2);
        loginPage.setVgap(15);
        loginPage.setHgap(15);
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Login login=new Login();
                loggedIn=login.customerLogin(userNameField.getText(),passwordField.getText());
                if(loggedIn!=null)
                {
                    welcomeLabel.setText("Welcome "+ loggedIn.getName());
                    body.getChildren().clear();
                    body.getChildren().add(productPage);
                    footerBar.setVisible(true);

                }
                else {
                    label.setText("Log in Failed !! Please provide valid credentials");
                }
            }
        });




    }

    private void createHeaderBar()
    {
        TextField searchField=new TextField();
        searchField.setPromptText("Search here");
        searchField.setPrefWidth(280);
        Button searchButton=new Button("Search");
        Button signinButton=new Button("Sign in");
        Button cartButton=new Button("Cart");
        Button placeOrder=new Button("Place order");
        Button homeButton=new Button("Home");
        Button orderButton =new Button("Orders");

        welcomeLabel=new Label();
        headerBar=new HBox();
        headerBar.setAlignment(Pos.CENTER);
        headerBar.setPadding(new Insets(10));
        headerBar.setSpacing(10);
        headerBar.setStyle("-fx-background-color: yellow ");
        headerBar.getChildren().addAll(homeButton,searchField,searchButton,signinButton,welcomeLabel,cartButton,orderButton);
        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(productPage);
                footerBar.setVisible(true);

            }
        });
        signinButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                 body.getChildren().clear();
                 body.getChildren().add(loginPage);
                 headerBar.getChildren().remove(signinButton);
                 footerBar.setVisible(false);
            }
        });
                cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                cartPage =  new VBox();
                cartPage=productList.createTable(itemsInCart);

                cartPage.getChildren().add(placeOrder);
                cartPage.setSpacing(10);
                cartPage.setAlignment(Pos.CENTER);

                body.getChildren().clear();
                body.getChildren().add(cartPage);
                body.setSpacing(15);
                body.setAlignment(Pos.CENTER);
                footerBar.setVisible(false);


            }
        });
                placeOrder.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(itemsInCart.size()==0)
                        {
                            showMessageDialog("Cart is Empty");
                            return;
                        }
                        if(loggedIn==null)
                        {
                            showMessageDialog("Please log in to Place order");
                            return;
                        }
                        int count=Orders.placeMultipleOrders((ObservableList<Product>) itemsInCart,loggedIn);
                        if(count>0)
                        {
                            showMessageDialog("Order of "+count+" products placed successfully");
                        }
                    }
                });
                orderButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        body.getChildren().clear();
                        footerBar.setVisible(false);
                        if(loggedIn==null)
                        {
                            showMessageDialog("PLease Log In to view your orders");
                            return;
                        }
                        int customerId=loggedIn.getId();
                        itemsInOrders=Orders.showOrders(customerId);

                        if(itemsInOrders==null)
                        {
                            showMessageDialog("No orders");
                            return;
                        }

                        orderPage=OrderList.fetchOrders(customerId);

                        body.getChildren().clear();
                        body.getChildren().add(orderPage);



                    }
                });
    }
    private void createFooterBar()
    {
       Button buyNowButton=new Button("Buy now");
       Button addToCartButton=new Button("Add to cart");


       footerBar=new HBox();
       footerBar.setPadding(new Insets(10));
       footerBar.setAlignment(Pos.CENTER);
       footerBar.setSpacing(10);
       footerBar.getChildren().add(buyNowButton);
       footerBar.getChildren().add(addToCartButton);

       buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {
               Product product =productList.getSelectedProduct();
               if(product==null)
               {

                   showMessageDialog("Please select the product to place order");
                   return;
               }
               if(loggedIn==null)
               {

                   showMessageDialog("Please log in to place order");
                   return ;
               }
               if(Orders.placeOrder(product,loggedIn))
               {

                   showMessageDialog("Ordered Successfully");

               }
               else {

                   showMessageDialog("Order Failed");

               }

           }
       });


       addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {
               Product product=productList.getSelectedProduct();
               if(product==null)
               {
                   showMessageDialog("Please select the product to  enable add to cart ");
                   return;
               }
               itemsInCart.add(product);
               showMessageDialog("Added successfully");
           }
       });
    }
    public static void showMessageDialog(String msg)
    {
          Alert alert=new Alert(Alert.AlertType.INFORMATION);
          alert.setTitle("Message");
          alert.setHeaderText(null);
          alert.setContentText(msg);
          alert.showAndWait();
    }
}
