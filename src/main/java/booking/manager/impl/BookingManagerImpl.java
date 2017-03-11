package booking.manager.impl;

import static booking.repository.NonDbRepository.getRepositoryInstance;

import java.time.LocalDate;

import booking.manager.BookingManager;
import booking.repository.Repository;

/**
 * @author Rafal Slowik
 * @date 11 Mar 2017
 *
 */
public class BookingManagerImpl implements BookingManager {

	private Repository repository;

	private BookingManagerImpl() {
		repository = getRepositoryInstance();
	}

	public static BookingManager getBookingManagerInstance() {
		return SingletonHelper.INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see booking.manager.BookingManager#isRoomAvailable(java.lang.Integer,
	 * java.time.LocalDate)
	 */
	@Override
	public boolean isRoomAvailable(Integer room, LocalDate date) {
		// TODO
	//	repository.isAvailableRoom(room, date);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see booking.manager.BookingManager#addBooking(java.lang.String,
	 * java.lang.Integer, java.time.LocalDate)
	 */
	@Override
	public synchronized void addBooking(String guest, Integer room, LocalDate date) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * booking.manager.BookingManager#getAvailableRooms(java.time.LocalDate)
	 */
	@Override
	public Iterable<Integer> getAvailableRooms(LocalDate date) {
		// TODO Auto-generated method stub
		return null;
	}

	private static class SingletonHelper {
		private static BookingManager INSTANCE = new BookingManagerImpl();
	}

}
