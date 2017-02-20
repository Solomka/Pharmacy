package com.upp.apteka.utils.validation;

import java.math.BigDecimal;
import java.math.MathContext;

import org.springframework.util.StringUtils;

public final class CValidationUtils {

	private CValidationUtils() {
		throw new RuntimeException();
	}

	/**
	 * from string to DigDecimal:
	 * 			null/"" -> BigDecimal.ZERO
	 * 			!, -> BigDecimal(str, MathContext.DECIMAL64)
	 * 			(,)* -> BigDecimal.ZERO
	 * 			, -> . -> BigDecimal(newStr, MathContext.DECIMAL64)
	 * @param str
	 * @return
	 */
	public static BigDecimal fromStringToBigDecimal(String str) {
		if (str == null || str.isEmpty()) {
			return BigDecimal.ZERO;
		} else if (!str.contains(",")) {
			return new BigDecimal(str, MathContext.DECIMAL64);
		} else if (StringUtils.countOccurrencesOf(str, ",") > 1) {
			return BigDecimal.ZERO;
		} else {
			String correctBDString = str.replace(',', '.');
			return new BigDecimal(correctBDString, MathContext.DECIMAL64);
		}
	}

}
