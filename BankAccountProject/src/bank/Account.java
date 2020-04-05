package bank;

public class Account {
    private double ammount;
    private double interestRate;
    private String accountNumber;
    private String type;
    public Account(double ammount,double ir, String number,String type){
        this.ammount = ammount;
        this.accountNumber = number;
        this.interestRate = ir;
        this.type=type;
    }

    public void deposit(double ammount){
        this.ammount += ammount;
    }
    public void withDraw(double ammount){
        this.ammount -= ammount;
    }
    public String getType(){
        return type;
    }
    public double getAmmount() {
        return ammount;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
}
