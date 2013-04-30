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
package ru.appliedtech.storage.hibernate;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

import lombok.val;
import ru.appliedtech.storage.IStorageService;
import ru.appliedtech.storage.IStorageSession;
import ru.appliedtech.storage.StorageException;
import ru.appliedtech.storage.StorageStatus;
import ru.appliedtech.storage.StorageTransaction;


public class HibernateStorageService implements IStorageService, IHibernateStorageService, AutoCloseable {
	
	private final EntityManagerFactory emf;
	
	private final ThreadLocal<HibernateStorageSession> sessionHolder = new ThreadLocal<HibernateStorageSession>();
	
	protected static final Logger LOG = Logger.getLogger(HibernateStorageService.class.getName());
	
	public HibernateStorageService(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public void executeWithSession(SessionRunnable runnable) throws StorageException {
		try {
			HibernateStorageSession session = sessionHolder.get();
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
		} catch (EntityNotFoundException e) {
			throw new StorageException(ERROR_CANNOT_FETCH_NECESSARY_DATA, e, StorageStatus.ENTITY_NOT_FOUND);
		} catch (NoResultException e) {
			throw new StorageException(ERROR_CANNOT_FETCH_NECESSARY_DATA, e, StorageStatus.ENTITY_NOT_FOUND);		
		} catch (RollbackException e) {
			if (e.getCause() != null && e.getCause() instanceof PersistenceException) {
				throw new StorageException(ERROR_CANNOT_EXECUTE_STATEMENT, e, StorageStatus.BAD_REQUEST);
			} else {
				throw new StorageException(ERROR_CANNOT_EXECUTE_STATEMENT, e, StorageStatus.FAILURE_UNKNOWN_REASON);
			}
		} catch (PersistenceException e) {
			throw new StorageException(ERROR_CANNOT_FETCH_NECESSARY_DATA, e, StorageStatus.BAD_REQUEST);
		} catch (Exception e) {
			throw new StorageException(ERROR_CANNOT_EXECUTE_STATEMENT, e, StorageStatus.FAILURE_UNKNOWN_REASON);
		}
	}
	
	@Override
	public void executeWithTransaction(final TransactionRunnable runnable) throws StorageException {
		
		final StorageTransaction transaction = new StorageTransaction();
		
		executeWithSession(new SessionRunnable() {
			@Override
			public void run(IStorageSession session) throws Exception {
				
				val tx = ((HibernateStorageSession) session).getManager().getTransaction();
				
				try {
					tx.begin();
					runnable.run(transaction);
					tx.commit();
					
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					throw new StorageException(ERROR_CANNOT_EXECUTE_STATEMENT, e, StorageStatus.FAILURE_UNKNOWN_REASON);
				} catch (Exception e) {
					if (tx != null && tx.isActive()) tx.rollback();
					throw e;
				}
			}
		});
		
		transaction.handleSuccess();
	}

	@Override
	public void executeWithEntityManager(final EntityManagerRunnable runnable) throws StorageException {
		executeWithSession(new SessionRunnable() {
			@Override
			public void run(IStorageSession session) throws Exception {
				val em = ((HibernateStorageSession) session).getManager();
				runnable.run(em);
			}
		});
	}
	
	public HibernateStorageSession openSession() throws StorageException {
		try {
			return new HibernateStorageSession(emf.createEntityManager());
		} catch (IllegalStateException e) {
			LOG.log(Level.SEVERE, ERROR_CANNOT_CREATE_CONNCETION, e);
			throw new StorageException(ERROR_CANNOT_CREATE_CONNCETION, e, StorageStatus.FAILURE_UNKNOWN_REASON);
		}
	}

	@Override
	public void close() throws Exception {
		if (emf != null && emf.isOpen()) {
			emf.close();
		}
	}
}
