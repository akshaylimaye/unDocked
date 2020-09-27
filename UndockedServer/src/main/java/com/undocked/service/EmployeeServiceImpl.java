package com.undocked.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.undocked.dao.EmployeeDao;
import com.undocked.model.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeDao dao;
	
	@Override
	public String saveEmployee(Employee employee) throws Exception {
		return dao.saveEmployee(employee);
	}

	@Override
	public String getEmployee(String empId) throws Exception {
		return dao.getUsers(empId);
	}

	@Override
	public void deleteEmployee(String empId) throws Exception {
		dao.deleteEmployee(empId);
		
	}

	@Override
	public String updateEmployee(Employee emp, String empId) throws Exception {
		return dao.updateEmployee(emp, empId);
	}

}
