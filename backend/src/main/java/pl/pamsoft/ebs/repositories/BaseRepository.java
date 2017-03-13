package pl.pamsoft.ebs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import pl.pamsoft.ebs.model.AbstractEntity;

@NoRepositoryBean
public interface BaseRepository<T extends AbstractEntity> extends JpaRepository<T, Long> {
}
