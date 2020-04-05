package bank.gui.Dashboard;

import bank.Account;
import bank.Customer;
import bank.gui.DashBoard;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Random;

public class CreateAccountMenu {
    private static Customer logedInCustomer;
    private DashBoard currentDash;
    private static int WIDTH = 800;
    private static int HIEGHT = 600;
    public CreateAccountMenu(Customer c,DashBoard dash){
        this.logedInCustomer = c;
        this.currentDash = dash;
    }

    public VBox getCreateMenu(){



        //radio buttons
        RadioButton checking = new RadioButton("Checking account");
        RadioButton savings = new RadioButton("Savings account");
        HBox radioSelectors = new HBox(10,checking,savings);
        radioSelectors.setPadding(new Insets(10,0,5,0));
        radioSelectors.setAlignment(Pos.CENTER);
        //deposit ammount
        Label depositMessage = new Label("Initial despoit ");
        TextField deposit = new TextField();
        HBox depositBox = new HBox(10,depositMessage,deposit);
        depositBox.setPadding(new Insets(10,0,5,0));
        depositBox.setAlignment(Pos.CENTER);
        //button
        Button createAccount = new Button("Create account");
        //logic

        createAccount.setOnAction(event -> {
            double initialDeposit = Double.parseDouble(deposit.getText());
            double interestRate = 0.0;
            String type = "";
            boolean error = false;
            if (checking.isSelected()) {
                type = "Checking";
                if (initialDeposit >= 10000) {
                    interestRate = 0.05;
                } else {
                    interestRate = 0.02;
                }
            }
            if (savings.isSelected()) {
                type = "Savings";
                if (initialDeposit < 100) {
                    currentDash.setMOTD("Can not Create savings account with initial deposit less that 100$");
                    error = true;
                } else {
                    if (initialDeposit >= 10000) {
                        interestRate = 0.07;
                    } else {
                        interestRate = 0.04;
                    }
                }
            }
            if (!error) {
                Random rand = new Random();
                String accountNum = type+"_"+String.valueOf(rand.nextInt(999999) + 100000);

                Account account = new Account(initialDeposit,interestRate,accountNum,type);
                try {
                    logedInCustomer.addAccount(account);
                    currentDash.setMOTD(String.format("Account '%s' created with initial deposit of %.2f and interest rate of %.2f", accountNum,initialDeposit,interestRate));
                } catch (IOException e) {
                    currentDash.setMOTD("Error occurred adding account");
                }

            }

        });


        Label message2 = new Label("Create new account");
        VBox createBox = new VBox(message2,radioSelectors,depositBox,createAccount);
        createBox.setStyle("-fx-border-color: black; -fx-border-style: none none none soild;");
        createBox.setAlignment(Pos.BASELINE_CENTER);
        createBox.setPadding(new Insets(20));
        createBox.setMinHeight(HIEGHT-(HIEGHT/8));
        createBox.setMinWidth(WIDTH/2);

        return createBox;
    }
}
