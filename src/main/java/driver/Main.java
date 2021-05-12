package driver;
import com.github.cliftonlabs.json_simple.Jsoner;
import controller.JlinController;
import dao.BankAccDAO;
import dao.BankAccDAOImpl;
import dao.CustomerDAO;
import dao.CustomerDAOImpl;
import exception.ServiceException;
import io.javalin.Javalin;
import model.Customer;
import org.apache.log4j.Logger;
import service.BankAccService;
import service.BankAccServiceImpl;
import service.CustomerService;
import service.CustomerServiceImpl;
import com.github.cliftonlabs.json_simple.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    private static Logger log = Logger.getLogger(CustomerDAOImpl.class);
    public static CustomerService customerService = new CustomerServiceImpl();
    public static Customer customer;
    public static JsonObject jsonObject;
    public static BankAccService bankAccService = new BankAccServiceImpl();

    private static final Logger transactions = Logger.getLogger("transactionsLogger");
    private static File file = new File(
            "transactions.log");
    private static Scanner fileScanner;

    static {
        try {
            fileScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            log.debug(e);
        }
    }

    public static void main(String[] args) {

        Javalin app = JlinController.startJlin();//Jetty server(localhost:9000)

        //GET Handlers
        app.get("/customer/apply/:username",ctx->{

            String username = ctx.pathParam("username");
            ctx.json(username);
            System.out.println(customerService.isUsernameAvailable(username));
        });

        //Apply for customer account feature
        app.post("/customer/apply",ctx->{

            //These lines deserialize the request and store the fields
            jsonObject = (JsonObject)Jsoner.deserialize(ctx.body());

            String username = jsonObject.get("username").toString();
            String password = jsonObject.get("password").toString();
            String firstname = jsonObject.get("firstname").toString();
            String lastname = jsonObject.get("lastname").toString();

            if(customerService.isUsernameAvailable(username)){
                customerService.applyCustomerAcc(firstname, lastname, username, password);
                ctx.json("account created");
            }else{
                ctx.json("username not available");
            }


        });

        //Login to customer account feature
        app.post("/customer/login",ctx->{

            try{
                jsonObject = (JsonObject) Jsoner.deserialize(ctx.body());
                String username = jsonObject.get("username").toString();
                String password = jsonObject.get("password").toString();
                customer = customerService.login(username, password);

                if(customer != null){
                    ctx.json("login success");
                }else{
                    ctx.json("login failed");
                    customer = null;
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        });

        //Apply for bank account feature
        app.post("/customer/applyAcc",ctx->{
            jsonObject = (JsonObject)Jsoner.deserialize(ctx.body());

            try{
                String accName = jsonObject.get("accName").toString();
                long accBalance = Long.parseLong(jsonObject.get("accBalance").toString());

                log.debug(customerService.applyBankAcc(accName, accBalance, customer));
                transactions.info("Bank account created, name: "+accName+" balance: $"+accBalance);
                ctx.result("applyAcc success");
            }catch(Exception e){
                log.debug(e);
                ctx.result("exception");
            }

        });

        //Deposit feature
        app.post("/customer/deposit",ctx->{
            try{
                jsonObject = (JsonObject) Jsoner.deserialize(ctx.body());
                String accName = jsonObject.get("accName").toString();
                long depositAmount = Long.parseLong(jsonObject.get("depositAmount").toString());
                System.out.println(accName+" "+depositAmount);
                System.out.println(customer);
                ctx.json(String.valueOf(customerService.deposit(accName, depositAmount, customer)));

            }catch(Exception e){
                e.printStackTrace();
                ctx.json(String.valueOf(e));
            }
        });

        //Withdraw feature
        app.post("/customer/withdraw",ctx->{

            try{
                jsonObject = (JsonObject) Jsoner.deserialize(ctx.body());
                String accName = jsonObject.get("accName").toString();
                long withdrawAmount = Long.parseLong(jsonObject.get("withdrawAmount").toString());

                customerService.withdraw(accName, customer, withdrawAmount);
                ctx.json("success");
            }catch(ServiceException e){
                ctx.json(String.valueOf(e));
            }

        });

        //View acc feature
        app.get("/customer/:accName",ctx->{
            try{
                String viewAccBalanceName = ctx.pathParam("accName");
                ctx.json(customerService.viewAccBalance(viewAccBalanceName));
            }catch(Exception e){
                ctx.json(String.valueOf(e));
            }
        });

        //Post transfer feature
        app.post("/customer/postTransfer",ctx->{
            try{
                jsonObject = (JsonObject) Jsoner.deserialize(ctx.body());
                String username = jsonObject.get("username").toString();
                double transferAmount = Double.parseDouble(jsonObject.get("transferAmount").toString());
                String senderAcc = jsonObject.get("senderAcc").toString();
                Customer recipient = customerService.getCustomerByUsername(username);

                customerService.postTransfer(customer, recipient, transferAmount, senderAcc);

            }catch(NumberFormatException | ServiceException e){
                ctx.result(String.valueOf(e));
            }
        });

        //Customer Handle transfers feature
        app.post("/customer/handleTransfer",ctx->{

            try{
                customerService.displayCustomerByTransfer(customer);

                jsonObject = (JsonObject) Jsoner.deserialize(ctx.body());
                String accName = jsonObject.get("accName").toString();
                String accToDeposit = jsonObject.get("accToDeposit").toString();

                customerService.acceptTransfer(customer,accToDeposit, accName);

            }catch(Exception e){
                ctx.result(String.valueOf(e));
            }
        });

        //Bank account application's handling feature
        app.get("/employee/applications/:username",ctx->{
            CustomerDAO customerDAO = new CustomerDAOImpl();
            log.debug("Enter the username of the customer");
            String acceptAccUsername = ctx.pathParam("username");
            Customer acceptAccCustomer = customerDAO.getCustomerByUsername(acceptAccUsername);

            log.debug("Below are the accounts pending acceptance");

            bankAccService.getStatusZeroAccounts(acceptAccCustomer);

            log.debug("Enter the names(separated by a space) of the bank accounts " +
                    "you would like to approve from those above");


        });
    }
}
