package com.undocked.service;

import com.undocked.model.Employee;

public interface EmployeeService {

	public String saveEmployee(Employee employee) throws Exception;

	public String getEmployee(String empId) throws Exception;

	public void deleteEmployee(String empId) throws Exception;

	public String updateEmployee(Employee emp, String empId) throws Exception;


}
