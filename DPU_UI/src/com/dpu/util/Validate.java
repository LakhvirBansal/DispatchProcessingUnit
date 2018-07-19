package com.dpu.util;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class Validate {

	public boolean validateLength(String str, int minLength, int maxLength) {
		if (str != null && str.length() > 0) {
			if (str.length() >= minLength && str.length() <= maxLength) {
				return true;
			}
		}
		return false;
	}

	public boolean validateEmptyness(String str) {
		if (str != null && str.trim().length() > 0) {
			return true;
		}
		return false;
	}

 

	public boolean isNumeric(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c))
				return false;
		}
		return true;
	}

	public boolean isAlpha(String name) {
		for (char c : name.toCharArray()) {
			if (!Character.isLetter(c)) {
				return false;
			}
		}
		return true;
	}

	public boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}

	public static boolean containsWhiteSpace(final String str) {
		if (str != null) {
			for (int i = 0; i < str.length(); i++) {
				if (Character.isWhitespace(str.charAt(i))) {
					return false;
				}
			}
		}
		return true;
	}

	// Numeric Validation Limit the characters to maxLengh AND to ONLY DigitS
	public static EventHandler<KeyEvent> numeric_Validation(final Integer max_Lengh) {
		return new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				TextField txt_TextField = (TextField) e.getSource();
				if (txt_TextField.getText().length() >= max_Lengh) {
					e.consume();
				}
				if (e.getCharacter().matches("[0-9.]")) {
					if (txt_TextField.getText().contains(".") && e.getCharacter().matches("[.]")) {
						e.consume();
					} else if (txt_TextField.getText().length() == 0 && e.getCharacter().matches("[.]")) {
						e.consume();
					}
				} else {
					e.consume();
				}
			}
		};
	}

	// Letters Validation Limit the characters to maxLengh AND to ONLY Letters
	public static EventHandler<KeyEvent> letter_Validation(final Integer max_Lengh) {
		return new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				TextField txt_TextField = (TextField) e.getSource();
				if (txt_TextField.getText().length() >= max_Lengh) {
					e.consume();
				}
				if (e.getCharacter().matches("[A-Za-z]")) {
				} else {
					e.consume();
				}
			}
		};
	}
}
