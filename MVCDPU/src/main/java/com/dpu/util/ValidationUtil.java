package com.dpu.util;

import java.util.Collection;
import java.util.Map;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class ValidationUtil {
 
	public static boolean isNull(Object obj) {
		if (obj == null) {
			return true;
		} else if (obj instanceof Collection) {
			return CollectionUtils.isEmpty((Collection) obj);
		} else if (obj instanceof Map) {
			return CollectionUtils.isEmpty((Map) obj);
		} else if (obj instanceof String) {
			return StringUtils.isEmpty(((String) obj).trim());
		} else if (obj instanceof Number) {
			Number number = (Number) obj;
			if (number.doubleValue() == 0.0) {
				return true;
			}
		}
		return false;
	}
}
