package com.pa.refactoring.view;

import com.pa.refactoring.model.Product;
import com.pa.refactoring.model.ShoppingCart;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class View extends BorderPane {
    private ShoppingCart shoppingCart;
    private ListView<Product> productListView;

    public View(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
        init();
    }

    public void init() {
        GridPane gridPaneAddProduct = new GridPane();
        Label labelAddProduct = new Label("Add products to cart");
        labelAddProduct.setStyle("-fx-font-weight: bold");
        gridPaneAddProduct.add(labelAddProduct, 0, 0);
        gridPaneAddProduct.add(new Label("Name"), 0, 1);
        TextField textFieldProductName = new TextField();
        gridPaneAddProduct.add(textFieldProductName, 1, 1);
        gridPaneAddProduct.add(new Label("Price"), 0, 2);
        TextField textFieldPrice = new TextField();
        gridPaneAddProduct.add(textFieldPrice, 1, 2);
        HBox hBoxAddProductButtons = new HBox(6);
        Button buttonAddProduct = new Button("Add");
        Button buttonTerminate = new Button("End");
        Label labelEnd = new Label();
        this.setBottom(labelEnd);

        hBoxAddProductButtons.setAlignment(Pos.CENTER_RIGHT);
        hBoxAddProductButtons.setStyle("-fx-padding: 2px 0 0 0");
        Label labelCost = new Label("Total cost: 0.0 €");
        gridPaneAddProduct.add(labelCost, 0, 3);
        gridPaneAddProduct.add(hBoxAddProductButtons, 1, 3);
        hBoxAddProductButtons.getChildren().addAll(buttonAddProduct, buttonTerminate);
        buttonAddProduct.setOnAction((ActionEvent e) -> {
            if (textFieldProductName.getText().isEmpty() || textFieldPrice.getText().isEmpty()) {
                showAlert("empty fields");
            } else {
                try {
                    String name = textFieldProductName.getText();
                    double price = Double.parseDouble(textFieldPrice.getText());
                    shoppingCart.addProduct(new Product(name, price));
                    labelCost.setText(String.format("Total Cost %.1f €", shoppingCart.getTotal()));
                    productListView.getItems().clear();
                    for (Product product : shoppingCart.getProducts()) {
                        productListView.getItems().add(product);
                    }
                    textFieldPrice.clear();
                    textFieldProductName.clear();
                } catch (NumberFormatException nfe) {
                    showAlert("invalid format");
                }
            }
        });
        this.setTop(gridPaneAddProduct);
        buttonTerminate.setOnAction((ActionEvent e) -> {
            shoppingCart.terminate();

            String strEnd;
            if (shoppingCart.isTerminated()) {
                strEnd = String.format("%s Total Cost %.2f Number of Items %d", shoppingCart.getDateStr(),
                        shoppingCart.getTotal(), shoppingCart.getProducts().size());
            } else {
                strEnd = "";
            }
            labelCost.setText(String.format("Current Cost %.1f €", shoppingCart.getTotal()));
            buttonAddProduct.setDisable(true);

            labelEnd.setText(strEnd);
        });
        // Shopping cart
        GridPane gridPaneCartContents = new GridPane();
        Label labelCartContents = new Label("Cart contents");
        labelCartContents.setStyle("-fx-font-weight: bold");
        createListView(gridPaneCartContents, labelCartContents);
        this.setStyle("-fx-padding: 5px");
        createStage();
    }

    private void createListView(GridPane gridPaneCartContents, Label labelCartContents) {
        productListView = new ListView<>();
        gridPaneCartContents.add(labelCartContents, 0, 0);
        gridPaneCartContents.add(productListView, 0, 1);
        GridPane.setHgrow(productListView, Priority.ALWAYS);
        this.setCenter(gridPaneCartContents);
        productListView.getItems().clear();

        for (Product product : shoppingCart.getProducts()) {
            productListView.getItems().add(product);
        }
    }

    private void createStage() {
        Scene scene = new Scene(this, 400, 600);
        Stage stage = new Stage();
        stage.setTitle("Shopping Cart");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void showAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Shopping Cart Error");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }
}

