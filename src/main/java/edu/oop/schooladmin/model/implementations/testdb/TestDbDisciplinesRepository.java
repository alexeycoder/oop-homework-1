package edu.oop.schooladmin.model.implementations.testdb;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import edu.oop.schooladmin.model.entities.Discipline;
import edu.oop.schooladmin.model.interfaces.DisciplinesRepository;
import edu.oop.schooladmin.testdatatables.DisciplinesTable;

public class TestDbDisciplinesRepository implements DisciplinesRepository {

	private final ArrayList<Discipline> disciplines;
	private int lastId;

	public TestDbDisciplinesRepository() {
		disciplines = DisciplinesTable.disciplines();
		lastId = RepositoryUtils.getLastPrimaryKey(disciplines, d -> d.getDisciplineId());
	}

	@Override
	public Discipline addDiscipline(Discipline discipline) {
		makeSureValidity(discipline);

		discipline.setDisciplineId(++lastId);

		// В таблицу сохраняем клон переданного экземпляра, а не его самого,
		// чтобы разорвать связь между переданным экземпляром и фактически
		// добавляемой в таблицу сущностью, дабы не возникло ситуации, когда
		// всякие возможные дальнейшие модификации переданного экземпляра
		// в клиентском коде влияли на состояние сущности в нашей таблице:
		var addedEntity = new Discipline(discipline);
		disciplines.add(addedEntity);

		// Возвращаем не связный с таблицей экземпляр, но показывая что
		// он уже содержит фактический id:
		return discipline;
	}

	@Override
	public Discipline getDisciplineById(int disciplineId) {
		var dbEntity = disciplines.stream().filter(d -> d.getDisciplineId().equals(disciplineId)).findFirst();
		if (dbEntity.isPresent()) {
			return new Discipline(dbEntity.get());
		}
		return null;
	}

	@Override
	public Discipline getDisciplineByName(String disciplineName) {
		var dbEntity = disciplines.stream().filter(d -> disciplineName.equalsIgnoreCase(d.getName())).findFirst();
		if (dbEntity.isPresent()) {
			return new Discipline(dbEntity.get());
		}
		return null;
	}

	@Override
	public boolean updateDiscipline(Discipline discipline) {
		makeSureValidity(discipline);

		var dbEntity = getDisciplineById(discipline.getDisciplineId());
		if (dbEntity == null) {
			return false;
		}
		// dbEntity.copyProperties(discipline);
		copyProperties(discipline, dbEntity);
		return true;
	}

	@Override
	public List<Discipline> getAllDisciplines() {
		// Возвращаем копии!
		// TODO: Тут использован copyProperties(from, to) место копирующего
		// конструктора, возможно нам лучше будет избавиться от копирующих конструкторов
		// у всех класов сущностей
		return disciplines.stream().map(d -> copyProperties(d, new Discipline())).toList();
	}

	@Override
	public Discipline removeDiscipline(int disciplineId) {
		var dbEntity = getDisciplineById(disciplineId);
		if (dbEntity == null) {
			return null;
		}
		disciplines.remove(dbEntity);
		return dbEntity;
	}

	// aux methods:

	private void makeSureValidity(Discipline discipline) {
		if (discipline == null) {
			throw new InvalidParameterException("discipline");
		}
		if (discipline.getName() == null || discipline.getName().isBlank()) {
			throw new InvalidParameterException("discipline.name");
		}
	}

	/**
	 * Копирует значения свойств экземпляра источника в экземпляр назначения
	 * и возвращает экземпляр назначения.
	 */
	private static Discipline copyProperties(Discipline instanceFrom, Discipline instanceTo) {
		assert instanceFrom != null && instanceTo != null;
		instanceTo.setDisciplineId(instanceFrom.getDisciplineId());
		instanceTo.setName(instanceFrom.getName());
		return instanceTo;
	}

	/* TODO: Тестовый main(). Удалить потом! */
	public static class TestTestDbDisciplinesRepository {
		public static void main(String[] args) {
			var disciplinesRepository = new TestDbDisciplinesRepository();
			System.out.println(disciplinesRepository.lastId);
			System.out.println(disciplinesRepository.getDisciplineById(disciplinesRepository.lastId));
			System.out.println("=".repeat(30));
			for (var entity : disciplinesRepository.getAllDisciplines()) {
				System.out.println(entity);
			}
		}
	}
}
