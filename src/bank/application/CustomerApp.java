package bank.application;

import bank.db.CustomerDB;
import bank.entity.Customer;

import java.util.HashMap;

public class CustomerApp {
    private static HashMap<String, Customer> customers = new HashMap<>();

    public static Customer registerCustomer(String customerID, String password, String customerName) {
        Customer newCustomer = new Customer(customerID, password, customerName);
        customers.put(customerID, newCustomer);
        CustomerDB.addCustomer(newCustomer);
        return newCustomer;
    }

    public static Customer getCustomerOrNull(String customerID) {
        if (customers.containsKey(customerID)) {
            return customers.get(customerID);
        } else {
            return null;
        }
    }

    public static boolean loginCustomer(Customer customer, String password) {
        if (customer.getPassword().equals(password)) {
            return true;
        }

        return false; //비밀번호 맞으면 로그인성공 아니면 로그인 실패
    }

    public static boolean checkDuplicateID(String customerID) { //아이디 중복체크, 중복아이디 있으면 실패 없으면 성공
        if (customers.containsKey(customerID)) {
            return false;
        } else {
            return true;
        }
    }
}
