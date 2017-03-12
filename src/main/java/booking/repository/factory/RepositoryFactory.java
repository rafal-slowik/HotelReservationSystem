/**
 * 
 */
package booking.repository.factory;

import static booking.properties.ConfigProperties.getConfigInstance;
import static booking.properties.ExceptionMessagesKeys.*;

import booking.repository.NonDbRepository;
import booking.repository.Repository;

/**
 * Factory method which creates corresponding type of repository.
 * 
 * @author Rafal Slowik
 * @date 12 Mar 2017
 *
 */
public class RepositoryFactory {

	/**
	 * Get repository by type. Following types are supported:<BR>
	 * <li>{@link RepositoryType#NON_DB_BASED_IMPLEMENTATION} which produce
	 * {@link NonDbRepository} implementation.</li>
	 * 
	 * @param type
	 *            {@link RepositoryType}
	 * @return {@link Repository}
	 * @throws IllegalArgumentException
	 *             if the type is {@code null} or currently unsupported type of
	 *             repository
	 */
	public Repository getFactory(RepositoryType type) {
		if (type == null) {
			throw new IllegalArgumentException(getConfigInstance()
					.formatProperty(EXC_MSG_PARAM_MUST_NOT_BE_NULL.getPropertyKey(), "Factory type"));
		}

		// ready to add new types of repository
		switch (type) {

		case NON_DB_BASED_IMPLEMENTATION:
			return new NonDbRepository();
		default:
			throw new IllegalArgumentException(
					getConfigInstance().formatProperty(EXC_MSG_REP_TYPE_NOT_SUPPORTED.getPropertyKey(), type));

		}
	}
}
