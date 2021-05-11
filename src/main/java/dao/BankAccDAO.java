package dao;

import model.BankAccount;
import model.Customer;

import java.util.List;

public interface BankAccDAO {

    List<BankAccount> viewAccounts(Customer customer);
    Customer selectCustomer(String username, String password);
    void getStatusZeroAccounts(Customer customer);
    void acceptAccounts(String name, Customer customer);
}
