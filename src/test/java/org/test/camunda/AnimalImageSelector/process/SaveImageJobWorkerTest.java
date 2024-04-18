package org.test.camunda.AnimalImageSelector.process;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import org.test.camunda.AnimalImageSelector.repository.AnimalImageRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaveImageJobWorkerTest {

    @Mock
    private AnimalImageRepository animalImageRepository;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private SaveImageJobWorker saveImageJobWorker;

    @Test
    public void testSaveImageWithAllValidInputs() throws IOException {

        var activatedJobMock = mock(ActivatedJob.class);
        var client = mock(JobClient.class);

        Map<String, Object> variablesMap = new HashMap<>();
        variablesMap.put("apiurl", "http://www.dogimage.com");
        variablesMap.put("category", "test_category");

        File file = new File("./src/test/resources/dog.jpeg");
        BufferedImage image = ImageIO.read(file);

        when(activatedJobMock.getVariablesAsMap()).thenReturn(variablesMap);
        when(restTemplate.getForObject(anyString(), any())).thenReturn(image);
        saveImageJobWorker.saveImage(client, activatedJobMock);

        verify(animalImageRepository).save(any());
    }
}
