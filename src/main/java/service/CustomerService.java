package service;

import exception.ServiceException;
import model.Customer;

import java.util.List;

public interface CustomerService {

    boolean applyCustomerAcc(String firstname, String lastname, String username, String password);
    boolean applyCustomerAcc(String firstname, String lastname, String username, String password,int employeeNum);
    boolean isUsernameAvailable(String username);
    boolean isUsernameAvailable(String username, int employeeNum);//employee check username
    Customer login(String username, String password);
    Customer login(String username, String password,int employeeNum);
    double viewAccBalance(String bankAccount);
    boolean applyBankAcc(String name, double balance , Customer customer);
    void postTransfer(Customer sender, Customer recipient, double amount, String senderName) throws ServiceException;
    boolean acceptTransfer(Customer recipient, String accountName, String acceptedAcc) throws ServiceException;
    boolean deposit(String name, double amount, Customer customer) throws ServiceException;
    void withdraw(String name, Customer customer, double amount) throws ServiceException;
    Customer getCustomerByUsername(String username);
    List<String> displayCustomerByTransfer(Customer customer);
    boolean checkAccStatus(Customer customer, String account);

}
