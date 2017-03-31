package com.main.hiber;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class MainHiber {
	static SessionFactory factory;

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		try {
			factory = new Configuration().configure().buildSessionFactory();
			int id = insert();
			// update(id);
			getAll();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * insert record in table
	 * @return
	 */
	public static int insert() {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer userID = null;
		try {
			tx = session.beginTransaction();

			// insert data to bean
			UserDetail detail = new UserDetail();
			detail.setUser("admn");
			detail.setPass("root");

			// add record and get id
			userID = (Integer) session.save(detail);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		System.err.println("record inserted");
		return userID;
	}

	/**
	 * update record in table
	 * @param id
	 */
	public static void update(int id) {

		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			// get data according to id
			UserDetail detail = (UserDetail) session.get(UserDetail.class, id);

			// update data in bean
			detail.setPass("rot");

			// update table
			session.update(detail);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		System.err.println("record updated");

	}


	/**
	 * get all records from table
	 */
	public static void getAll() {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			// all list of record
			List result = session.createQuery("from com.main.hiber.UserDetail").list();
			System.err.println(result.toString());
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
	}
}
