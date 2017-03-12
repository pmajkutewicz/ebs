package pl.pamsoft.ebs.error;

public class BadRequestException extends Exception {
	private static final long serialVersionUID = 1L;
	private String errorMessage;

	public BadRequestException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public BadRequestException() {
		super();
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
