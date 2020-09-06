package radio.persistence;

public class PersistenceException extends RuntimeException {

	public PersistenceException(Exception e) {
		super(e);
	}
}
