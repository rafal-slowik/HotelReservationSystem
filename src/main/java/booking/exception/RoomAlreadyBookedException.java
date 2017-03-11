/**
 * 
 */
package booking.exception;

/**
 * @author Rafal Slowik
 * @date   11 Mar 2017
 *
 */
public class RoomAlreadyBookedException extends BookingException {

	private static final long serialVersionUID = -4692956276401436200L;

	public RoomAlreadyBookedException(String message) {
		super(message);
	}
}
