package edu.oop.schooladmin.client.views;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Function;

import edu.oop.schooladmin.client.viewmodels.ViewModelBase;

public interface ViewBase {

	void clear();

	void showEmpty();

	void waitToProceed();

	void showGoodbye();

	void showText(String text);

	void showMenu(Map<Object, String> menuModel);

	void showList(List<? extends ViewModelBase> viewModelsList, String title);

	boolean askYesNo(String prompt, boolean isYesDefault);

	/**
	 * Запрашивает выбор пункта меню от пользователя.
	 * 
	 * @param prompt    Приглашение ввода ответа, типа "Выберите пункт меню: "
	 * @param menuModel Модель представления меню. Используется здесь для проверки
	 *                  соответствия ввода пользователя пунктам, определённым в
	 *                  модели меню. Если введённый пользоваетелем ответ не
	 *                  предусмотрен меню, то необходимо показать пользователю
	 *                  предупреждение и запросить повторный ввод.
	 * @return Выбор пользователя: ключь из модели представления меню.
	 */
	Object askUserChoice(String prompt, Map<Object, String> menuModel);

	OptionalInt askInteger(String prompt, Integer min, Integer max);

	OptionalInt askInteger(String prompt, Function<Integer, Boolean> checkValidity, String wrongWarn);

	Optional<String> askString(String prompt, Function<String, Boolean> checkValidity, String wrongWarn);
}
