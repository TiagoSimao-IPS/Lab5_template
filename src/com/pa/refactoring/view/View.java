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
        initView();
    }

    public void initView() {
        // Add product
        createAddProductGrid();

        // Shopping cart
        createShoppingCartGrid();

        createStage();
    }

    private void createAddProductGrid() {
        GridPane gridPaneAddProduct = createGridPaneAddProduct();

        TextField textFieldProductName = addTextField(gridPaneAddProduct);
        TextField textFieldPrice = addTextFieldPrice(gridPaneAddProduct, 2);

        createHBoxAddProducts(gridPaneAddProduct, textFieldProductName, textFieldPrice);
    }

    private void createHBoxAddProducts(GridPane gridPaneAddProduct, TextField textFieldProductName, TextField textFieldPrice) {
        HBox hBoxAddProductButtons = new HBox(6);

        Label labelEnd = getLabelEnd(hBoxAddProductButtons);
        Label labelCost = getLabelCost(gridPaneAddProduct, hBoxAddProductButtons);

        Button buttonAddProduct = getAddButton(textFieldProductName, textFieldPrice, labelCost);
        Button buttonTerminate = getTerminateButton(buttonAddProduct, labelEnd, labelCost);

        hBoxAddProductButtons.getChildren().addAll(buttonAddProduct, buttonTerminate);
        this.setTop(gridPaneAddProduct);
    }

    private TextField addTextFieldPrice(GridPane gridPaneAddProduct, int i) {
        TextField textFieldPrice = createTextField();
        gridPaneAddProduct.add(textFieldPrice, 1, i);

        return textFieldPrice;
    }

    private TextField addTextField(GridPane gridPaneAddProduct) {
        TextField textFieldProductName = addTextFieldPrice(gridPaneAddProduct, 1);
        gridPaneAddProduct.add(new Label("Price"), 0, 2);

        return textFieldProductName;
    }

    private TextField createTextField() {
        return new TextField();
    }

    private Label getLabelCost(GridPane gridPaneAddProduct, HBox hBoxAddProductButtons) {
        Label labelCost = createLabel("Total cost: 0.0 €");
        gridPaneAddProduct.add(labelCost, 0, 3);
        gridPaneAddProduct.add(hBoxAddProductButtons, 1, 3);

        return labelCost;
    }

    private Label getLabelEnd(HBox hBoxAddProductButtons) {
        Label labelEnd = new Label();
        this.setBottom(labelEnd);

        hBoxAddProductButtons.setAlignment(Pos.CENTER_RIGHT);
        hBoxAddProductButtons.setStyle("-fx-padding: 2px 0 0 0");

        return labelEnd;
    }

    private GridPane createGridPaneAddProduct() {
        GridPane gridPaneAddProduct = new GridPane();
        Label labelAddProduct = createLabel("Add products to cart");
        labelAddProduct.setStyle("-fx-font-weight: bold");
        gridPaneAddProduct.add(labelAddProduct, 0, 0);
        gridPaneAddProduct.add(new Label("Name"), 0, 1);

        return gridPaneAddProduct;
    }

    private Label createLabel(String s) {
        return new Label(s);
    }

    private void createShoppingCartGrid() {
        GridPane gridPaneCartContents = new GridPane();
        Label labelCartContents = createLabel("Cart contents");
        labelCartContents.setStyle("-fx-font-weight: bold");
        createListView(gridPaneCartContents, labelCartContents);
        this.setStyle("-fx-padding: 5px");
    }

    private Button getTerminateButton(Button buttonAddProduct, Label labelEnd, Label labelCost) {
        Button buttonTerminate = new Button("End");
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

        return buttonTerminate;
    }

    private Button getAddButton(TextField textFieldProductName, TextField textFieldPrice, Label labelCost) {
        Button buttonAddProduct = new Button("Add");
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

        return buttonAddProduct;
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

