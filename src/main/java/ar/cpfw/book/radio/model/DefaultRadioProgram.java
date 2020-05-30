package ar.cpfw.book.radio.model;

import java.util.List;
import java.util.stream.Collectors;

import ar.cpfw.book.radio.persistence.Competition;
import ar.cpfw.book.radio.persistence.CompetitionRepository;

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
//		TODO: validae idCompetition
		Competitor c = new DefaultCompetitor(competitor.id(),
				competitor.name(), competitor.lastName(),
				competitor.email(), competitor.phone());

		repository.addInscription(c.name(), c.lastName(), c.id(),
				c.phone(), c.email(), idCompetition);

	}

}
