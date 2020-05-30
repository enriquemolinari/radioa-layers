package ar.cpfw.book.radio.persistence;

import java.time.LocalDateTime;

public interface Competition {

	int id();

	String description();

	String rules();

	LocalDateTime startDate();

	LocalDateTime inscriptionStartDate();

	LocalDateTime inscriptionEndDate();

}
