package pl.urban.bpmn;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class OrderProcessController {

    private static final String BPMN_PROCESS_ID = "order-process";


    @Qualifier("zeebeClientLifecycle")
    private ZeebeClient client;

    @PostMapping("/start")
    public Map<String, Object> startProcessInstance(@RequestBody Map<String, Object> variables) {

        var event = client
                .newCreateInstanceCommand()
                .bpmnProcessId(BPMN_PROCESS_ID)
                .latestVersion()
                .variables(variables)
                .send();



        variables.put("processInstanceKey", event.join().getProcessInstanceKey());
        return variables;
    }


}
