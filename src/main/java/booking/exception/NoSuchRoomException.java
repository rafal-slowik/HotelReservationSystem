/**
 * 
 */
package booking.exception;

/**
 * Throws when there is not such room in the hotel.
 * 
 * @author Rafal Slowik
 * @date 11 Mar 2017
 *
 */
public class NoSuchRoomException extends BookingException {

	private static final long serialVersionUID = 1902049032263959037L;

	public NoSuchRoomException(String message) {
		super(message);
	}

}
