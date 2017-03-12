/**
 * 
 */
package booking.repository;

import static booking.properties.ConfigProperties.getConfigInstance;
import static booking.properties.ExceptionMessagesKeys.EXC_MSG_ROOM_DOES_NOT_EXIST;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import booking.entity.Booking;
import booking.exception.NoSuchRoomException;

/**
 * @author Rafal Slowik
 * @date 11 Mar 2017
 *
 */
public class NonDbRepository implements Repository {

	private static Map<Integer, Set<Booking>> bookingMap = init();

	private static Map<Integer, Set<Booking>> init() {
		Map<Integer, Set<Booking>> map = new ConcurrentHashMap<>();
		String roomsToParse = getConfigInstance().findByKey("rooms");
		Arrays.stream(roomsToParse.split(",")).map(String::trim).mapToInt(Integer::parseInt)
				.forEach(room -> map.put(room, new HashSet<>()));
		return map;
	}

	/**
	 * @param room
	 * @return all bookings for the given room.
	 */
	private Set<Booking> getRoomBookings(Integer room) throws NoSuchRoomException {
		if (bookingMap.containsKey(room)) {
			return bookingMap.get(room);
		}
		throw new NoSuchRoomException(
				getConfigInstance().formatProperty(EXC_MSG_ROOM_DOES_NOT_EXIST.getPropertyKey(), room));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see booking.repository.Repository#isAvailableRoom(java.lang.Integer,
	 * java.time.LocalDate)
	 */
	@Override
	public boolean isRoomAvailable(Integer room, LocalDate date) throws NoSuchRoomException {
		Set<Booking> bookings = getRoomBookings(room);
		return bookings.stream().filter(b -> date.equals(b.getBookingDate())).count() == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see booking.repository.Repository#getAvailableRooms(java.time.LocalDate)
	 */
	@Override
	public Set<Integer> getAvailableRooms(LocalDate date) {
		return bookingMap.keySet().stream()
				.filter(key -> bookingMap.get(key).stream().filter(b -> date.equals(b.getBookingDate())).count() == 0)
				.collect(Collectors.toCollection(HashSet::new));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see booking.repository.Repository#addBooking(java.lang.String,
	 * java.lang.Integer, java.time.LocalDate)
	 */
	@Override
	public void addBooking(String guest, Integer room, LocalDate date) {
		bookingMap.get(room).add(new Booking(guest, room, date));
	}
}
