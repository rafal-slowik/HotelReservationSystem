package booking.manager;

import static booking.properties.ConfigProperties.getConfigInstance;
import static booking.properties.ExceptionMessagesKeys.*;
import static booking.repository.NonDbRepository.getRepositoryInstance;

import java.time.LocalDate;

import com.google.common.base.Preconditions;

import booking.exception.NoSuchRoomException;
import booking.exception.RoomAlreadyBookedException;
import booking.repository.Repository;

/**
 * @author Rafal Slowik
 * @date 11 Mar 2017
 *
 */
public class BookingManagerImpl implements BookingManager {

	private static Object lock = new Object();
	private Repository repository;

	public BookingManagerImpl() {
		repository = getRepositoryInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see booking.manager.BookingManager#isRoomAvailable(java.lang.Integer,
	 * java.time.LocalDate)
	 */
	@Override
	public boolean isRoomAvailable(Integer room, LocalDate date) throws NoSuchRoomException {
		Preconditions.checkNotNull(room, getConfigInstance().formatProperty(EXC_MSG_PARAM_MUST_NOT_BE_NULL.getPropertyKey(), "Given room number"));
		Preconditions.checkNotNull(date, getConfigInstance().formatProperty(EXC_MSG_PARAM_MUST_NOT_BE_NULL.getPropertyKey(), "Given date"));
		return repository.isRoomAvailable(room, date);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see booking.manager.BookingManager#addBooking(java.lang.String,
	 * java.lang.Integer, java.time.LocalDate)
	 */
	@Override
	public synchronized void addBooking(String guest, Integer room, LocalDate date)
			throws NoSuchRoomException, RoomAlreadyBookedException {
		Preconditions.checkNotNull(guest,
				getConfigInstance().formatProperty(EXC_MSG_PARAM_MUST_NOT_BE_NULL.getPropertyKey(), "Guest name"));
		synchronized (lock) {
			if (!isRoomAvailable(room, date)) {
				throw new RoomAlreadyBookedException(
						getConfigInstance().formatProperty(EXC_MSG_ROOM_ALREADY_BOOKED.getPropertyKey(), room, date));
			}
			repository.addBooking(guest, room, date);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * booking.manager.BookingManager#getAvailableRooms(java.time.LocalDate)
	 */
	@Override
	public Iterable<Integer> getAvailableRooms(LocalDate date) {
		Preconditions.checkNotNull(date, getConfigInstance().formatProperty(EXC_MSG_PARAM_MUST_NOT_BE_NULL.getPropertyKey(), "Given date"));
		return repository.getAvailableRooms(date);
	}
}
