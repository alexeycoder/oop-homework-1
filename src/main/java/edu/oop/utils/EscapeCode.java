package edu.oop.utils;

public enum EscapeCode {
	CLEAR(EscapeCode.csi + "1;1H" + EscapeCode.csi + "2J"),
	RESET(EscapeCode.csi + "0m"),
	BOLD(EscapeCode.csi + "1m"),
	ITALIC(EscapeCode.csi + "3m"),
	UNDERLINE(EscapeCode.csi + "4m"),
	BLINK(EscapeCode.csi + "5m"),

	FG_RED(EscapeCode.csi + "31m"),
	FG_BRIGHT_WHITE(EscapeCode.csi + "97m");

	private final String code;
	private static final String csi = "\u001b[";

	private EscapeCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
