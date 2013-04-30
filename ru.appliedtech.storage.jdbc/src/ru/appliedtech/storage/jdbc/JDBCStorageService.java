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
package ru.appliedtech.storage.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.logging.Level;

import javax.sql.DataSource;

import lombok.val;
import ru.appliedtech.application.AppError;
import ru.appliedtech.application.AppException;
import ru.appliedtech.storage.CallableStatementExt;
import ru.appliedtech.storage.IStorageService;
import ru.appliedtech.storage.IStorageSession;
import ru.appliedtech.storage.StorageException;
import ru.appliedtech.storage.StorageStatus;


/**
 * Base JDBC implementaion of the {@link IStorageService}.
 * 
 * @author Igor Pavlenko
 */
public class JDBCStorageService implements IStorageService, IJDBCStorageService {

	public static final String JDBC_DRIVER = "jdbc_driver";
	public static final String PARAMETER_URL = "jdbc_url";
	public static final String PARAMETER_USER = "db_user";
	public static final String PARAMETER_PASSWORD = "db_password";


	private static final String ERROR_CANNOT_LOAD_DRIVER = "Cannot load JDBC Driver ''{0}''."; 

	private DataSource ds;
	private Properties properties;

	private final ThreadLocal<JDBCStorageSession> sessionHolder = new ThreadLocal<JDBCStorageSession>();


	/**
	 * Constructor.
	 */
	public JDBCStorageService(DataSource ds) {
		this.ds = ds;
	}

	/**
	 * Constructor.
	 */
	public JDBCStorageService(Properties props) {
		this.properties = props;
	}

	public static  Connection getConnection(Properties properties) throws SQLException 
	{
		String PARAMETER_URL = "jdbc_url";
		String PARAMETER_USER = "db_user";
		String PARAMETER_PASSWORD = "db_password";
		String jdbcURL = (String) properties.get(PARAMETER_URL);
    	String user = (String) properties.get(PARAMETER_USER);
    	String password = (String) properties.get(PARAMETER_PASSWORD);

    	return DriverManager.getConnection(jdbcURL, user, password);
	}

	@Override
	public void executeWithSession(SessionRunnable runnable) throws StorageException {
		try {
			JDBCStorageSession session = sessionHolder.get();
			if (session != null) {
				runnable.run(session);
			} else {
				try {
					session = openSession();
					sessionHolder.set(session);
					runnable.run(session);
				} finally {
					if (session != null) {
						sessionHolder.set(null);
						session.close();
					}
				}
			}
		} catch (StorageException e) {
			throw e;
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new StorageException(ERROR_CANNOT_EXECUTE_STATEMENT, e, StorageStatus.FAILURE_UNKNOWN_REASON);
		} catch (Exception e) {
			throw new StorageException(ERROR_CANNOT_EXECUTE_STATEMENT, e, StorageStatus.FAILURE_UNKNOWN_REASON);
		}
	}

	@Override
	public void executeWithTransaction(final TransactionRunnable runnable) throws StorageException {
		
		final JDBCStorageTransaction transaction = new JDBCStorageTransaction();
		
		executeWithSession(new SessionRunnable() {
			@Override
			public void run(IStorageSession session) throws Exception {
				
				val connection = ((JDBCStorageSession) session).getConnection();
				
				boolean autoCommit = connection.getAutoCommit();
				try {
					connection.setAutoCommit(false);
					transaction.setConnection(connection);
					try {
						runnable.run(transaction);
						connection.commit();
					} catch (InterruptedException e) {
						throw e;
					} catch (Exception e) {
						connection.rollback();
						throw e;
					}
				} finally {
					try {
						connection.setAutoCommit(autoCommit);
					} finally {
						session.close();
					}
				}
			}
		});
		
		transaction.handleSuccess();
	}

	public JDBCStorageSession openSession() throws StorageException {
		try {
			Connection connection;
			if (ds != null) {
				connection =  ds.getConnection();
			}
			else{
				try
				{
					Class.forName(properties.getProperty(JDBC_DRIVER));
				}
				catch (ClassNotFoundException e)
				{
					throw new AppError(MessageFormat.format(ERROR_CANNOT_LOAD_DRIVER, JDBC_DRIVER), e);
				}
				connection = getConnection(properties);
			}
			return new JDBCStorageSession(connection);
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, ERROR_CANNOT_CREATE_CONNCETION, e);
			throw new StorageException(ERROR_CANNOT_CREATE_CONNCETION, e, StorageStatus.FAILURE_UNKNOWN_REASON);
		}
	}
	
	@Override
	public void executeWithConnection(final ConnectionRunnable runnable) throws StorageException {
		executeWithSession(new SessionRunnable() {
			@Override
			public void run(IStorageSession session) throws Exception {
				val connection = ((JDBCStorageSession) session).getConnection();
				runnable.run(connection);
			}
		});
	}

	@Override
	public void executeCallableStatement(final String statement, final CallableStatementRunnable runnable) throws StorageException {
		executeWithSession(new SessionRunnable() {
			@Override
			public void run(IStorageSession session) throws SQLException, AppException {
				val connection = ((JDBCStorageSession) session).getConnection();
				CallableStatement stmt = null;
				try {
					stmt = connection.prepareCall(statement);
					runnable.run(new CallableStatementExt(stmt));
				} finally {
					if (stmt != null) {
						stmt.close();
					}
				}
			}
		});
	}
}
