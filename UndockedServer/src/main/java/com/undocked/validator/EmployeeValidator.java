package com.undocked.validator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.undocked.model.Employee;
import com.undocked.utility.Constants;

public class EmployeeValidator {

	private Employee employee;
	private JSONObject request;
	private JSONArray errorArray = new JSONArray();

	private EmployeeValidator(JSONObject request) {
		this.request = request;
	}

	public Employee getEmployee() {
		return employee;
	}

	public static synchronized EmployeeValidator getValidator(JSONObject request) {
		return new EmployeeValidator(request);
	}

	public JSONArray validateEmployeeObject() throws Exception {
		employee = new Employee();
		if (mandatoryField(request, Constants.FIRST_NAME)) {
			employee.setFirstName(request.get(Constants.FIRST_NAME).toString());
		} else {
			errorMessages(Constants.FIRST_NAME);
		}
		if (mandatoryField(request, Constants.LAST_NAME)) {
			employee.setLastName(request.get(Constants.LAST_NAME).toString());
		} else {
			errorMessages(Constants.LAST_NAME);
		}
		if (mandatoryField(request, Constants.DEPT_NAME)) {
			employee.setDeptName(request.get(Constants.DEPT_NAME).toString());
		} else {
			errorMessages(Constants.DEPT_NAME);
		}
		return errorArray;
	}

	private void errorMessages(String key) {
		JSONObject json = new JSONObject();
		json.put("key", key);
		json.put("message", Constants.MANDATORY);
		errorArray.put(json);
	}

	private boolean mandatoryField(JSONObject request, String key) {
		if (request.has(key) && request.get(key).toString() != null && !request.get(key).toString().isEmpty())
			return true;
		return false;
	}

}
