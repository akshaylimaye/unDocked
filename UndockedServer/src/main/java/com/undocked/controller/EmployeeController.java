package com.undocked.controller;

import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.undocked.utility.Constants;
import com.undocked.model.Employee;
import com.undocked.service.EmployeeService;
import com.undocked.utility.EmployeeServerUtility;
import com.undocked.validator.EmployeeValidator;

@RestController
@CrossOrigin("*")
public class EmployeeController {

	@Autowired
	EmployeeService service;

	static Logger LOGGER = Logger.getLogger(EmployeeController.class);

	private EmployeeValidator validator;

	@RequestMapping(method = RequestMethod.POST, value = "/employee")
	public ResponseEntity<String> saveEmployee(@RequestBody String request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Entering in saveEmployee-" + request);
		}
		JSONObject json = new JSONObject(request);
		JSONArray validationArray = new JSONArray();
		try {
			validator = EmployeeValidator.getValidator(json);
			validationArray = validator.validateEmployeeObject();
			if (!validationArray.isEmpty()) {
				throw new Exception(validationArray.toString());
			}
			Employee emp = validator.getEmployee();
			String response = service.saveEmployee(emp);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (Exception e) {
			LOGGER.error("Exception- ", e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/employee")
	public ResponseEntity<String> getEmployee(@QueryParam("empId") String empId) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Entering in getEmployee empId" + empId);
		}
		EmployeeServerUtility util = new EmployeeServerUtility();
		try {
			if (null != empId) {
				if (!util.numericByString(empId)) {
					JSONObject json = new JSONObject();
					json.put("key", empId);
					json.put("message", Constants.INVALID);
					throw new Exception(json.toString());
				}
			}
			return ResponseEntity.status(HttpStatus.OK).body(service.getEmployee(empId));
		} catch (Exception e) {
			LOGGER.error("Exception- ", e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/employee/{empId}")
	public ResponseEntity<?> deleteEmployee(@PathVariable("empId") String empId) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Entering in deleteEmployee empId" + empId);
		}
		EmployeeServerUtility util = new EmployeeServerUtility();
		try {
			if (!util.numericByString(empId)) {
				JSONObject json = new JSONObject();
				json.put("key", empId);
				json.put("message", Constants.INVALID);
				throw new Exception(json.toString());
			}
			service.deleteEmployee(empId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
		} catch (Exception e) {
			LOGGER.error("Exception- ", e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/employee/{empId}")
	public ResponseEntity<String> updateEmployee(@PathVariable String empId, @RequestBody String request) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Entering in updateEmployee empId" + empId);
		}
		JSONObject json = new JSONObject(request);
		JSONArray validationArray = new JSONArray();
		EmployeeServerUtility util = new EmployeeServerUtility();
		try {
			if (!util.numericByString(empId)) {
				JSONObject error = new JSONObject();
				error.put("key", empId);
				error.put("message", Constants.INVALID);
				throw new Exception(error.toString());
			}
			validator = EmployeeValidator.getValidator(json);
			validationArray = validator.validateEmployeeObject();
			if (!validationArray.isEmpty()) {
				throw new Exception(validationArray.toString());
			}
			Employee empployee = validator.getEmployee();
			String response = service.updateEmployee(empployee, empId);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (Exception e) {
			LOGGER.error("Exception- ", e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
