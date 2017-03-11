/**
 * 
 */
package booking.entity;

import java.time.LocalDate;

/**
 * Booking entity.
 * 
 * @author Rafal Slowik
 * @date 11 Mar 2017
 *
 */
public class Booking {

	private LocalDate bookingDate;
	private String guestSurname;
	
	public Booking(String guestSurname, LocalDate bookingDate) {
		this.guestSurname = guestSurname;
		this.bookingDate = bookingDate;
	}
	
	public Booking() {}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getGuestSurname() {
		return guestSurname;
	}

	public void setGuestSurname(String guestSurname) {
		this.guestSurname = guestSurname;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookingDate == null) ? 0 : bookingDate.hashCode());
		result = prime * result + ((guestSurname == null) ? 0 : guestSurname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Booking other = (Booking) obj;
		if (bookingDate == null) {
			if (other.bookingDate != null)
				return false;
		} else if (!bookingDate.equals(other.bookingDate))
			return false;
		if (guestSurname == null) {
			if (other.guestSurname != null)
				return false;
		} else if (!guestSurname.equals(other.guestSurname))
			return false;
		return true;
	}
}
