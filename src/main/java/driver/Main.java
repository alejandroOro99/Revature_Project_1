package driver;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.Jsoner;
import controller.JlinController;
import dao.BankAccDAO;
import dao.BankAccDAOImpl;
import dao.CustomerDAO;
import dao.CustomerDAOImpl;
import exception.ServiceException;
import io.javalin.Javalin;
import model.BankAccount;
import model.Customer;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import service.BankAccService;
import service.BankAccServiceImpl;
import service.CustomerService;
import service.CustomerServiceImpl;
import com.github.cliftonlabs.json_simple.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
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
                ctx.json("applyAcc success");
            }catch(Exception e){
                log.debug(e);
                ctx.json(String.valueOf(e));
            }

        });

        //Deposit feature
        app.post("/customer/deposit",ctx->{
            try{
                jsonObject = (JsonObject) Jsoner.deserialize(ctx.body());
                String accName = jsonObject.get("accName").toString();
                long depositAmount = Long.parseLong(jsonObject.get("depositAmount").toString());
                ctx.json(String.valueOf(customerService.deposit(accName, depositAmount, customer)));
                transactions.debug(customer.getUsername()+" deposited $"+depositAmount+" in "+accName);
            }catch(Exception e){
                e.printStackTrace();
                ctx.json(String.valueOf(e));
            }
        });

        //Withdraw feature
        app.post("/customer/withdraw",ctx->{

            log.debug("withdraw");
            try{
                jsonObject = (JsonObject) Jsoner.deserialize(ctx.body());
                String accName = jsonObject.get("accName").toString();
                long withdrawAmount = Long.parseLong(jsonObject.get("withdrawAmount").toString());

                customerService.withdraw(accName, customer, withdrawAmount);
                transactions.debug(customer.getUsername()+" withdrew $"+withdrawAmount+" from "+accName);
                ctx.json("success");
            }catch(ServiceException e){
                ctx.json(String.valueOf(e));
            }

        });

        //View acc feature
        app.get("/customer/:accName",ctx->{
            try{
                String viewAccBalanceName = ctx.pathParam("accName");
                ctx.json(customerService.viewAccBalance(viewAccBalanceName,customer));
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
                transactions.debug(customer.getUsername()+" posted $"+transferAmount+" to "+username+" from "+senderAcc);
                ctx.json("success");
            }catch(NumberFormatException | ServiceException e){
                ctx.json(String.valueOf(e));
            }
        });

        //Customer Handle transfers feature
        app.get("/customer/handleTransfer/:accName/:accToDeposit",ctx->{

            try{

                String accName = ctx.pathParam("accName").toString();
                String accToDeposit = ctx.pathParam("accToDeposit").toString();

                if(customerService.acceptTransfer(customer,accToDeposit, accName)){
                    transactions.debug(customer.getUsername()+" accepted transfer from "+accName);
                    ctx.json("success");
                }else{
                    ctx.json("failed");
                }


            }catch(Exception e){
                ctx.json(String.valueOf(e));
            }
        });

        //Display pending transfers
        app.get("/customer/view/:username",ctx->{

            Customer customerTransfer = customerService.getCustomerByUsername(ctx.pathParam("username"));
            ctx.json(customerService.displayCustomerByTransfer(customer));

        });
        //Create employee
        app.post("/employee/apply",ctx->{
            try{
                jsonObject = (JsonObject) Jsoner.deserialize(ctx.body());
                String firstname = jsonObject.get("firstname").toString();
                String lastname = jsonObject.get("lastname").toString();
                String username = jsonObject.get("username").toString();
                String password = jsonObject.get("password").toString();
                int employeenum = 1;

                if(customerService.isUsernameAvailable(username,1)){
                    ctx.json(customerService.applyCustomerAcc(firstname,lastname, username, password, employeenum));
                }else{
                    ctx.json("username in use");
                }


            }catch(Exception e){
                ctx.json(String.valueOf(e));
            }
        });

        //login employee
        app.post("/employee/login",ctx->{
            try{
                jsonObject = (JsonObject) Jsoner.deserialize(ctx.body());
                String username = jsonObject.get("username").toString();
                String password = jsonObject.get("password").toString();


                    customer = customerService.login(username, password,1);//employee login overloaded


                if(customer != null){
                    ctx.json("login success");
                }else{
                    customer = null;
                    ctx.json("login failed");
                }


            }catch(Exception e){
                e.printStackTrace();
                ctx.json("login failed");
                customer = null;
            }
        });

        //Bank account application's handling feature
        app.get("/employee/applications/:username",ctx->{
            CustomerDAO customerDAO = new CustomerDAOImpl();
            log.debug("Enter the username of the customer");
            String acceptAccUsername = ctx.pathParam("username");
            Customer acceptAccCustomer = customerDAO.getCustomerByUsername(acceptAccUsername);

            bankAccService.getStatusZeroAccounts(acceptAccCustomer);

        });

        //View transactions.log for customer
        app.get("/customer/viewTransactions/:a",ctx->{
            List<String> transactionsList = new ArrayList<>();
            JsonObject jsonObject = new JsonObject();
            JSONArray arr = new JSONArray();
            JSONArray arrDeposits = new JSONArray();
            String date;
            while(fileScanner.hasNextLine()){
                String line = fileScanner.nextLine();
                if(line.contains(customer.getUsername())){
                    transactionsList.add(line);
                }
            }

            for(String s : transactionsList){
                int index = s.indexOf(",");
                if(s.contains("deposited")){
                    //find where substrings end and start depending on special characters($) and spaces
                    date = s.substring(0,index);
                    int index$ = s.indexOf("$");
                    String index$String = s.substring(index$);
                    int indexEnd$ = index$String.indexOf(" ");
                    String value = index$String.substring(0,indexEnd$);

                    arr.add("Date: "+date+" Deposit amount="+value);
                }else if(s.contains("|")){// | is used in accepting bank accounts nextTO it is name of banker who approved
                    date = s.substring(0,index);
                    int indexAcceptUser = s.indexOf("|")+1;
                    String indexAcceptUserString = s.substring(indexAcceptUser);
                    int indexEnd = indexAcceptUserString.indexOf(" ");
                    String value = indexAcceptUserString.substring(0,indexEnd);

                    int indexUsername = indexAcceptUserString.indexOf("account:");
                    String indexUsernameString = indexAcceptUserString.substring(indexUsername);
                    arr.add("Date: "+date+", employee: "+value+" approved: "+indexUsernameString);

                }else if(s.contains("withdrew")){
                    date = s.substring(0,index);
                    int indexWithdrewName = s.indexOf(customer.getUsername());
                    String indexWithdrewNameString = s.substring(indexWithdrewName);
                    arr.add("Date: "+date+", user "+indexWithdrewNameString);
                }else if (s.contains("posted")){
                    date = s.substring(0,index);
                    int indexPosted = s.indexOf(customer.getUsername());
                    String indexPostedString = s.substring(indexPosted);
                    arr.add("Date: "+date+", user "+indexPostedString);
                }

            }
            //creates json object with array for dates
            jsonObject.put("dates",arr);
            //jsonObject.put("deposits",arrDeposits);
            //Have to re-initialize scanner so it points to the beginning of file again
            try {
                fileScanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                log.debug(e);
            }
            ctx.json(jsonObject);
        });

        //view transactions for employee
        app.get("/employee/transactionsDate/:date",ctx->{
            String date = ctx.pathParam("date");
            List<String> transactionsList = new ArrayList<>();
            JsonObject jsonObject = new JsonObject();
            JSONArray arr = new JSONArray();
            JSONArray arrDeposits = new JSONArray();
            while(fileScanner.hasNextLine()){
                String line = fileScanner.nextLine();
                if(line.contains(date)){
                    transactionsList.add(line);
                }
            }

            for(String s : transactionsList){
                //split strings by start and end of []
                int indexMain = s.indexOf("[");
                String firstHalf = s.substring(0,indexMain);
                String indexMainString = s.substring(indexMain);
                int indexLast = indexMainString.indexOf("]");
                String secondHalf = indexMainString.substring(indexLast);
                String value = firstHalf + secondHalf;
                arr.add(value);
            }
            jsonObject.put("transactions",arr);
            //reset scanner
            try {
                fileScanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                log.debug(e);
            }
            ctx.json(jsonObject);
        });

        //GET customer accounts
        app.get("/employee/getCustomer/:username/:password",ctx->{
            JsonObject json = new JsonObject();
            BankAccDAO bankAccDAOC1C3 = new BankAccDAOImpl();
            log.debug("Put username of user");
            String usernameViewAcc = ctx.pathParam("username").toString();
            log.debug("Put password of user");
            String passwordViewAcc = ctx.pathParam("password").toString();

            //method for sending json array
            log.debug(usernameViewAcc+" "+passwordViewAcc);
            Customer customerViewAcc = bankAccService.selectCustomer(usernameViewAcc,passwordViewAcc);
            List<BankAccount> bankAccountListViewAcc = bankAccService.viewAccounts(customerViewAcc);
            JSONArray arr = new JSONArray();
            for(BankAccount b : bankAccountListViewAcc){
                JsonObject bankacc = new JsonObject();
                log.debug(b.getBankAccId());
                bankacc.put("accountID", b.getBankAccId());
                bankacc.put("balance", b.getBalance());
                bankacc.put("name",b.getName());
                arr.add(bankacc);
            }

            json.put("accounts",arr);

            ctx.json(json);
        });

        //get all transactions
        app.get("/employee/allTransactions/:a",ctx->{
            List<String> transactionsList = new ArrayList<>();
            JsonObject jsonObject = new JsonObject();
            JSONArray arr = new JSONArray();
            JSONArray arrDeposits = new JSONArray();
            String date = "";
            while(fileScanner.hasNextLine()){
                String line = fileScanner.nextLine();
                if(line.contains("")){
                    transactionsList.add(line);
                }

            }
            for(String s : transactionsList){
                int indexMain = s.indexOf("[");
                String firstHalf = s.substring(0,indexMain);
                String indexMainString = s.substring(indexMain);
                int indexLast = indexMainString.indexOf("]");
                String secondHalf = indexMainString.substring(indexLast);
                String value = firstHalf + secondHalf;
                arr.add(value);
            }
            jsonObject.put("transactions",arr);
            //reset scanner
            try {
                fileScanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                log.debug(e);
            }
            ctx.json(jsonObject);
        });
        //approve customer accounts
        app.get("/employee/approve/:username/:name",ctx->{
            String acceptedAccounts = ctx.pathParam("name");
            String acceptAccUsername = ctx.pathParam("username");;
            Customer acceptAccCustomer = customerService.getCustomerByUsername(acceptAccUsername);
            try{
                bankAccService.acceptAccounts(acceptedAccounts, acceptAccCustomer);
                ctx.json("success");
                transactions.debug("|"+customer.getUsername()+" accepted account:"+acceptedAccounts+
                        ", username: "+acceptAccUsername);
            }catch(Exception e){
                ctx.json(String.valueOf(e));
            }

        });

        //view customer applications
        app.get("/employee/viewApplications/:username",ctx -> {

            log.debug("Enter the username of the customer");
            String acceptAccUsername = ctx.pathParam("username").toString();
            Customer acceptAccCustomer = customerService.getCustomerByUsername(acceptAccUsername);

            log.debug("Below are the accounts pending acceptance");

            //method for sending json array
            List<String> accNames = bankAccService.getStatusZeroAccounts(acceptAccCustomer);
            JSONArray arr = new JSONArray();
            JsonObject jsonObj = new JsonObject();
            for(String s : accNames){
                JsonObject jsonAcc = new JsonObject();
                jsonAcc.put("account", s);
                arr.add( jsonAcc);
            }
            jsonObj.put("accounts",arr);

            ctx.json(jsonObj);

        });

    }
}
