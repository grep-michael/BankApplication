package bank.gui.Dashboard;

import bank.Account;
import bank.Customer;
import bank.gui.DashBoard;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.IOException;

public class EditAccountMenu {
    private static Customer logedInCustomer;
    private DashBoard currentDash;
    private static int WIDTH = 800;
    private static int HIEGHT = 600;
    public EditAccountMenu(Customer c,DashBoard dash){
        this.logedInCustomer = c;
        this.currentDash = dash;
    }

    public VBox getEditMenu(){
        //use final array because the array is constant but its elements arent, allowing us to change them in a lamda expression
        final Account[] selectedAccount = new Account[1];
        //selector
        ListView<String> list = new ListView<>();

        list.getItems().addAll(logedInCustomer.getAccountsAsStrings());
        list.setPrefSize(WIDTH/4, HIEGHT/3-10);
        Label info = new Label("No account selected");
        list.getSelectionModel().selectedItemProperty().addListener(event ->{
            selectedAccount[0] = logedInCustomer.getAccount(list.getSelectionModel().getSelectedItem());
            info.setText(String.format("Account %s\nCurrent balance: %.2f",selectedAccount[0].getAccountNumber(),selectedAccount[0].getAmmount()));
        });
        HBox listBox = new HBox(10, list,info);

        //withdraw / deposit
        TextField dwAmount = new TextField();
        Label  message = new Label("Ammount to deposit/withdraw");
        Button deposit = new Button("Deposit");
        Button withdraw = new Button("Withdraw");

        deposit.setOnAction(event ->{
            double ammount = Double.parseDouble(dwAmount.getText());
            selectedAccount[0].deposit(ammount);
            currentDash.setMOTD(String.format("Deposited %.2f into %s",ammount,selectedAccount[0].getAccountNumber()));
            try {
                logedInCustomer.saveAsJson();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        withdraw.setOnAction(event ->{
            double ammount = Double.parseDouble(dwAmount.getText());
            selectedAccount[0].withDraw(ammount);
            currentDash.setMOTD(String.format("Withdrew %.2f from %s",ammount,selectedAccount[0].getAccountNumber()));
            try {
                logedInCustomer.saveAsJson();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        HBox buttons = new HBox(20,deposit,withdraw);
        VBox dwBox = new VBox(10,message,dwAmount,buttons);
        Label message1 = new Label("Edit existing account");
        VBox editbox = new VBox(10,message1,listBox,dwBox);
        editbox.setStyle("-fx-border-color: black; -fx-border-style: none none solid none;");
        editbox.setAlignment(Pos.BASELINE_CENTER);
        editbox.setPadding(new Insets(20));
        editbox.setMinHeight(HIEGHT-(HIEGHT/8));
        editbox.setMinWidth(WIDTH/2);

        return editbox;
    }
}
