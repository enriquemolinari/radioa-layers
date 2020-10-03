package radio.model.api;

public interface RadioProgram {

	Iterable<RadioCompetition> availableCompetitions();

	void addInscription(int idCompetition, Competitor c);
}
