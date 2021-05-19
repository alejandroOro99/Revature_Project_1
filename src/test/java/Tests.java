import dao.CustomerDAO;
import dao.CustomerDAOImpl;
import exception.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.CustomerService;
import service.CustomerServiceImpl;
import model.Customer;
import static org.mockito.Mockito.*;

public class Tests {
    private static CustomerDAO customerDAO;
    private static CustomerService customerService = new CustomerServiceImpl();

    @BeforeAll
    public static void setup(){
        customerDAO = new CustomerDAOImpl();
    }

    /*
    @Test
    void testSelectCustomerFalse(){
        String username = "";
        Assertions.assertEquals(null,customerDAO.getCustomerByUsername(username));
    }
    @Test
    void testCheckAccStatus(){
        Customer customer = new Customer(5775280242015442471l);
        String account = "orozcoAcc";
        Assertions.assertEquals(false,customerDAO.checkAccStatus(customer, account));
    }
    @Test
    void testCheckAccStatusFalse(){
        Customer customer = new Customer(5775280242015442471l);
        String account = "yu";
        Assertions.assertEquals(false,customerDAO.checkAccStatus(customer, account));
    }
    @Test
    void testIsUsernameAvailable(){
        String username ="";
        Assertions.assertEquals(true, customerDAO.isUsernameAvailable(username));
    }
    @Test
    void testIsUsernameAvailableFalse(){
        String username = "1234";
        Assertions.assertEquals(false, customerDAO.isUsernameAvailable(username));
    }
    @Test
    void testDeposit1(){
        Customer customer = new Customer(5775280242015442471l);
        String name = "orozcoAcc";
        double amount = 10;
        Assertions.assertEquals(true,customerDAO.deposit(name, amount, customer));
    }
    @Test
    void testDeposit3(){
        Customer customer = new Customer(5775280242015442471l);
        String name = "orozcoAcc";
        double amount = -1;

        Assertions.assertEquals(true,customerDAO.deposit(name, amount, customer));
    }
    @Test
    void testAcceptTransfer1(){
        Customer customer = new Customer(-1033555330049077587l);
        String accepted = "orozcoAcc";
        String accName = "yu";
        Assertions.assertEquals(false,customerDAO.acceptTransfer(customer, accName, accepted));
    }
    @Test
    void testAcceptTransfer2(){
        Customer customer = new Customer(-1033555330049077587l);
        String accepted = "yu";
        String accName = "orozcoAcc";
        Assertions.assertEquals(false,customerDAO.acceptTransfer(customer, accName, accepted));
    }
    @Test
    void testWithdraw() throws ServiceException {
        Assertions.assertThrows(Exception.class,()->{
            customerDAO.withdraw("",null,-1);});
    }
*/
    //mockito tests
    @InjectMocks
    CustomerService mockedCustomerService = new CustomerServiceImpl();
    @Mock
    CustomerDAO customerDAOImpl = new CustomerDAOImpl();
    @BeforeEach
    void createMock() throws Exception{
        MockitoAnnotations.initMocks(this);
    }
    @Test
    final void isUsernameAvailableMock(){

        when(customerDAOImpl.isUsernameAvailable("1234")).thenReturn(false);

        boolean test = mockedCustomerService.isUsernameAvailable("1234");
        Assertions.assertEquals(false,test);
    }
    @Test
    final void depositMock() throws ServiceException {

        when(customerDAOImpl.deposit("ale",-1,null)).thenReturn(true);//anything, service has validation
        Assertions.assertThrows(ServiceException.class,()->{
            mockedCustomerService.deposit("ale",-1,null);});

    }
    @Test
    final void depositMock2() throws ServiceException {

        when(customerDAOImpl.deposit("ale",10,null)).thenReturn(true);
        when(customerDAOImpl.checkAccStatus(null,"ale")).thenReturn(true);//anything, service has validation
        Assertions.assertEquals(true,
            mockedCustomerService.deposit("ale",10,null));

    }
    @Test
    final void depositMock3() throws ServiceException {

        when(customerDAOImpl.deposit("ale",10,null)).thenReturn(true);
        when(customerDAOImpl.checkAccStatus(null,"ale")).thenReturn(false);//anything, service has validation
        Assertions.assertThrows(Exception.class,()->{
            mockedCustomerService.deposit("ale",10,null);});


    }
    @Test
    final void withdrawMock() throws ServiceException {

        when(customerDAOImpl.deposit("ale",-1,null)).thenReturn(true);
        Assertions.assertThrows(Exception.class,()->{
            mockedCustomerService.deposit("ale",-1,null);});

    }
    @Test
    final void withdrawMock2() throws ServiceException {

        when(customerDAOImpl.deposit("ale",10,null)).thenReturn(true);
        when(customerDAOImpl.checkAccStatus(null,"ale")).thenReturn(false);
        Assertions.assertThrows(Exception.class,()->{
            mockedCustomerService.deposit("ale",10,null);});

    }
    @Test
    final void acceptTransfersMock() throws ServiceException {

        when(customerDAOImpl.checkAccStatus(null,"ale")).thenReturn(false);
        Assertions.assertThrows(Exception.class,()->{
            mockedCustomerService.acceptTransfer(null,"ale","");});

    }
    @Test
    final void postTransfersMock() throws ServiceException {

        when(customerDAOImpl.checkAccStatus(null,"ale")).thenReturn(false);
        Assertions.assertThrows(Exception.class,()->{
            mockedCustomerService.postTransfer(null,null,0,"ale");});

    }
    @Test
    final void postTransfersMock2() throws ServiceException {

        when(customerDAOImpl.checkAccStatus(null,"ale")).thenReturn(true);
        when(customerDAOImpl.viewAccBalance("ale",null)).thenReturn(-1d);
        Assertions.assertThrows(Exception.class,()->{
            mockedCustomerService.postTransfer(null,null,0,"ale");});

    }
    @Test
    final void postTransfersMock3() throws ServiceException {

        when(customerDAOImpl.checkAccStatus(null,"ale")).thenReturn(true);
        when(customerDAOImpl.viewAccBalance("ale",null)).thenReturn(11d);
        Assertions.assertThrows(Exception.class,()->{
            mockedCustomerService.postTransfer(null,null,-10,"ale");});

    }

}
