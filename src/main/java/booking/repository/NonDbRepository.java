/**
 * 
 */
package booking.repository;

import static booking.properties.ConfigProperties.getConfigInstance;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import booking.entity.Booking;
import booking.exception.NoSuchRoomException;
import booking.exception.RoomAlreadyBookedException;

/**
 * @author Rafal Slowik
 * @date 11 Mar 2017
 *
 */
public class NonDbRepository implements Repository {

	private Map<Integer, Set<Booking>> bookingMap = new ConcurrentHashMap<>();

	private NonDbRepository() {
		init();
	}

	private void init() {
		String roomsToParse = getConfigInstance().findByKey("rooms");
		int[] availableRooms = parseStringOfInts(roomsToParse);
		for (Integer room : availableRooms) {
			bookingMap.put(room, new HashSet<>());
		}
	}

	private int[] parseStringOfInts(String line) {
		return Arrays.stream(line.split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
	}

	/**
	 * @param room
	 * @return all bookings for the given room.
	 */
	private Set<Booking> getRoomBookings(Integer room) throws NoSuchRoomException {
		if (bookingMap.containsKey(room)) {
			return bookingMap.get(room);
		}
		throw new NoSuchRoomException(getConfigInstance().formatProperty("exception.room_does_not_exist.text", room));
	}

	private static class SingletonHelper {
		private static NonDbRepository INSTANCE = new NonDbRepository();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see booking.repository.Repository#isAvailableRoom(java.lang.Integer,
	 * java.time.LocalDate)
	 */
	@Override
	public boolean isAvailableRoom(Integer room, LocalDate date) throws NoSuchRoomException {
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
				.collect(Collectors.toCollection(TreeSet::new));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see booking.repository.Repository#addBooking(java.lang.String,
	 * java.lang.Integer, java.time.LocalDate)
	 */
	@Override
	public void addBooking(String guest, Integer room, LocalDate date)
			throws NoSuchRoomException, RoomAlreadyBookedException {
		bookingMap.get(room).add(new Booking(guest, date));
	}

	public static NonDbRepository getRepositoryInstance() {
		return SingletonHelper.INSTANCE;
	}
}
