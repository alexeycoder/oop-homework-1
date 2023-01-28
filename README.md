# Проект информационной системы школьной администрации

## Изменения в версии от 2023-01-17

### Добавлено настраиваемое логирование

В качестве унифицированного API, позволяющего абстрагировать приложение
от конкретной системы логирования (и в дальнейшем при необходимости менять её)
использован фреймворк *The Simple Logging Facade for Java (SLF4J)*.

В качестве конкретной реализации использована система логирования *Apache Log4j2*.
Конфигурация &mdash; [log4j2.xml](src/main/java/log4j2.xml).

*Привнесённые зависимости:*

*  Модуль API для SLF4J (The Simple Logging Facade for Java) (slf4j-api-2.0.6.jar) &mdash; служит фасадом для различных фреймворков ведения журнала, позволяя конечному клиенту подключать желаемый фреймворк логирования при развёртывании.

* Модуль API для Apache Log4J (log4j-api-2.19.0.jar) &mdash; гибкий в настройке инструмент ведения журнала, с фокусом на производительность.

* Непосредственно реализация Apache Log4J (log4j-core-2.19.0.jar).

* The Apache Log4j SLF4J 2.0 API binding to Log4j 2 Core (log4j-slf4j2-impl-2.19.0.jar) &mdash; специальный адаптер, обеспечивающий работу с конкретной системой логирования Log4J2 посредством использования унифицированного API SLF4J.

*Пример журнала:*

![logging-example](https://user-images.githubusercontent.com/109767480/213255815-faa1118c-6ee4-4c1e-98cf-50cf1fdd0420.png)

### Дополнительно

* Исправлены методы обновления сущностей в базе данных
* Переработана логика выполнения команды завершения приложения в контроллерах:
вместо явного прерывания программы на месте вызовом `System.exit(0)` методы `runLifecycle()` и `switchToAction(...)`
при возвращении управления родительскому контроллеру возвращают флаг, указывающий
был ли жизненный цикл дочернего контроллера завершён естественным образом или
пользователь ввёл команду немедленного завершения приложения.

## Изменения в версии от 2022-12-26

### Переработана организация Модели.

Модуль `model` был разделён на две большие логические части:

* `dblayer` &mdash; слой "источника данных", определяющий интерфейс относительно низкоуровневого взаимодействия
с любым источником данных, предполагающим использование типичных CRUD операций
(см. интерфейс [Queryable&lt;T&gt;](src/main/java/edu/oop/schooladmin/model/dblayer/interfaces/Queryable.java)).\
А также конкретные реализации доступа к таблицам сущностей в разного рода источниках данных,
как например, в тестовой резидентной базе данных, реализованной в виде обычных списков,
так и во встраиваемой файловой СУБД SQLite.

* `businesslevel` &mdash; слой, определяющий более высокоуровневую часть модели &mdash;
бизнес-логику, с единственной реализацией в виде хранилища (набора репозиториев), способного работать с
любыми реализациями "источника данных" и предоставляющего интерфейс бизнес-логики для клиентской части
(см. интерфейс [DataProvider](src/main/java/edu/oop/schooladmin/model/businesslevel/interfaces/DataProvider.java)).

### Переработан модуль работы со встроенной СУБД SQLite.

Такая послойная организация Модели позволила *существенно упростить её реализацию и масштабируемость*.\
Так, реализация запросов к таблицам файловой базы данных SQLite (как и любой другой)
включает лишь имплементации простого интерфейса Queryable&lt;T&gt;,
а слой бизнес-логики теперь в принципе абстрагирован от каких-либо особенностей
работы с конкретными источниками данных, в нём нет ни SQL запросов, ни работы с
исходными списками резидентной БД.

*Схема реализованной БД на базе SQLite (resources/schooladmin.db):*

![sqlite_db_scheme](https://user-images.githubusercontent.com/109767480/209575522-2553068a-26c7-44e6-943c-20083f2d0c80.png)

## Проектирование информационный системы

Для реализации информационный системы разработана упрощённая структура БД учебного процесса средней школы.

*Базовые сущности модели данных*: ученик, учитель, группа (класс), предмет (дисциплина), назначения учителя, оценка.

![data_scheme](https://user-images.githubusercontent.com/109767480/208437120-9cb0f086-4e96-4da4-a779-e00c0e85051f.png)

## Организация приложения: MVC подход

При проектировании приложения использовался Model-View-Controller подход, предполагающий модульную организацию кода с выделением блоков, отвечающих за решение разных задач.

Структура проекта (показаны только основные пакеты):

![packages_description_v2](https://user-images.githubusercontent.com/109767480/209575499-fa14be16-f2e1-4619-b7ca-3f30b451a2de.png)

## Примеры работы приложения

![schooladmin-example-01](https://user-images.githubusercontent.com/109767480/208437177-c4275dbd-bfcb-475b-a529-30d74d751ef2.png)

![schooladmin-example-02](https://user-images.githubusercontent.com/109767480/208437189-b581313a-a500-43a2-9bda-a7358e221586.png)

![schooladmin-example-03](https://user-images.githubusercontent.com/109767480/208437197-f3365d25-c6c0-4720-b25d-2a6d22559768.png)

![schooladmin-example-04](https://user-images.githubusercontent.com/109767480/208437206-2e875a8b-e3ef-47ac-be3d-70d6089c4874.png)

![schooladmin-example-05](https://user-images.githubusercontent.com/109767480/208437217-698642cc-59ab-41b8-b8ff-d1664a813e0b.png)

![schooladmin-example-06](https://user-images.githubusercontent.com/109767480/208437225-cddc6a89-e3d8-4960-8c61-8fe82c77a8b0.png)

![schooladmin-example-07](https://user-images.githubusercontent.com/109767480/208437234-9046251d-ed4b-47fd-9a60-e8ccc4c6b94c.png)

![schooladmin-example-08](https://user-images.githubusercontent.com/109767480/208437242-68702a88-92a6-4333-9fee-ded7d7647fe3.png)

![schooladmin-example-09](https://user-images.githubusercontent.com/109767480/208437250-7e01bf8e-1d69-429d-ad78-b19b74799352.png)

![schooladmin-example-10](https://user-images.githubusercontent.com/109767480/208437260-ed04195e-bf60-4f3d-a101-ab5eda35936b.png)

![schooladmin-example-11](https://user-images.githubusercontent.com/109767480/208437265-8066eb1b-b198-4426-8cea-67a10bc0d9ff.png)

![schooladmin-example-12](https://user-images.githubusercontent.com/109767480/208437273-5adafa0b-1c35-4c14-95e3-b498ddc06e64.png)

![schooladmin-example-13](https://user-images.githubusercontent.com/109767480/208437290-c0914dbf-0d5a-4b2a-b598-f15026efa3b0.png)

![schooladmin-example-14](https://user-images.githubusercontent.com/109767480/208437303-f6e632ed-a0ca-4dfe-b9eb-160d52ed60c5.png)

![schooladmin-example-15](https://user-images.githubusercontent.com/109767480/208437316-736933cc-fb3b-4977-8ee8-87ff03017086.png)

![schooladmin-example-16](https://user-images.githubusercontent.com/109767480/208437327-3843e61d-8520-4a39-bfa2-f429ecffd207.png)

![schooladmin-example-17](https://user-images.githubusercontent.com/109767480/208437339-44c8889c-22e4-4339-8480-f292e0041f1f.png)

![schooladmin-example-18](https://user-images.githubusercontent.com/109767480/208437362-3fccf975-7f41-46f0-bf7d-3fcfee9e8b08.png)

![schooladmin-example-19](https://user-images.githubusercontent.com/109767480/208437495-5cb371d1-f339-460a-8b98-840d39c42ff0.png)

![schooladmin-example-20](https://user-images.githubusercontent.com/109767480/208437505-089b3463-e060-46d6-a114-9f71bb8cf61d.png)

![schooladmin-example-21](https://user-images.githubusercontent.com/109767480/208437509-a284be79-dba9-4c8b-9aff-e606a866faa2.png)

![schooladmin-example-22](https://user-images.githubusercontent.com/109767480/208437515-17602d65-ae1c-49fe-811c-dda59f89b7dc.png)

![schooladmin-example-23](https://user-images.githubusercontent.com/109767480/208437523-3f49a6dc-84fa-4b95-a894-2bdeb539e72e.png)

![schooladmin-example-24](https://user-images.githubusercontent.com/109767480/208437525-ed3c76d7-72a5-4ce8-a283-c859e7a0de09.png)

![schooladmin-example-25](https://user-images.githubusercontent.com/109767480/208437533-5e76e16c-f76b-479f-9388-9793399b1011.png)
