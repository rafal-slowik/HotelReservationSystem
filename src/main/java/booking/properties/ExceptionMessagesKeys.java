/**
 * 
 */
package booking.properties;

/**
 * @author Rafal Slowik
 * @date 11 Mar 2017
 *
 */
public enum ExceptionMessagesKeys {
	EXC_MSG_ROOM_ALREADY_BOOKED("exception.room_already_booked.text"), //
	EXC_MSG_ROOM_DOES_NOT_EXIST("exception.room_does_not_exist.text"), //
	EXC_MSG_PARAM_MUST_NOT_BE_NULL("exception.must_not_be_null.text"), //
	EXC_MSG_REP_TYPE_NOT_SUPPORTED("exception.repository_type_not_supported.text");

	private ExceptionMessagesKeys(String propertyKey) {
		this.propertyKey = propertyKey;
	}

	private String propertyKey;

	public String getPropertyKey() {
		return propertyKey;
	}
}
