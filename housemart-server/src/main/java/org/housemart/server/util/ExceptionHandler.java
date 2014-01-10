package org.housemart.server.util;

import java.lang.reflect.InvocationTargetException;

public class ExceptionHandler {

	public static void renderHandle(Exception e) throws Exception {
		Exception temp = new Exception();
		if (e instanceof InvocationTargetException) {
			temp = (InvocationTargetException) e;
			for (int i = 0; i < 10; i++) {
				InvocationTargetException wow = (InvocationTargetException) temp;
				temp = (Exception) wow.getTargetException();
				if (!(temp instanceof InvocationTargetException)) {
					break;
				}
			}
		} else {
			throw e;
		}

		throw temp;
	}

}
