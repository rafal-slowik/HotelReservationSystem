/**
 * 
 */
package booking.exception;

/**
 * Base exception of the application.
 * 
 * @author Rafal Slowik
 * @date   11 Mar 2017
 *
 */
public abstract class BookingException extends Exception {

	public BookingException(String message) {
		super(message);
	}
	
	private static final long serialVersionUID = 2942448043417077041L;

}
