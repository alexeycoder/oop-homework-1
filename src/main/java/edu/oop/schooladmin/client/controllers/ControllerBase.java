package edu.oop.schooladmin.client.controllers;

import edu.oop.schooladmin.client.views.ViewBase;
import edu.oop.schooladmin.model.interfaces.DataProvider;

public abstract class ControllerBase {

	protected final DataProvider dp;
	protected final ViewBase view;

	public ControllerBase(DataProvider dataProvider, ViewBase viewManager) {
		this.dp = dataProvider;
		this.view = viewManager;
	}

	/** Запуск жизненного цикла контроллера */
	abstract void runLifecycle();
}
