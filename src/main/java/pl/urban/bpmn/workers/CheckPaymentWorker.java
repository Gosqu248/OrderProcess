package pl.urban.bpmn.workers;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.urban.bpmn.api.service.PaymentService;

import java.util.Map;

@Component
@AllArgsConstructor
public class CheckPaymentWorker {

    private final PaymentService paymentService;

    @JobWorker(type = "goToPaymentGateway")
    public Map<String, Object> goToPaymentGateway(final ActivatedJob job) {
        System.out.println("Going to payment service");
        return null;
    }

    @JobWorker(type = "checkPayment")
    public Map<String, Object> loadRestaurantMenu(final ActivatedJob job) {
        System.out.println("Checking payment");

        var jobResultVariables = job.getVariablesAsMap();
        String payment = (String) jobResultVariables.get("payment");


        if (payment.equals("BLIK")) {
            String blikCode = (String) jobResultVariables.get("blikCode");

            boolean isPaymentSuccessful = paymentService.checkBlik(blikCode);

            jobResultVariables.put("isPaymentSuccessful", isPaymentSuccessful);

            if (isPaymentSuccessful) {
                System.out.println("Payment successful");
            } else {
                System.out.println("Payment failed");
            }

        } else if (payment.equals("card")) {
            String cardNumber = (String) jobResultVariables.get("cardNumber");
            String name = (String) jobResultVariables.get("name");
            String expirationDate = (String) jobResultVariables.get("expirationDate");
            String cvv = (String) jobResultVariables.get("cvv");

            boolean isPaymentSuccessful = paymentService.checkCard(cardNumber, name, expirationDate, cvv);

            jobResultVariables.put("isPaymentSuccessful", isPaymentSuccessful);

            if (isPaymentSuccessful) {
                System.out.println("Payment successful");
            } else {
                System.out.println("Payment failed");
            }
        }

        return jobResultVariables;

    }

}
