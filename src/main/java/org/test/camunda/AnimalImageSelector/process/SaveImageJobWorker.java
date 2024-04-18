package org.test.camunda.AnimalImageSelector.process;

import org.test.camunda.AnimalImageSelector.model.AnimalImage;
import org.test.camunda.AnimalImageSelector.repository.AnimalImageRepository;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class SaveImageJobWorker {

    private static final Logger LOG = LoggerFactory.getLogger(SaveImageJobWorker.class);

    private final AnimalImageRepository animalImageRepository;
    private final RestTemplate restTemplate;

    public SaveImageJobWorker(AnimalImageRepository animalImageRepository, RestTemplate restTemplate) {
        this.animalImageRepository = animalImageRepository;
        this.restTemplate = restTemplate;
    }

    @JobWorker(type = "saveimage")
    public void saveImage(final JobClient client, final ActivatedJob job) throws IOException {

        final String url = (String) job.getVariablesAsMap().get("apiurl");
        final String category = (String) job.getVariablesAsMap().get("category");

        LOG.info("category: {}", category);

        var instanceKey = job.getProcessInstanceKey();
        var image = restTemplate.getForObject(url, BufferedImage.class);
        LOG.info("Completed fetch Image from API ='{}'", url);

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();

            animalImageRepository.save(new AnimalImage(instanceKey, category, imageInByte));
            LOG.info("Completed Save Image to Repository");

        } catch (IOException e) {
            LOG.error("Error occurred while processing request", e);
            throw e;
        }

    }


}