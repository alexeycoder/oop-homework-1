У сущностей данных поля, соответсвующие primary key именуем включая имя
класса (сущности), т.е., например, для `Student` должно быть `int studentId;`.

<hr>

Атрибуты 'отчества' у сущностей Ученик, Учитель убираем для упрощения.

<hr>

Документация *Hibernate ORM*, одной из Java ORM систем, рекомендует использовать
не примитивный тип (aka boxed, и следовательно nullable) для свойств идентификатора.

*Например:*

	@Id
	private Long id;

Хотя, в ОРМ описание своиства и различные ограничения на него регулируются с помощью [аннотаций](https://docs.jboss.org/hibernate/orm/5.3/userguide/html_single/Hibernate_User_Guide.html#annotations-hibernate), которые в нашем случае не применимы, тем не менее, тоже *будем использовать boxed-версии базовых типов для свойств идентификаторов в классах сущностей*.

А вот в параметрах методов репозиториев, типа `getStudentById(*int* studentId)`, там где требуется указание идентификатора сущности (напр. `studentId`), будем для параметра использовать примитивный тип (`int`), чтобы не допустить передачи *null*-значения для идентификатора, недопустимого по смыслу работы соответствующего метода.

*См. линки:*

[4.1.2. Provide an identifier property (optional)](https://docs.jboss.org/hibernate/core/3.3/reference/en/html/persistent-classes.html#persistent-classes-pojo-identifier)

[2.5.5. Providing identifier attribute(s)](https://docs.jboss.org/hibernate/orm/5.3/userguide/html_single/Hibernate_User_Guide.html#entity-pojo-identifier)

<hr>

JPA (Java Persistence API, реализующий концепцию ORM) требует чтобы классы сущностей имели конструктор по-умолчанию (т.е. без аргументов). Делаем тоже.

<hr>

Для экспериментов с **SQL Lite**:

[Builds](https://github.com/xerial/sqlite-jdbc/releases)

[Sqlite-JDBC](https://github.com/xerial/sqlite-jdbc)
