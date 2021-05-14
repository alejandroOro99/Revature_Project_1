package dao;

import exception.ServiceException;
import model.*;

import java.util.List;
import java.util.Map;

public interface CustomerDAO {
   boolean applyCustomerAcc(String firstname, String lastname, String username, String password);
   boolean applyCustomerAcc(String firstname, String lastname, String username, String password, int employeeNum);
   Customer login(String username, String password);
   Customer login(String username, String password, int employeeNum);//login for employee
   double viewAccBalance(String bankAccount,Customer customer);
   boolean applyBankAcc(String name, double balance , Customer customer);
   void postTransfer(Customer sender, Customer recipient, double amount, String senderName);
   boolean acceptTransfer(Customer recipient, String accountName, String acceptedAccount);
   boolean deposit(String name, double amount, Customer customer);
   void withdraw(String name, Customer customer, double amount) throws ServiceException;
   Customer getCustomerByUsername(String username);
   Map<String,Double> displayCustomerByTransfer(Customer customer);
   boolean checkAccStatus(Customer customer, String account);
   boolean isUsernameAvailable(String username);
   boolean isUsernameAvailable(String username, int employeeNum);//check employee username
}
