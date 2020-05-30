package ar.cpfw.book.radio.model;

class NotNullOrNorEmpty<T> {

	private T value;
	
	public NotNullOrNorEmpty(T value, String description) {
		if (value == null || "".equals(value)) {
			throw new RadioException(description + " must be valid...");
		}
		this.value = value;
	}
	
	public T value() {
		return value;
	}
}
