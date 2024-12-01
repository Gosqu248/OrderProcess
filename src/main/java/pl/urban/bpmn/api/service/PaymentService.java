package pl.urban.bpmn.api.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {


    public boolean checkBlik(String code) {
        return code.length() == 6;
    }

    public boolean checkCard(String cardNumber, String name, String date, String cvv) {

        if (cardNumber.length() == 16 && !name.isEmpty() && date.length() == 5 && cvv.length() == 3) {
            return true;
        } else if (cardNumber.length() != 16) {
            System.out.println("Card number must have 16 digits");
            return false;
        } else if (name.isEmpty()) {
            System.out.println("Name cannot be empty");
            return false;
        } else if (date.length() == 5) {
            System.out.println("Date must have format MM/YY");
            return false;
        } else  {
            System.out.println("CVV must have 3 digits");
            return false;
        }

    }
}
