package ru.roshchin.springcourse.project3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.roshchin.springcourse.project3.models.Measurement;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

    int countByIsRainy(boolean rainy);



}
