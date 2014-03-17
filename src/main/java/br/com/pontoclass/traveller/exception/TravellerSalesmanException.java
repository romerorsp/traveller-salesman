package br.com.pontoclass.traveller.exception;

public class TravellerSalesmanException extends Exception {

	private static final long serialVersionUID = -2412016617555970844L;

	public TravellerSalesmanException() {
		super();
	}
	
	public TravellerSalesmanException(String message) {
		super(message);
	}

	public TravellerSalesmanException(Throwable cause) {
		super(cause);
	}

	public TravellerSalesmanException(String message, Throwable cause) {
		super(message, cause);
	}
}