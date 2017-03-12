/**
 * 
 */
package booking.manager;

import static booking.properties.ConfigProperties.getConfigInstance;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.StreamSupport;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import booking.exception.NoSuchRoomException;
import booking.exception.RoomAlreadyBookedException;

/**
 * @author Rafal Slowik
 * @date 11 Mar 2017
 *
 */
public class BookingManagerTest {

	private BookingManager manager = new BookingManagerImpl();
	private Set<Integer> rooms = new TreeSet<>();

	@Before
	public void init() {
		String roomsToParse = getConfigInstance().findByKey("rooms");
		Arrays.stream(roomsToParse.split(",")).map(String::trim).mapToInt(Integer::parseInt)
				.forEach(room -> rooms.add(room));
	}

	@Test
	public void isRoomAvailable1Test() {
		for (int i = 0; i < 1000; i++) {
			try {
				int notExistingRoom = generateNotExsistingRoomNumber();
				manager.isRoomAvailable(notExistingRoom, LocalDate.now());
				Assert.fail("The room doesn't, so the ''NoSuchRoomException'' should be thrown!");
			} catch (NoSuchRoomException e) {
				// OK
			}
		}
	}

	@Test
	public void isRoomAvailable2Test() {
		try {
			manager.isRoomAvailable(null, LocalDate.now());
			Assert.fail("The room number is null and NullPointerException should be thrown");
		} catch (Throwable e) {
			if (e instanceof NullPointerException == false) {
				Assert.fail("The room number is null and NullPointerException should be thrown");
			}
		}
	}

	@Test
	public void isRoomAvailable3Test() {
		try {
			manager.isRoomAvailable(1, null);
			Assert.fail("The room number is null and NullPointerException should be thrown");
		} catch (Throwable e) {
			if (e instanceof NullPointerException == false) {
				Assert.fail("The room number is null and NullPointerException should be thrown");
			}
		}
	}

	@Test
	public void isRoomAvailable4Test() {
		try {
			manager.isRoomAvailable(null, null);
			Assert.fail("The room number is null and NullPointerException should be thrown");
		} catch (Throwable e) {
			if (e instanceof NullPointerException == false) {
				Assert.fail("The room number is null and NullPointerException should be thrown");
			}
		}
	}

	@Test
	public void addBooking1Test() {
		Assert.assertTrue("The number of rooms is insufficient and have to be at least 1", rooms.size() > 0);
		for (Integer room : rooms) {
			LocalDate date = LocalDate.now().plusDays(1);
			try {
				Assert.assertEquals(true, manager.isRoomAvailable(room, date));
				manager.addBooking("Guest", room, date);
			} catch (Throwable e) {
				Assert.fail("No exception should be thrown! Following exception was thrown: " + e);
			}

			try {
				Assert.assertEquals(false, manager.isRoomAvailable(room, date));
			} catch (Throwable e) {
				Assert.fail("No exception should be thrown!");
			}

			try {
				manager.addBooking("Guest", room, date);
				Assert.fail("RoomAlreadyBookedException should be thrown!");
			} catch (Throwable e) {

				if (e instanceof RoomAlreadyBookedException == false) {
					Assert.fail("RoomAlreadyBookedException should be thrown!");
				}
			}
		}
	}

	@Test
	public void addBooking2Test() {

		try {
			manager.addBooking(null, 1, LocalDate.now().plusDays(2));
		} catch (Throwable e) {
			if (e instanceof NullPointerException == false) {
				Assert.fail("The guest name is null and NullPointerException should be thrown");
			}
		}

		try {
			manager.addBooking("Guest", null, LocalDate.now());
		} catch (Throwable e) {
			if (e instanceof NullPointerException == false) {
				Assert.fail("The room number is null and NullPointerException should be thrown");
			}
		}

		try {
			manager.addBooking("Guest", 1, null);
		} catch (Throwable e) {
			if (e instanceof NullPointerException == false) {
				Assert.fail("The date is null and NullPointerException should be thrown");
			}
		}
	}
	
	@Test
	public void addBooking2MagangersTest() {
		Assert.assertTrue("The number of rooms is insufficient and have to be at least 1", rooms.size() > 0);
		Integer room = rooms.iterator().next();
		BookingManager manager2 = new BookingManagerImpl();
		LocalDate date = LocalDate.now().plusDays(2);
		try {
			manager.isRoomAvailable(room, date);
			manager2.addBooking("Guest", room, date);
			manager.addBooking("Guest", room, date);
		} catch (Throwable e) {
			if (e instanceof RoomAlreadyBookedException == false) {
				Assert.fail("The guest name is null and NullPointerException should be thrown");
			}
		}
	}
	
	@Test
	public void getAvailableRooms1Test() {
		Assert.assertTrue("The number of rooms is insufficient and have to be at least 1", rooms.size() > 0);
		LocalDate date = LocalDate.now();
		checkAvailableRooms(0, date);
		int[] idx = {0};
		rooms.stream().forEach(room -> {
			idx[0]++;
			try {
				manager.addBooking("Guest", room, date);
				checkAvailableRooms(idx[0], date);
			} catch (Throwable e) {
				Assert.fail("There was no exception expected!");
			}
		});
	}

	private void checkAvailableRooms(int reduceBy, LocalDate date) {
		Iterable<Integer> available = manager.getAvailableRooms(date);

		Assert.assertEquals("The method returned the wrong number of available rooms", rooms.size() - reduceBy,
				StreamSupport.stream(available.spliterator(), true).count());
		StreamSupport.stream(available.spliterator(), true).forEach(room -> {
			if (!rooms.contains(room)) {
				Assert.fail("The set of available rooms should contain the room number=" + room);
			}
		});
	}
	
	private Integer generateNotExsistingRoomNumber() {
		Random random = new Random();
		int room = 0;
		do {
			room = random.nextInt(Integer.MAX_VALUE);
			if (!rooms.contains(room)) {
				break;
			}
		} while (true);
		return room;
	}
}
