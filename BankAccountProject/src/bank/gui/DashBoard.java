package bank.gui;

import bank.Customer;
import bank.Main;
import bank.gui.Dashboard.CreateAccountMenu;
import bank.gui.Dashboard.EditAccountMenu;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class DashBoard {
    private static Customer logedInCustomer;
    public DashBoard(Customer c){
        this.logedInCustomer = c;
    }
    private static int WIDTH = 800;
    private static int HIEGHT = 600;
    private String motdString = "";

    public void setMOTD(String s){
        this.motdString = s;
        Main.update(this.getDash());
    }

    public Scene getDash(){
        //motd
        Label motd = new Label(this.motdString);
        VBox motdbox = new VBox(motd);
        motdbox.setAlignment(Pos.CENTER);
        motdbox.setMinHeight(HIEGHT/8);
        motdbox.setMinWidth(WIDTH);

        //edit menu
        EditAccountMenu editMenu = new EditAccountMenu(logedInCustomer,this);
        //create menu
        CreateAccountMenu createMenu = new CreateAccountMenu(logedInCustomer,this);


        GridPane root = new GridPane();
        root.add(editMenu.getEditMenu(),0,0);
        root.add(createMenu.getCreateMenu(),1,0);
        root.add(motdbox,0,1,2,1);
        root.setAlignment(Pos.BASELINE_CENTER);
        root.setGridLinesVisible(false);
        Scene scene = new Scene(root,WIDTH, HIEGHT);
        return scene;
    }
}
