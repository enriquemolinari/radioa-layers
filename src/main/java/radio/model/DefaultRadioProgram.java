package radio.model;

import java.util.List;
import java.util.stream.Collectors;

import radio.model.api.Competitor;
import radio.model.api.RadioCompetition;
import radio.model.api.RadioException;
import radio.model.api.RadioProgram;
import radio.persistence.api.Competition;
import radio.persistence.api.CompetitionRepository;

public class DefaultRadioProgram implements RadioProgram {

	private CompetitionRepository repository;

	public DefaultRadioProgram(CompetitionRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public Iterable<RadioCompetition> availableCompetitions() {
		List<Competition> cs = repository.competitionsForInscription();

		return cs.stream().map(d -> new RadioCompetition() {
			@Override
			public int id() {
				return d.id();
			}

			@Override
			public String description() {
				return d.description();
			}
		}).collect(Collectors.toList());
	}

	@Override
	public void addInscription(int idCompetition, Competitor competitor) {
		repository.competitionBy(idCompetition)
				.orElseThrow(() -> new RadioException(
						"Selected competition does not exists..."));

		Competitor c = new DefaultCompetitor(competitor.id(),
				competitor.name(), competitor.lastName(),
				competitor.email(), competitor.phone());

		repository.addInscription(c.name(), c.lastName(), c.id(),
				c.phone(), c.email(), idCompetition);

	}
}
