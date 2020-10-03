package radio.persistence.api;

public class PersistenceException extends RuntimeException {

	public PersistenceException(Exception e) {
		super(e);
	}
}
