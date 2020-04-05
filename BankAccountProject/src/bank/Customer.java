package bank;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Customer {
    private String firstName;
    private String lastName;
    private String pin;
    private ArrayList<Account> accounts = new ArrayList<>();
    public Customer(String firstName, String lastName, String pin){
        this.firstName = firstName;
        this.lastName = lastName;
        this.pin = pin;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getPin() {
        return pin;
    }
    public ArrayList<Account> getAccounts() {
        return accounts;
    }
    public void addAccount(Account a) throws IOException {
        this.accounts.add(a);
        this.saveAsJson();
    }
    public static Customer loadFromJson(String pin) throws FileNotFoundException {
        Gson gson = new Gson();
        BufferedReader reader = new BufferedReader(new FileReader(String.format("%s.json",pin)));
        Type customerType = new TypeToken<Customer>(){}.getType();
        Customer customer;
        customer = gson.fromJson(reader,customerType);
        return customer;
    }
    public void saveAsJson() throws IOException {
        Gson gson = new Gson();
        String jsonString = gson.toJson(this);
        String filename = String.format("%s.json",pin);
        FileWriter fileWriter = new FileWriter(filename,false);
        fileWriter.write(jsonString);
        fileWriter.close();
    }

    public String[] getAccountsAsStrings(){
        String[] accountNames = new String[accounts.size()];
        for(int i =0;i<accounts.size();i++){
            accountNames[i] = accounts.get(i).getAccountNumber();
        }
        return accountNames;
    }

    public Account getAccount(String id){
        for(Account i : accounts){
            if(i.getAccountNumber().equalsIgnoreCase(id)){
                return i;
            }
        }
        return new Account(0,0,"null","null");
    }

}
