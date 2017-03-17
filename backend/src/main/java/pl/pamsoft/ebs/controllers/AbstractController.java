package pl.pamsoft.ebs.controllers;

import java.util.Collection;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.pamsoft.ebs.error.BadRequestException;
import pl.pamsoft.ebs.model.AbstractEntity;

public abstract class AbstractController<T extends AbstractEntity> {

	@Autowired
	JpaRepository<T, Long> repository;

	@RequestMapping(method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Collection<T> listAll() {
		return repository.findAll();
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void delete(@PathVariable(value = "id") Long id) {
		repository.delete(id);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void save(@Valid @RequestBody T entity) throws BadRequestException {
		if (null != entity.getId()) {
			throw new BadRequestException("Id must be null");
		}
		repository.save(entity);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public void update(@Valid @RequestBody T entity) throws BadRequestException {
		throwExceptionWhenIdIsNull(entity);
		repository.save(entity);
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
