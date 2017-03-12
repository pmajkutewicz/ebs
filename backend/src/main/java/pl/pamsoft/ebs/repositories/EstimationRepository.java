package pl.pamsoft.ebs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pamsoft.ebs.model.Estimation;

public interface EstimationRepository extends JpaRepository<Estimation, Long> {

}
