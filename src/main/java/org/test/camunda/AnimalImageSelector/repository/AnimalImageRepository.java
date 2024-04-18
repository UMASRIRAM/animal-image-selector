package org.test.camunda.AnimalImageSelector.repository;

import org.test.camunda.AnimalImageSelector.model.AnimalImage;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AnimalImageRepository extends JpaRepository<AnimalImage, Long> {

    AnimalImage findByProcessId(long processId);
}


