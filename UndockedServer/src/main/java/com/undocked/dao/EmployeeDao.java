package com.undocked.dao;

import com.undocked.model.Employee;

public interface EmployeeDao {

	String saveEmployee(Employee employee) throws Exception;

	String getUsers(String empId) throws Exception;

	void deleteEmployee(String empId) throws Exception;

	String updateEmployee(Employee emp, String empId) throws Exception;

}
