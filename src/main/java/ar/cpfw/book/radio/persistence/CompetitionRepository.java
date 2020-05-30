package ar.cpfw.book.radio.persistence;

import java.sql.SQLException;
import java.util.List;

public interface CompetitionRepository {

	void addInscription(String name, String lastName, String id,
			String phone, String email, int idCompetition)
			throws PersistenceException;

	List<Competition> competitionsForInscription()
			throws PersistenceException;
}
