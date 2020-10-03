package radio.persistence.api;

import java.time.LocalDateTime;

public interface Competition {

	int id();

	String description();

	String rules();

	LocalDateTime startDate();

	LocalDateTime inscriptionStartDate();

	LocalDateTime inscriptionEndDate();

}
