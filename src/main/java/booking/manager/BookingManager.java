/**
 * 
 */
package booking.manager;

import java.time.LocalDate;

import booking.exception.NoSuchRoomException;
import booking.exception.RoomAlreadyBookedException;

/**
 * @author Rafal Slowik
 * @date 11 Mar 2017
 *
 */
public interface BookingManager {

	/**
	 * @param room
	 *            - room number. <b>Must not be null</b>
	 * @param date
	 *            - date of availability to be checked. <b>Must not be null</b>
	 * @return true if there is no booking for the given room on the date,
	 *         otherwise false.
	 * @throws NullPointerException
	 *             if any of the input parameters equal {@code null}
	 * @throws NoSuchRoomException
	 *             if the given room number doesn't exist
	 */
	public boolean isRoomAvailable(Integer room, LocalDate date) throws NoSuchRoomException;

	/**
	 * Add a booking for the given guest in the given room on the given name. If
	 * the room is not available, throw a suitable exception.
	 * 
	 * @param guest
	 *            - guest surname
	 * @param room
	 *            - room number
	 * @param date
	 *            - booking date
	 *            
	 * @throws NullPointerException
	 *             if any of the input parameters equal {@code null}
	 * @throws NoSuchRoomException
	 *             if the given room number doesn't exist
	 * @throws RoomAlreadyBookedException
	 *             when the given room number is already booked on given date
	 */
	public void addBooking(String guest, Integer room, LocalDate date)
			throws NoSuchRoomException, RoomAlreadyBookedException;

	/**
	 * 
	 * @param date
	 *            - availability date
	 * @return a list of all the available room numbers for the given date.
	 * 
	 * @throws NullPointerException
	 *             if the {@code date} parameters equals {@code null}
	 */
	public Iterable<Integer> getAvailableRooms(LocalDate date);
}
