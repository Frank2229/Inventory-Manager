package com.poboys.inventorymanager;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class Main  extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        // MAIN MENU SETUP

        Label mainLabel = new Label("Inventory Manager");
        mainLabel.setStyle("-fx-font-family: Arial; -fx-font-size: 35px; -fx-font-style: italic;");
        Button addRemoveArticleButton = new Button("Add/Edit/Remove Article Entry");
        addRemoveArticleButton.getStyleClass().add("main-button");
        Button receiveInventoryButton = new Button("Receive Inventory");
        receiveInventoryButton.getStyleClass().add("main-button");
        Button articleSearchButton = new Button("Search For Article");
        articleSearchButton.getStyleClass().add("main-button");
        VBox mainButtonsVBox = new VBox(addRemoveArticleButton, receiveInventoryButton, articleSearchButton);
        mainButtonsVBox.setAlignment(Pos.CENTER);
        mainButtonsVBox.setSpacing(20);
        VBox mainVBox = new VBox(mainLabel, mainButtonsVBox);
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setSpacing(50);

        // ADD/REMOVE ARTICLE SETUP

        Label addRemoveArticleScanLabel = new Label("Enter/Scan Article:   ");
        TextField addRemoveArticleTextField = new TextField();
        HBox addRemoveArticleMainHBox = new HBox(addRemoveArticleScanLabel,addRemoveArticleTextField);
        addRemoveArticleMainHBox.setAlignment(Pos.CENTER);

        GridPane addArticleGridPane = new GridPane();
        addArticleGridPane.setAlignment(Pos.CENTER);
        addArticleGridPane.setVgap(10);
        Label addArticleNameLabel = new Label("Product Name:  ");
        addArticleGridPane.add(addArticleNameLabel,0,0);
        TextField addArticleNameTextField = new TextField();
        addArticleGridPane.add(addArticleNameTextField,1,0);
        Label addArticleUPCLabel = new Label("UPC:  ");
        addArticleGridPane.add(addArticleUPCLabel,0,1);
        TextField addArticleUPCTextField = new TextField();
        addArticleUPCTextField.setDisable(true);
        addArticleGridPane.add(addArticleUPCTextField,1,1);
        Label addArticleOnHandLabel = new Label("Current On-Hand:  ");
        addArticleGridPane.add(addArticleOnHandLabel,0,2);
        TextField addArticleOnHandTextField = new TextField("0");
        addArticleOnHandTextField.setDisable(true);
        addArticleGridPane.add(addArticleOnHandTextField,1,2);
        Label addArticleCostLabel = new Label("Cost:  ");
        addArticleGridPane.add(addArticleCostLabel,0,3);
        TextField addArticleCostTextField = new TextField();
        addArticleGridPane.add(addArticleCostTextField,1,3);
        Label addArticleRetailLabel = new Label("Retail Price:  ");
        addArticleGridPane.add(addArticleRetailLabel,0,4);
        TextField addArticleRetailTextField = new TextField();
        addArticleGridPane.add(addArticleRetailTextField,1,4);
        Label addArticleTaxLabel = new Label("Tax:  ");
        addArticleGridPane.add(addArticleTaxLabel, 0, 5);
        TextField addArticleTaxTextField = new TextField();
        addArticleGridPane.add(addArticleTaxTextField, 1, 5);
        Button addArticleButton = new Button("Add");
        addArticleButton.getStyleClass().add("regular-button");
        addArticleGridPane.add(addArticleButton,0,6);

        GridPane editArticleGridPane = new GridPane();
        editArticleGridPane.setAlignment(Pos.CENTER);
        editArticleGridPane.setVgap(10);
        Label editArticleNameLabel = new Label("Product Name:  ");
        editArticleGridPane.add(editArticleNameLabel,0,0);
        TextField editArticleNameTextField = new TextField();
        editArticleNameTextField.setDisable(true);
        editArticleGridPane.add(editArticleNameTextField,1,0);
        Label editArticleUPCLabel = new Label("UPC:  ");
        editArticleGridPane.add(editArticleUPCLabel,0,1);
        TextField editArticleUPCTextField = new TextField();
        editArticleUPCTextField.setDisable(true);
        editArticleGridPane.add(editArticleUPCTextField,1,1);
        Label editArticleOnHandLabel = new Label("Current On-Hand:  ");
        editArticleGridPane.add(editArticleOnHandLabel,0,2);
        TextField editArticleOnHandTextField = new TextField("0");
        editArticleOnHandTextField.setDisable(true);
        editArticleGridPane.add(editArticleOnHandTextField,1,2);
        Label editArticleCostLabel = new Label("Cost:  ");
        editArticleGridPane.add(editArticleCostLabel,0,3);
        TextField editArticleCostTextField = new TextField();
        editArticleGridPane.add(editArticleCostTextField,1,3);
        Label editArticleRetailLabel = new Label("Retail Price:  ");
        editArticleGridPane.add(editArticleRetailLabel,0,4);
        TextField editArticleRetailTextField = new TextField();
        editArticleGridPane.add(editArticleRetailTextField,1,4);
        Label editArticleTaxLabel = new Label("Tax:  ");
        editArticleGridPane.add(editArticleTaxLabel, 0, 5);
        TextField editArticleTaxTextField = new TextField();
        editArticleGridPane.add(editArticleTaxTextField, 1, 5);
        Button editArticleButton = new Button("Change");
        editArticleButton.getStyleClass().add("regular-button");
        editArticleGridPane.add(editArticleButton,0,6);
        Button removeArticleButton = new Button("Remove");
        removeArticleButton.getStyleClass().add("regular-button");
        editArticleGridPane.add(removeArticleButton, 1,6);

        Button backAddRemoveButton = new Button("Back");
        backAddRemoveButton.getStyleClass().add("regular-button");

        VBox addRemoveArticleMainVBox = new VBox(addRemoveArticleMainHBox, backAddRemoveButton);
        addRemoveArticleMainVBox.setAlignment(Pos.CENTER);
        addRemoveArticleMainVBox.setSpacing(50);

        // RECEIVE INVENTORY SETUP

        LinkedList<Object[]> receivingTable = new LinkedList<Object[]>();

        Label receiveInventoryScanLabel = new Label("Enter/Scan Article:   ");
        TextField receiveInventoryTextField = new TextField();
        HBox receiveInventoryMainHBox = new HBox(receiveInventoryScanLabel, receiveInventoryTextField);
        receiveInventoryMainHBox.setAlignment(Pos.CENTER);

        GridPane receiveInventoryGridPane = new GridPane();
        receiveInventoryGridPane.setAlignment(Pos.CENTER);
        receiveInventoryGridPane.setVgap(10);
        Label receiveInventoryNameLabel = new Label("Product Name:  ");
        receiveInventoryGridPane.add(receiveInventoryNameLabel,0,0);
        TextField receiveInventoryNameTextField = new TextField();
        receiveInventoryNameTextField.setDisable(true);
        receiveInventoryGridPane.add(receiveInventoryNameTextField,1,0);
        Label receiveInventoryUPCLabel = new Label("UPC:  ");
        receiveInventoryGridPane.add(receiveInventoryUPCLabel,0,1);
        TextField receiveInventoryUPCTextField = new TextField();
        receiveInventoryUPCTextField.setDisable(true);
        receiveInventoryGridPane.add(receiveInventoryUPCTextField,1,1);
        Label receiveInventoryTotalQtyLabel = new Label("Total Quantity:  ");
        receiveInventoryGridPane.add(receiveInventoryTotalQtyLabel,0,2);
        TextField receiveInventoryTotalQtyTextField = new TextField();
        receiveInventoryTotalQtyTextField.setDisable(true);
        receiveInventoryGridPane.add(receiveInventoryTotalQtyTextField,1,2);
        Label receiveInventoryQtyLabel = new Label("Quantity:  ");
        receiveInventoryGridPane.add(receiveInventoryQtyLabel,0,3);
        TextField receiveInventoryQtyTextField = new TextField();
        receiveInventoryGridPane.add(receiveInventoryQtyTextField,1,3);
        Button receiveInventoryAddButton = new Button("Add");
        receiveInventoryAddButton.getStyleClass().add("regular-button");
        receiveInventoryGridPane.add(receiveInventoryAddButton,0,4);
        Button receiveInventoryDeleteButton = new Button("Delete");
        receiveInventoryDeleteButton.getStyleClass().add("regular-button");
        receiveInventoryGridPane.add(receiveInventoryDeleteButton, 1,4);

        Label receiveInventoryNotFoundLabel = new Label("Article not found.");
        receiveInventoryNotFoundLabel.setStyle("-fx-font-family: Arial; -fx-font-size: 20px; -fx-font-style: italic;");

        Button postReceiveInventoryButton = new Button("Post");
        postReceiveInventoryButton.getStyleClass().add("regular-button");
        Button resetReceiveInventoryButton = new Button("Reset");
        resetReceiveInventoryButton.getStyleClass().add("regular-button");
        Button backReceiveInventoryButton = new Button("Back");
        backReceiveInventoryButton.getStyleClass().add("regular-button");
        HBox receiveInventoryButtonHBox = new HBox(postReceiveInventoryButton, resetReceiveInventoryButton, backReceiveInventoryButton);
        receiveInventoryButtonHBox.setAlignment(Pos.CENTER);
        receiveInventoryButtonHBox.setSpacing(20);

        VBox receiveInventoryMainVBox = new VBox(receiveInventoryMainHBox, receiveInventoryButtonHBox);
        receiveInventoryMainVBox.setAlignment(Pos.CENTER);
        receiveInventoryMainVBox.setSpacing(50);

        // SEARCH FOR ARTICLE SETUP

        Label searchArticleScanLabel = new Label("Enter/Scan Article:   ");
        TextField searchArticleTextField = new TextField();
        HBox searchArticleMainHBox = new HBox(searchArticleScanLabel, searchArticleTextField);
        searchArticleMainHBox.setAlignment(Pos.CENTER);

        GridPane searchArticleGridPane = new GridPane();
        searchArticleGridPane.setAlignment(Pos.CENTER);
        searchArticleGridPane.setVgap(10);
        Label searchArticleNameLabel = new Label("Product Name:  ");
        searchArticleGridPane.add(searchArticleNameLabel,0,0);
        TextField searchArticleNameTextField = new TextField();
        searchArticleNameTextField.setDisable(true);
        searchArticleGridPane.add(searchArticleNameTextField,1,0);
        Label searchArticleUPCLabel = new Label("UPC:  ");
        searchArticleGridPane.add(searchArticleUPCLabel,0,1);
        TextField searchArticleUPCTextField = new TextField();
        searchArticleUPCTextField.setDisable(true);
        searchArticleGridPane.add(searchArticleUPCTextField,1,1);
        Label searchArticleOnHandLabel = new Label("Current On-Hand:  ");
        searchArticleGridPane.add(searchArticleOnHandLabel,0,2);
        TextField searchArticleOnHandTextField = new TextField("0");
        searchArticleOnHandTextField.setDisable(true);
        searchArticleGridPane.add(searchArticleOnHandTextField,1,2);
        Label searchArticleCostLabel = new Label("Cost:  ");
        searchArticleGridPane.add(searchArticleCostLabel,0,3);
        TextField searchArticleCostTextField = new TextField();
        searchArticleCostTextField.setDisable(true);
        searchArticleGridPane.add(searchArticleCostTextField,1,3);
        Label searchArticleRetailLabel = new Label("Retail Price:  ");
        searchArticleGridPane.add(searchArticleRetailLabel,0,4);
        TextField searchArticleRetailTextField = new TextField();
        searchArticleRetailTextField.setDisable(true);
        searchArticleGridPane.add(searchArticleRetailTextField,1,4);
        Label searchArticleTaxLabel = new Label("Tax:  ");
        searchArticleGridPane.add(searchArticleTaxLabel, 0, 5);
        TextField searchArticleTaxTextField = new TextField();
        searchArticleTaxTextField.setDisable(true);
        searchArticleGridPane.add(searchArticleTaxTextField, 1, 5);

        Label searchNotFoundLabel = new Label("Article not found.");
        searchNotFoundLabel.setStyle("-fx-font-family: Arial; -fx-font-size: 20px; -fx-font-style: italic;");

        Button backSearchButton = new Button("Back");
        backSearchButton.getStyleClass().add("regular-button");

        VBox searchArticleMainVBox = new VBox(searchArticleMainHBox, backSearchButton);
        searchArticleMainVBox.setAlignment(Pos.CENTER);
        searchArticleMainVBox.setSpacing(50);

        // SCENE/STAGE SETUP

        StackPane root = new StackPane(mainVBox);
        root.getStyleClass().add("background");
        Scene scene = new Scene(root, 1024, 768);
        scene.getStylesheets().add("com/poboys/inventorymanager/application.css");
        stage.setTitle("Inventory Manager");
        stage.setScene(scene);
        stage.show();
        stage.setFullScreen(true);

        // BUTTON EVENTS

        addArticleButton.setOnAction(event -> {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/po_boys","root","Musician2229");
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO inventory (product_name, upc, onhand, cost, retail, tax) VALUES (?, ?, ?, ?, ?, ?)");
                preparedStatement.setString(1, addArticleNameTextField.getText());
                preparedStatement.setString(2, addArticleUPCTextField.getText());
                preparedStatement.setFloat(3, parseFloat(addArticleOnHandTextField.getText()));
                preparedStatement.setFloat(4, parseFloat(addArticleCostTextField.getText()));
                preparedStatement.setFloat(5, parseFloat(addArticleRetailTextField.getText()));
                preparedStatement.setFloat(6, parseFloat(addArticleRetailTextField.getText()));
                preparedStatement.execute();
                addRemoveArticleMainVBox.getChildren().remove(1);
                clearAddArticleFields(addArticleNameTextField, addArticleUPCTextField, addArticleOnHandTextField, addArticleCostTextField, addArticleRetailTextField, addArticleTaxTextField);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        addRemoveArticleButton.setOnAction(event -> {
            root.getChildren().remove(mainVBox);
            root.getChildren().add(addRemoveArticleMainVBox);
        });

        receiveInventoryButton.setOnAction(event -> {
            root.getChildren().remove(mainVBox);
            root.getChildren().add(receiveInventoryMainVBox);
        });

        articleSearchButton.setOnAction(event -> {
            root.getChildren().remove(mainVBox);
            root.getChildren().add(searchArticleMainVBox);
        });

        backAddRemoveButton.setOnAction(event -> {
            root.getChildren().clear();
            root.getChildren().add(mainVBox);
        });

        backReceiveInventoryButton.setOnAction(event -> {
            root.getChildren().clear();
            root.getChildren().add(mainVBox);
        });

        backSearchButton.setOnAction(event -> {
            root.getChildren().clear();
            root.getChildren().add(mainVBox);
        });

        editArticleButton.setOnAction(event -> {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/po_boys","root","Musician2229");
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE inventory SET cost = ?, retail = ? , tax = ? WHERE upc = " + editArticleUPCTextField.getText());
                preparedStatement.setFloat(1, parseFloat(editArticleCostTextField.getText()));
                preparedStatement.setFloat(2, parseFloat(editArticleRetailTextField.getText()));
                preparedStatement.setFloat(3, parseFloat(editArticleTaxTextField.getText()));
                preparedStatement.execute();
                addRemoveArticleMainVBox.getChildren().remove(1);
                clearAddArticleFields(editArticleNameTextField, editArticleUPCTextField, editArticleOnHandTextField, editArticleCostTextField, editArticleRetailTextField, editArticleTaxTextField);
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        postReceiveInventoryButton.setOnAction(event -> {
            Connection connection = null;
            System.out.println(receivingTable.size());
            float tempFloat = 0;
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/po_boys","root","Musician2229");
                Statement statement = connection.createStatement();
                PreparedStatement preparedStatement;
                ResultSet inventoryResultSet;
                for (int i = 0; i < receivingTable.size(); i++) {
                    inventoryResultSet  = statement.executeQuery("SELECT * FROM inventory");
                    while (inventoryResultSet.next()) {
                        if (inventoryResultSet.getString("upc").equals(receivingTable.get(i)[1].toString())) {
                            tempFloat = parseFloat(receivingTable.get(i)[2].toString()) + inventoryResultSet.getFloat("onhand");
                            preparedStatement = connection.prepareStatement("UPDATE inventory SET onhand = " + tempFloat + " WHERE upc = " + receivingTable.get(i)[1].toString());
                            preparedStatement.execute();
                            System.out.println(receivingTable.get(i)[2]);
                        }
                    }
                }
                receivingTable.clear();
                while (receiveInventoryMainVBox.getChildren().size() > 1) receiveInventoryMainVBox.getChildren().remove(1);
                receiveInventoryMainVBox.getChildren().add(receiveInventoryButtonHBox);
                receiveInventoryQtyTextField.clear();
                root.getChildren().clear();
                root.getChildren().add(mainVBox);
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        receiveInventoryAddButton.setOnAction(event -> {
            boolean doesExist = false;
            float tempFloat = 0;

            for (int i = 0; i < receivingTable.size(); i++) {
                if (receivingTable.get(i)[1].equals(receiveInventoryUPCTextField.getText().toString())) {
                    doesExist = true;
                    tempFloat = parseFloat(receivingTable.get(i)[2].toString());
                    System.out.println(tempFloat);
                    tempFloat += parseFloat(receiveInventoryQtyTextField.getText().toString());
                    receivingTable.get(i)[2] = tempFloat;
                }
            }
            if (doesExist == false) {
                receivingTable.add(new Object[3]);
                receivingTable.get(receivingTable.size() - 1)[0] = receiveInventoryNameTextField.getText().toString();
                receivingTable.get(receivingTable.size() - 1)[1] = receiveInventoryUPCTextField.getText().toString();
                receivingTable.get(receivingTable.size() - 1)[2] = parseFloat(receiveInventoryQtyTextField.getText());
            }
            while (receiveInventoryMainVBox.getChildren().size() > 1) receiveInventoryMainVBox.getChildren().remove(1);
            receiveInventoryMainVBox.getChildren().add(receiveInventoryButtonHBox);
            receiveInventoryQtyTextField.clear();
        });

        receiveInventoryDeleteButton.setOnAction(event -> {
            for (int i = 0; i < receivingTable.size(); i++) {
                if (receivingTable.get(i)[1].equals(receiveInventoryUPCTextField.getText().toString())) {
                    receivingTable.remove(i);
                }
            }
            while (receiveInventoryMainVBox.getChildren().size() > 1) receiveInventoryMainVBox.getChildren().remove(1);
            receiveInventoryMainVBox.getChildren().add(receiveInventoryButtonHBox);
            receiveInventoryQtyTextField.clear();
        });

        removeArticleButton.setOnAction(event -> {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/po_boys","root","Musician2229");
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM inventory WHERE upc = " + editArticleUPCTextField.getText());
                preparedStatement.execute();
                addRemoveArticleMainVBox.getChildren().remove(1);
                clearAddArticleFields(editArticleNameTextField, editArticleUPCTextField, editArticleOnHandTextField, editArticleCostTextField, editArticleRetailTextField, editArticleTaxTextField);
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        resetReceiveInventoryButton.setOnAction(event -> {
            receivingTable.clear();
            while (receiveInventoryMainVBox.getChildren().size() > 1) receiveInventoryMainVBox.getChildren().remove(1);
            receiveInventoryMainVBox.getChildren().add(receiveInventoryButtonHBox);
            receiveInventoryQtyTextField.clear();
        });

        // TEXT FIELD EVENTS

        addRemoveArticleTextField.setOnAction(event -> {
            try {

                // Search all inventory table entries.
                // If there is a match, ask whether item should be edited or deleted.
                // If there is no match, allow user to create a new entry.

                boolean doesExist = false;
                addArticleOnHandTextField.setText("0");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/po_boys","root","Musician2229");
                Statement statement = connection.createStatement();
                ResultSet inventoryResultSet = statement.executeQuery("SELECT * FROM inventory");
                String tempNameString = "";
                String tempOnHandString = "";
                while (inventoryResultSet.next()) {
                    if (inventoryResultSet.getString("upc").equals(addRemoveArticleTextField.getText())) {
                        doesExist = true;
                        tempNameString = inventoryResultSet.getString("product_name");
                        tempOnHandString = inventoryResultSet.getString("onhand");
                    }
                }
                while (addRemoveArticleMainVBox.getChildren().size() > 1) {
                    addRemoveArticleMainVBox.getChildren().remove(1);
                }
                if (doesExist) {
                    editArticleNameTextField.setText(tempNameString);
                    editArticleOnHandTextField.setText(tempOnHandString);
                    addRemoveArticleMainVBox.getChildren().addAll(editArticleGridPane, backAddRemoveButton);
                    editArticleUPCTextField.setText(addRemoveArticleTextField.getText());
                }
                else {
                    addRemoveArticleMainVBox.getChildren().addAll(addArticleGridPane, backAddRemoveButton);
                    addArticleUPCTextField.setText(addRemoveArticleTextField.getText());
                }
                addRemoveArticleTextField.clear();
            }
            catch (SQLException e){
                throw new RuntimeException(e);
            }
        });

        receiveInventoryTextField.setOnAction(event -> {
            try {
                boolean existsInTable = false;
                boolean doesExist = false;

                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/po_boys","root","Musician2229");
                Statement statement = connection.createStatement();
                ResultSet inventoryResultSet = statement.executeQuery("SELECT * FROM inventory");

                for (int i = 0; i < receivingTable.size(); i++) {
                    if (receiveInventoryTextField.getText().equals(receivingTable.get(i)[1])) {
                        existsInTable = true;
                        receiveInventoryNameTextField.setText(receivingTable.get(i)[0].toString());
                        receiveInventoryUPCTextField.setText(receivingTable.get(i)[1].toString());
                        receiveInventoryTotalQtyTextField.setText(receivingTable.get(i)[2].toString());
                    }
                }

                if (existsInTable == false) {
                    while (inventoryResultSet.next()) {
                        if (inventoryResultSet.getString("upc").equals(receiveInventoryTextField.getText())) {
                            doesExist = true;
                            receiveInventoryNameTextField.setText(inventoryResultSet.getString("product_name"));
                            receiveInventoryUPCTextField.setText(inventoryResultSet.getString("upc"));
                            receiveInventoryTotalQtyTextField.setText("0");
                        }
                    }
                }
                while (receiveInventoryMainVBox.getChildren().size() > 1) receiveInventoryMainVBox.getChildren().remove(1);
                if (doesExist || existsInTable) {
                    receiveInventoryMainVBox.getChildren().addAll(receiveInventoryGridPane, receiveInventoryButtonHBox);
                }
                else receiveInventoryMainVBox.getChildren().addAll(receiveInventoryNotFoundLabel, receiveInventoryButtonHBox);
                receiveInventoryTextField.clear();
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        searchArticleTextField.setOnAction(event -> {
            try {

                // Search all inventory table entries.

                boolean doesExist = false;
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/po_boys","root","Musician2229");
                Statement statement = connection.createStatement();
                ResultSet inventoryResultSet = statement.executeQuery("SELECT * FROM inventory");
                String tempNameString = "";
                String tempOnHandString = "";
                String tempCostString = "";
                String tempRetailString = "";
                String tempTaxString = "";
                while (inventoryResultSet.next()) {
                    if (inventoryResultSet.getString("upc").equals(searchArticleTextField.getText())) {
                        doesExist = true;
                        tempNameString = inventoryResultSet.getString("product_name");
                        tempOnHandString = inventoryResultSet.getString("onhand");
                        tempCostString = inventoryResultSet.getString("cost");
                        tempRetailString = inventoryResultSet.getString("retail");
                        tempTaxString = inventoryResultSet.getString("tax");
                    }
                }
                while (searchArticleMainVBox.getChildren().size() > 1) searchArticleMainVBox.getChildren().remove(1);
                if (doesExist) {
                    searchArticleNameTextField.setText(tempNameString);
                    searchArticleOnHandTextField.setText(tempOnHandString);
                    searchArticleCostTextField.setText(tempCostString);
                    searchArticleRetailTextField.setText(tempRetailString);
                    searchArticleTaxTextField.setText(tempTaxString);
                    searchArticleMainVBox.getChildren().addAll(searchArticleGridPane, backSearchButton);
                    searchArticleUPCTextField.setText(searchArticleTextField.getText());
                }
                else searchArticleMainVBox.getChildren().addAll(searchNotFoundLabel, backAddRemoveButton);
                searchArticleTextField.clear();
            }
            catch (SQLException e){
                throw new RuntimeException(e);
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }

    public void clearAddArticleFields(TextField field1, TextField field2, TextField field3, TextField field4, TextField field5, TextField field6) {
        field1.clear();
        field2.clear();
        field3.clear();
        field4.clear();
        field5.clear();
        field6.clear();
    }
}