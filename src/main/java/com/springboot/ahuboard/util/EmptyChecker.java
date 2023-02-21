package com.springboot.ahuboard.util;

public class EmptyChecker {

	public static boolean isEmpty(Object obj) {

		
		if(obj==null) {
			return true;
		}
		
		//숫자일 경우 (null과 0을 체크)
		if(obj instanceof Number) {
			Number n = (Number)obj;
			return n.doubleValue() == 0d;
		}
		
		//문자일 경우(null과 empty String을 체크)
		if(obj instanceof String) {
			String s = (String)obj;
			return s.isEmpty();
		}
		return true;
	}

	public static boolean isEmpty(Object ... args) {
		if(args == null || args.length ==0) return true;
		
		for(Object obj : args) {
			if(!isEmpty(obj)) {
				return false;
			}
		}
		
		return true;
	}

}
