package ar.cpfw.book.radio.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcCompetitorRepository implements CompetitionRepository {

	private static final int INITIAL_POINTS = 0;

	@Override
	public void addInscription(String name, String lastName, String id,
			String phone, String email, int idCompetition)
			throws PersistenceException {

		Connection c = connection();
		try {
			c.setAutoCommit(false);
			PreparedStatement st = c.prepareStatement(
					"insert into competitor(first_name, last_name, person_id, "
							+ "email, phone, points) "
							+ "values(?,?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, name);
			st.setString(2, lastName);
			st.setString(3, id);
			st.setString(4, email);
			st.setString(5, phone);
			st.setInt(6, INITIAL_POINTS);
			st.executeUpdate();

			ResultSet generatedKeys = st.getGeneratedKeys();
			generatedKeys.next();

			PreparedStatement st2 = c.prepareStatement(
					"insert into inscription(id_competition, id_competitor, inscription_date) "
							+ "values(?,?,?)");

			st2.setInt(1, idCompetition);
			st2.setInt(2, generatedKeys.getInt(1));
			st2.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
			st2.executeUpdate();

			c.commit();
		} catch (Exception e) {
			try {
				c.rollback();
				throw new PersistenceException(e);
			} catch (SQLException s) {
				throw new PersistenceException(s);
			}
		} finally {
			try {
				c.setAutoCommit(true);
			} catch (SQLException s) {
				throw new PersistenceException(s);
			}
		}

	}

	@Override
	public List<Competition> competitionsForInscription()
			throws PersistenceException {
		Connection c = connection();
		try {
			PreparedStatement st = c.prepareStatement(
					"select id, description, rules, start_date, "
							+ "inscription_start_date, inscription_end_date "
							+ "from competition "
							+ "where inscription_start_date <= ? "
							+ "and inscription_end_date >= ?");

			st.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
			st.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));

			ResultSet resultSet = st.executeQuery();

			var competitions = new ArrayList<Competition>();
			while (resultSet.next()) {

				int id = resultSet.getInt("id");
				var description = resultSet.getString("description");
				var rules = resultSet.getString("rules");
				var startDate = resultSet.getTimestamp("start_date");
				var inscriptionStartDate = resultSet
						.getTimestamp("inscription_start_date");
				var inscriptionEndDate = resultSet
						.getTimestamp("inscription_end_date");

				competitions.add(new Competition() {
					@Override
					public LocalDateTime startDate() {
						return startDate.toLocalDateTime();
					}

					@Override
					public String rules() {
						return rules;
					}

					@Override
					public LocalDateTime inscriptionStartDate() {
						return inscriptionStartDate.toLocalDateTime();
					}

					@Override
					public LocalDateTime inscriptionEndDate() {
						return inscriptionEndDate.toLocalDateTime();
					}

					@Override
					public int id() {
						return id;
					}

					@Override
					public String description() {
						return description;
					}
				});
			}
			return competitions;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}
	}

	private Connection connection() throws PersistenceException {
		String url = "jdbc:derby://localhost:1527/radiocompetition";
		String user = "app";
		String password = "app";
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

}
