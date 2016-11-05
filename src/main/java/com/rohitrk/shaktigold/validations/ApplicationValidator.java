package com.rohitrk.shaktigold.validations;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.stereotype.Component;

import com.rohitrk.shaktigold.model.UserAccountModel;

@Component
public class ApplicationValidator {

	//List<String> userAccountModelRequiredFiels = new ArrayList<String>();
	String[] userAccountModelRequiredFiels = {"firstName","lastName","email"};
	
	public Object validateUserAccountModel(UserAccountModel model) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Field field = model.getClass().getDeclaredField("lastName");
		int i = 0;
		System.out.println(field.get(model));
		
		for(Method method : UserAccountModel.class.getDeclaredMethods()) {
			if(method.getName().startsWith("get") && (method.getName().length() == (userAccountModelRequiredFiels[i].length() + 3))) {
				if(method.getName().toLowerCase().endsWith(userAccountModelRequiredFiels[i])) {
					try {
						String value = (String) method.invoke(model, new Object[0]);
						if(value == null) {
							System.out.println(userAccountModelRequiredFiels[i] + " is null");
						} else {
							System.out.println(userAccountModelRequiredFiels[i] + " = " + value);
						}
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return true;
	}

	public static boolean validateLoginFields(UserAccountModel userAccount) {
		
		if(userAccount.getEmail() == null || userAccount.getEmail().isEmpty()) {
			 return false;
		}
		
		if(userAccount.getPassword() == null || userAccount.getPassword().isEmpty()) {
			return false;
		}
		
		return true;
	}
}
