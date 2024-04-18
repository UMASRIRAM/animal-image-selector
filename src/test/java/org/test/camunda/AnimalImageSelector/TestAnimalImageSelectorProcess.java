//package org.test.camunda.AnimalImageSelector;
//
//
//import io.camunda.zeebe.client.ZeebeClient;
//import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
//import io.camunda.zeebe.spring.test.ZeebeSpringTest;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.test.camunda.AnimalImageSelector.repository.AnimalImageRepository;
//
//import java.util.Map;
//
//import static io.camunda.zeebe.process.test.assertions.BpmnAssert.assertThat;
//import static io.camunda.zeebe.spring.test.ZeebeTestThreadSupport.waitForProcessInstanceCompleted;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//
//@ZeebeSpringTest
//@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
//public class TestAnimalImageSelectorProcess {
//
//    @Autowired
//    private ZeebeClient client;
//
//    @MockBean
//    private AnimalImageRepository animalImageRepository;
//
//    @Test
//    public void testSaveBearImage() {
//        Map<String, Object> processVariableMap = Map.of("category", "bear");
//
//        ProcessInstanceEvent processInstance = client.newCreateInstanceCommand()
//                .bpmnProcessId("animal_image_viewer")
//                .latestVersion()
//                .variables(processVariableMap)
//                .send()
//                .join();
//
//        // Now the process should run to the end
//        waitForProcessInstanceCompleted(processInstance);
//
//        assertThat(processInstance)
//                .hasPassedElement("Event_177sv80")
//                .isCompleted();
//
//        verify(animalImageRepository).save(any());
//    }
//
//}
//
//
//
