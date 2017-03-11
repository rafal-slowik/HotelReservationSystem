/**
 * 
 */
package booking.manager;

import static booking.properties.ConfigProperties.getConfigInstance;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import booking.exception.NoSuchRoomException;
import booking.exception.RoomAlreadyBookedException;
import booking.repository.Repository;

/**
 * @author Rafal Slowik
 * @date 11 Mar 2017
 *
 */
public class BookingManagerTest {

	private BookingManager manager = null;
	private Set<Integer> rooms = new TreeSet<>();

	@Before
	public void init() {
		manager = new BookingManagerImpl();
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
				Assert.fail("The room doesn't exist and ''NoSuchRoomException'' should be thrown!");
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
		for (Integer room : rooms) {
			LocalDate date = LocalDate.now();
			try {
				Assert.assertEquals(true, manager.isRoomAvailable(room, date));
				manager.addBooking("Guest", room, date);
			} catch (Throwable e) {
				Assert.fail("No exception should be thrown!");
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
			manager.addBooking(null, 1, LocalDate.now());
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
	public void moreManagersSingletonRepositoryTest() {
		Repository rep1 = getRepository(manager);
		Repository rep2 = getRepository(new BookingManagerImpl());
		Repository rep3 = getRepository(new BookingManagerImpl());
		Assert.assertTrue("Repository singleton does not work properly!", rep1 == rep2 && rep1 == rep3 && rep2 == rep3);
	}

	private Repository getRepository(BookingManager manager) {
		Repository rep = null;
		try {
			Field field = manager.getClass().getDeclaredField("repository");
			field.setAccessible(true);
			rep = (Repository) field.get(manager);
		} catch (Throwable e) {
			Assert.fail("Unexpected exception was thrown!");
		}
		return rep;
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
