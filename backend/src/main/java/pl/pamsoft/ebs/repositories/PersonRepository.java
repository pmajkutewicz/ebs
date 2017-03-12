package pl.pamsoft.ebs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pamsoft.ebs.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
