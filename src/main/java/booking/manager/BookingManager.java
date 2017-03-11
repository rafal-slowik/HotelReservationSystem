/**
 * 
 */
package booking.manager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import booking.entity.Booking;

/**
 * @author Rafal Slowik
 * @date 11 Mar 2017
 *
 */
public interface BookingManager {

	/**
	 * @param room
	 *            - room number
	 * @param date
	 *            - date of availability to be checked
	 * @return true if there is no booking for the given room on the date,
	 *         otherwise false.
	 */
	public boolean isRoomAvailable(Integer room, LocalDate date);

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
	 */
	public void addBooking(String guest, Integer room, LocalDate date);

	/**
	 * 
	 * @param date
	 *            - availability date
	 * @return a list of all the available room numbers for the given date.
	 */
	public Iterable<Integer> getAvailableRooms(LocalDate date);

	/**
	 *  
	 * @param date
	 * @param room
	 * @param bookings 
	 * @return
	 */
	default boolean isRoomAvailableOnDate(LocalDate date, Integer room, Set<Booking> bookings) {
		if (bookings == null) {

		}
		return bookings.stream().filter(booking -> date.equals(booking.getBookingDate()))
				.collect(Collectors.toCollection(ArrayList::new)).size() > 0;
	}
}
