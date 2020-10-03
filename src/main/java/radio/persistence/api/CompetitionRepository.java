package radio.persistence.api;

import java.util.List;
import java.util.Optional;

public interface CompetitionRepository {

	Optional<Competition> competitionBy(int id);
	
	void addInscription(String name, String lastName, String id,
			String phone, String email, int idCompetition)
			throws PersistenceException;

	List<Competition> competitionsForInscription()
			throws PersistenceException;

//	static CompetitionRepository fabrica(String a, String b, String c, String d, String e) {
//	 return new JdbcCompetitionRepository("", "", "", "", "");
//	}
}
