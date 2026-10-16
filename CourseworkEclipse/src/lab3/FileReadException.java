package lab3;

public class FileReadException extends Exception {

	public FileReadException(String message) {
		super(message);
	}

	public FileReadException(Exception ex) {
		super(ex);
	}

}
