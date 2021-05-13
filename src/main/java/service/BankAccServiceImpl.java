package service;

import dao.BankAccDAO;
import dao.BankAccDAOImpl;
import model.BankAccount;
import model.Customer;

import java.util.List;

public class BankAccServiceImpl implements BankAccService{
    private static BankAccDAO bankAccDAO = new BankAccDAOImpl();
    @Override
    public List<BankAccount> viewAccounts(Customer customer) {
        return bankAccDAO.viewAccounts(customer);
    }

    @Override
    public Customer selectCustomer(String username, String password) {

        return bankAccDAO.selectCustomer(username, password);
    }

    @Override
    public List<String> getStatusZeroAccounts(Customer customer) {
        return bankAccDAO.getStatusZeroAccounts(customer);
    }

    @Override
    public void acceptAccounts(String name, Customer customer) {
        bankAccDAO.acceptAccounts(name, customer);
    }
}
