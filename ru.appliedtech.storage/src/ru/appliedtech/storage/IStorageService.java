/*
* Copyright (c) 2013 Applied Technologies, Ltd.
*
* Permission is hereby granted, free of charge, to any person obtaining
* a copy of this software and associated documentation files (the
* "Software"), to deal in the Software without restriction, including
* without limitation the rights to use, copy, modify, merge, publish,
* distribute, sublicense, and/or sell copies of the Software, and to
* permit persons to whom the Software is furnished to do so, subject to
* the following conditions:
*
* The above copyright notice and this permission notice shall be
* included in all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
* EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
* MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
* NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
* LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
* OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
* WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*
*/
package ru.appliedtech.storage;

import java.util.logging.Logger;

import ru.appliedtech.application.AppException;
import ru.appliedtech.application.services.IService;


/**
 * The base interface for storage access.
 * @author Igor Pavlenko
 */
public interface IStorageService extends IService {
	
	Logger LOG = Logger.getLogger(IStorageService.class.getName());
	
	String ERROR_CANNOT_CREATE_CONNCETION = "Cannot create connection";
	String ERROR_CANNOT_EXECUTE_STATEMENT = "Cannot execute statement";
	String ERROR_CANNOT_PREPARE_DATA = "Cannot prepare data";
	String ERROR_CANNOT_FETCH_NECESSARY_DATA = "Cannot fetch necessary data";
	String ERROR_UNIQUE_CONSTRAINT_VIOLATION = "Title unique constraint violation";
	String ERROR_CANNOT_CLOSE_STATEMENT = "Cannot close statement";
	
	/**
	 * Base interface for storage calls.
	 * @author Igor Pavlenko
	 */
	public static interface StorageRunnable {

		void run() throws StorageException;
	}

	/**
	 * Base interface for transactional storage calls.
	 * @author ipavlenko
	 */
	public static interface TransactionRunnable {
		
		/**
		 * @param transaction The transaction with "on-success" handler.
		 * @throws Exception
		 */
		void run(StorageTransaction transaction) throws Exception;
	}
	
	/**
	 * Looks for a connection, executes {@link TransactionRunnable} and commits changes. Also
	 * executes on-success handler of the runnable if necessary.
	 * 
	 * @param runnable The runnable.
	 * @throws AppException
	 */
	void executeWithTransaction(TransactionRunnable runnable) throws StorageException;

	/**
	 * Base interface for storage calls.
	 * @author Igor Pavlenko
	 */
	public static interface SessionRunnable {

		/**
		 * @param session Storage session/connection.
		 * @throws Exception
		 */
		void run(IStorageSession session) throws Exception;
	}

	/**
	 * Looks for a connection, executes {@link SessionRunnable}.
	 * @param runnable The runnable to execute.
	 * @throws AppException
	 */
	void executeWithSession(SessionRunnable runnable) throws StorageException;
}
