/**
 * 
 */
package booking.repository;

import java.time.LocalDate;
import java.util.Set;

import booking.exception.NoSuchRoomException;
import booking.exception.RoomAlreadyBookedException;

/**
 * @author Rafal Slowik
 * @date 11 Mar 2017
 *
 */
public interface Repository {

	/**
	 * Check availability of the given <code>room</code> on <code>date</code>
	 * 
	 * @param room
	 * @param date
	 * @return <code>true</code> when the given room is available on that date,
	 *         otherwise false
	 * @throws NoSuchRoomException
	 *             in case when there is not such room
	 */
	public boolean isRoomAvailable(Integer room, LocalDate date) throws NoSuchRoomException;

	/**
	 * 
	 * @param date
	 * @return all available rooms on the given date
	 */
	public Set<Integer> getAvailableRooms(LocalDate date);

	/**
	 * Perform the booking for the given <code>guest</code>, <code>room</code>
	 * and <code>date</code>.
	 * 
	 * @param guest
	 *            - the name of the guest
	 * @param room
	 *            - room number
	 * @param date
	 *            - booking date
	 */
	public void addBooking(String guest, Integer room, LocalDate date)
			throws NoSuchRoomException, RoomAlreadyBookedException;
}
