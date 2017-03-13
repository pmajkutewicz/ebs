package pl.pamsoft.ebs.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.pamsoft.ebs.error.BadRequestException;
import pl.pamsoft.ebs.model.AbstractEntity;

public abstract class AbstractController<T extends AbstractEntity> {

	@Autowired
	JpaRepository<T, Long> repository;

	@RequestMapping(method = RequestMethod.GET)
	public Collection<T> listAll() {
		return repository.findAll();
	}

	public void throwExceptionWhenIdIsNull(T entity) throws BadRequestException {
		throwExceptionWhenIdIsNull(entity.getId());
	}

	public void throwExceptionWhenIdIsNull(Long id) throws BadRequestException {
		if (null == id) {
			throw new BadRequestException("Id must not be null");
		}
	}
}
