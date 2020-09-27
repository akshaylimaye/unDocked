package com.undocked.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.undocked.model.Employee;
import com.undocked.model.Status;

@Repository
@Transactional
public class EmployeeDaoImpl implements EmployeeDao {

	static Logger LOGGER = Logger.getLogger(EmployeeDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public String saveEmployee(Employee employee) throws Exception {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Entering to EmployeeDaoImpl saveEmployee- " + employee);
		}
		Session session = null;
		Transaction tx = null;
		JSONObject response = new JSONObject();
		try {
			session = sessionFactory.openSession();
			Status status = session.get(Status.class, 1);
			employee.setStatus(status);
			tx = session.beginTransaction();
			session.save(employee);
			session.flush();
			tx.commit();
			response.put("message", "employee saved succesfully");
			response.put("id", employee.getEmpId());
		} catch (Exception e) {
			if (null != tx) {
				tx.rollback();
			}
			LOGGER.error("Exception in saveEmployee- " + e);
			throw new Exception(e.getMessage());
		} finally {
			if (null != session) {
				session.close();
			}
		}
		return response.toString();
	}

	@SuppressWarnings("deprecation")
	@Override
	public String getUsers(String empId) throws Exception {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Entering to EmployeeDaoImpl getUsers- " + empId);
		}
		Session session = null;
		JSONArray response = new JSONArray();
		try {
			session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Employee.class, "employee");
			criteria.add(Restrictions.eq("employee.status.statusId", 1));
			if (null != empId) {
				criteria.add(Restrictions.eq("employee.empId", Integer.valueOf(empId)));
			}
			@SuppressWarnings("unchecked")
			List<Employee> criList = criteria.list();
			for (Employee employee : criList) {
				response.put(employee.serialize());
			}
		} catch (Exception e) {
			LOGGER.error("Exception in saveEmployee- " + e);
			throw new Exception(e.getMessage());
		}
		return response.toString();
	}

	@Override
	public void deleteEmployee(String empId) throws Exception {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Entering to EmployeeDaoImpl deleteEmployee- " + empId);
		}
		Session session = null;

		try {
			session = sessionFactory.getCurrentSession();
			Status status = session.get(Status.class, 0);
			Employee employee = session.get(Employee.class, Integer.valueOf(empId));
			employee.setStatus(status);
			session.update(employee);
		} catch (Exception e) {
			LOGGER.error("Exception in saveEmployee- " + e);
			throw new Exception(e.getMessage());
		}

	}

	@Override
	public String updateEmployee(Employee emp, String empId) throws Exception {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Entering to EmployeeDaoImpl deleteEmployee- " + empId);
		}
		Session session = null;
		Transaction tx = null;
		JSONObject response = new JSONObject();
		try {
			session = sessionFactory.openSession();
			Employee employee = session.get(Employee.class, Integer.valueOf(empId));
			if (null != emp.getFirstName()) {
				employee.setFirstName(emp.getFirstName());
			}
			if (null != emp.getLastName()) {
				employee.setLastName(emp.getLastName());
			}
			if (null != emp.getDeptName()) {
				employee.setDeptName(emp.getDeptName());
			}
			tx = session.beginTransaction();
			session.update(employee);
			session.flush();
			tx.commit();
			response.put("message", "employee updated succesfully");
			response.put("id", employee.getEmpId());
		} catch (Exception e) {
			if (null != tx) {
				tx.rollback();
			}
			LOGGER.error("Exception in saveEmployee- " + e);
			throw new Exception(e.getMessage());
		} finally {
			if (null != session) {
				session.close();
			}
		}
		return response.toString();
	}

}
