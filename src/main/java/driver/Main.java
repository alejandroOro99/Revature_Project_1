package driver;
import com.github.cliftonlabs.json_simple.Jsoner;
import controller.JlinController;
import dao.CustomerDAOImpl;
import io.javalin.Javalin;
import model.Customer;
import org.apache.log4j.Logger;
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

            //These lines deserialize the request and
            jsonObject = (JsonObject)Jsoner.deserialize(ctx.body());

            String username = jsonObject.get("username").toString();
            String password = jsonObject.get("password").toString();
            String firstname = jsonObject.get("firstname").toString();
            String lastname = jsonObject.get("lastname").toString();

            if(customerService.isUsernameAvailable(username)){
                customerService.applyCustomerAcc(firstname, lastname, username, password);
                ctx.result("account created");
            }else{
                ctx.result("username not available");
            }


        });

        //Login to customer account feature
        app.post("/customer/login",ctx->{

            jsonObject = (JsonObject) Jsoner.deserialize(ctx.body());
            String username = jsonObject.get("username").toString();
            String password = jsonObject.get("password").toString();
            customer = customerService.login(username, password);

            if(customer != null){
                ctx.result("login success");
            }else{
                ctx.result("login failed");
                customer = null;
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

    }
}
