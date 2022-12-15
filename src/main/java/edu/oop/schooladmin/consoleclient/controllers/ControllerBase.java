package edu.oop.schooladmin.consoleclient.controllers;

import java.util.Scanner;

import edu.oop.schooladmin.model.interfaces.DataProvider;

public abstract class ControllerBase {

	protected final DataProvider dp;
	protected final Scanner scanner;

	public ControllerBase(DataProvider dp, Scanner scanner) {
		this.dp = dp;
		this.scanner = scanner;
	}

	/** Запуск жизненного цикла контроллера */
	abstract void runLifecycle();
}
