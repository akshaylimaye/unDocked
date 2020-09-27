package com.undocked.utility;

import java.util.regex.Pattern;

public class EmployeeServerUtility {

	public boolean numericByString(String strNum) {
		Pattern pattern = Pattern.compile("[0-9]+");
		if (strNum == null || strNum.isEmpty()) {
			return false;
		}
		return pattern.matcher(strNum).matches();
	}

}
