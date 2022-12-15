package edu.oop.schooladmin.client.views;

import java.util.List;
import java.util.Map;

import edu.oop.schooladmin.client.viewmodels.ViewModelBase;

public interface ViewBase {
	public void waitEnterToProceed();

	void showMenu(Map<Object, String> menuModel);

	Object askUserChoice(String askModel, Map<Object, String> menuModel);

	public void showList(List<? extends ViewModelBase> viewModelsList, String title);

	public void showGoodbye();
}
