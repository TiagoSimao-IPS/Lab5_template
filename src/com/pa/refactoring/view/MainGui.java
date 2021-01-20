package com.pa.refactoring.view;

import com.pa.refactoring.model.ShoppingCart;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Template para o laboratório de deteçãod e BAD Smells e aplicação de tecnicas de refatoring
 *
 * @author patricia.macedo
 */
public class MainGui extends Application {
    private ShoppingCart shoppingCart;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        shoppingCart = new ShoppingCart();
        View view = new View(shoppingCart);
    }
}
