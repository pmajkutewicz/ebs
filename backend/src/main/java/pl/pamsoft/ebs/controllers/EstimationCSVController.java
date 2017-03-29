package pl.pamsoft.ebs.controllers;

import static java.lang.String.valueOf;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.opencsv.CSVWriter;

import pl.pamsoft.ebs.dto.PersonStats;
import pl.pamsoft.ebs.error.BadRequestException;
import pl.pamsoft.ebs.model.Estimation;
import pl.pamsoft.ebs.model.Views;
import pl.pamsoft.ebs.service.EstimationServices;

@RestController
@RequestMapping("/estimation")
public class EstimationCSVController {

	private static final int HISTOGRAM_LIMIT = 20;
	private EstimationServices estimationServices;

	@JsonView(Views.PersonStats.class)
	@RequestMapping(value = "stats/histogram/{personId}", method = RequestMethod.GET, produces = "text/csv")
	public void histogram(@PathVariable(value = "personId") Long personId,
											HttpServletResponse response) throws BadRequestException, IOException {
		Optional<PersonStats> first =
			estimationServices.getStats().stream().filter(p -> p.getPerson().getId().equals(personId)).findFirst();
		toCsv(first.get(), response);
	}

	@Autowired
	public void setEstimationServices(EstimationServices estimationServices) {
		this.estimationServices = estimationServices;
	}

	private void toCsv(PersonStats stats, HttpServletResponse response) throws IOException {
		OutputStreamWriter osw = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
		CSVWriter csvWriter = new CSVWriter(osw, ',');
		csvWriter.writeNext(new String[]{"Index","Estimated","Actual"});
		AtomicInteger index = new AtomicInteger(1);
		stats.getEstimations().stream().limit(HISTOGRAM_LIMIT).forEach(e -> {
			String[] l = {valueOf(index.getAndIncrement()), valueOf(e.getEstimatedTime()), valueOf(e.getActualTime())};
			csvWriter.writeNext(l, false);
		});
		csvWriter.flush();
		csvWriter.close();
	}
}
