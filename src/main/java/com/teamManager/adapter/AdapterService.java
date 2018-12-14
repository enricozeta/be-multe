package com.teamManager.adapter;

/**
 * The Interface AdapterService.
 */
public interface AdapterService {

	/**
	 * Gets the adapter.
	 *
	 * @param <T>
	 *            the generic type
	 * @param adaptableObject
	 *            the adaptable object
	 * @param adapterType
	 *            the adapter type
	 * @return the adapter
	 * @throws Exception
	 *             the exception
	 */
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) throws Exception;

}
