package org.test.camunda.AnimalImageSelector.rest;

import org.test.camunda.AnimalImageSelector.model.AnimalImage;
import org.test.camunda.AnimalImageSelector.repository.AnimalImageRepository;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.Map;

@RestController
public class RetrieveImageRestController {

    private final ZeebeClient client;
    private final AnimalImageRepository animalImageRepository;
    private final static Logger LOG = LoggerFactory.getLogger(RetrieveImageRestController.class);

    public RetrieveImageRestController(AnimalImageRepository animalImageRepository, ZeebeClient client) {
        this.client = client;
        this.animalImageRepository = animalImageRepository;
    }


    @PostMapping("/category/{id}")
    public byte[] RetrieveImage(@PathVariable String id) {

        try {
            client.newDeployResourceCommand().addResourceFromClasspath("bpmn/retrieve-image.bpmn").send().join();

            ProcessInstanceResult processInstanceResult = client
                    .newCreateInstanceCommand()
                    .bpmnProcessId("animal_image_viewer")
                    .latestVersion()
                    .variables(Map.of("category", id))
                    .withResult() // to await the completion of process execution and return result
                    .send()
                    .join();

            LOG.info(
                    "Started instance for processDefinitionKey='{}', bpmnProcessId='{}', version='{}' with processInstanceKey='{}'",
                    processInstanceResult.getProcessDefinitionKey(), processInstanceResult.getBpmnProcessId(), processInstanceResult.getVersion(),
                    processInstanceResult.getProcessInstanceKey());

            AnimalImage obj = animalImageRepository.findByProcessId(processInstanceResult.getProcessInstanceKey());
            return Base64.getEncoder().encode(obj.getCategoryPhoto());

        } catch (Exception e) {
            LOG.debug("Exception " + e.getMessage());
            return null;
        }
    }

}
