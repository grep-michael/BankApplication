package bank.gui;
import bank.Customer;
import bank.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Menus {
    private static int WIDTH = 800;
    private static int HIEGHT = 600;
    private static Customer logedInCustomer;

    public Scene login(){
        Label message = new Label("Enter pin!");
        TextField pin = new TextField();
        pin.setMaxWidth(100);
        Button logon = new Button("Login");
        Label error = new Label("");

        logon.setOnAction(event -> {
            try{
                Customer customer = Customer.loadFromJson(pin.getText());
                logedInCustomer = customer;
                Main.update(this.customerDash());
            }catch (IOException e) {
                error.setText("Error finding account with that pin");
            }
        });

        VBox vbox = new VBox(10,message,pin,logon,error);
        vbox.setAlignment(Pos.CENTER);

        vbox.setPadding(new Insets(30));
        Scene scene = new Scene(vbox,WIDTH,HIEGHT);
        return scene;
    }
    public Scene mainMenu(){
        Label message = new Label("Welcome to Bank");

        Button createBTN = new Button("Login");
        createBTN.setOnAction(event -> {
            Main.update(this.login());
        });

        Button accessBTN = new Button("Create Customer account");
        accessBTN.setOnAction(event -> {
            Main.update(this.createCustomer());
        });

        Button exit = new Button("Exit");
        exit.setOnAction(event ->{
            System.exit(0);
        });

        VBox vbox = new VBox(10,message,createBTN,accessBTN,exit);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(30));
        Scene scene = new Scene(vbox,WIDTH,HIEGHT);
        return scene;
    }
    public Scene customerDash(){
        DashBoard dash = new DashBoard(logedInCustomer);
        dash.setMOTD(String.format("Welcome %s to your DashBoard!",logedInCustomer.getFirstName()));
        return dash.getDash();
    }
    public Scene createCustomer(){
        Label motd = new Label("Create Account");

        Label firstNameLabel = new Label("First name: ");
        TextField firstName = new TextField();
        HBox firstNameBox = new HBox(10,firstNameLabel,firstName);
        firstNameBox.setAlignment(Pos.CENTER);

        Label lastnameLabel = new Label("Last name: ");
        TextField lastName = new TextField();
        HBox lastNameBox = new HBox(10,lastnameLabel,lastName);
        lastNameBox.setAlignment(Pos.CENTER);

        Label pinLabel = new Label("Enter 4 digit pin");
        TextField pin = new TextField();
        HBox pinBox = new HBox(10,pinLabel,pin);
        pinBox.setAlignment(Pos.CENTER);

        Label errorLabel = new Label("");


        Button createCustomer = new Button("Create Customer");

        createCustomer.setOnAction(event -> {
            try{
                int pin2 = Integer.parseInt(pin.getText());
                if(pin.getText().length() != 4){
                    throw new Exception("error");
                }
                Customer newCustomer = new Customer(firstName.getText(),lastName.getText(),pin.getText());
                try {
                    newCustomer.saveAsJson();
                } catch (Exception e) {
                    errorLabel.setText("Error saving user");
                }
                logedInCustomer = newCustomer;
                Main.update(this.customerDash());
            }catch (Exception e){
                errorLabel.setText("Enter 4 digit pin");
            }
        });

        VBox vbox = new VBox(10,motd,firstNameBox,lastNameBox,pinBox,createCustomer,errorLabel);
        vbox.setPadding(new Insets(30));
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox,WIDTH,HIEGHT);
        return scene;
    }

}

