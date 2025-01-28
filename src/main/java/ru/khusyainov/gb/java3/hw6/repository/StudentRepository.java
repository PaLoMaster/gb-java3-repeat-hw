package ru.khusyainov.gb.java3.hw6.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.khusyainov.gb.java3.hw6.service.DDL;
import ru.khusyainov.gb.java3.hw6.service.DML;
import ru.khusyainov.gb.java3.hw6.entity.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static ru.khusyainov.gb.java3.hw6.service.ErrorLogHelper.logException;

public class StudentRepository implements Repository<Student> {
    private static final Logger LOGGER = LogManager.getLogger(StudentRepository.class);
    private final String TABLE = "students";
    private final int[] oneUpdate = new int[]{1};

    @Override
    public void createRepository() {
        DDL.createTable(TABLE + " (" +
                "id INTEGER NOT NULL PRIMARY KEY," +
                "surname text(30) NOT NULL," +
                "score REAL)");
    }

    public void fillRepositoryByRandomData(int count) {
        String[] data = new String[count];
        for (int i = 1; i <= count; i++) {
            data[i - 1] = String.format(Locale.US, "Random surname %d, %.1f", i, Math.random() * 3 + 2);
        }
        LOGGER.info("Fill result: {}", Arrays.toString(DML.insert(TABLE, "surname, score", data)));
    }

    @Override
    public void clearRepository() {
        DDL.clearTable(TABLE);
    }

    @Override
    public void deleteRepository() {
        DDL.dropTable(TABLE);
    }

    @Override
    public boolean save(Student item) {
        boolean update = Arrays.equals(oneUpdate, DML.insert(TABLE, "surname, score",
                item.getSurname() + ", " + item.getScore()));
        if (update) {
            try (ResultSet rs = DML.select(TABLE, "*", "ORDER BY id DESC LIMIT 1")) {
                if (rs.next() && rs.getString(2) != null) {
                    item.setId(rs.getInt(1));
                    return true;
                }
            } catch (SQLException e) {
                logException(e, item.toString());
            }
        }
        return false;
    }

    @Override
    public boolean update(Student item) {
        int result = DML.update(TABLE, "id = " + item.getId(),
                String.format(Locale.US, "surname = '%s', score = %f", item.getSurname(), item.getScore()));
        return result == 1;
    }

    private Student getStudentFromResultSet(ResultSet rs) throws SQLException {
        return new Student(rs.getInt(1), rs.getString(2), rs.getFloat(3));
    }

    @Override
    public Student get(int id) {
        Student student = null;
        try (ResultSet rs = DML.select(TABLE, "*", "WHERE id = " + id)) {
            if (rs.next() && rs.getString(2) != null) {
                student = getStudentFromResultSet(rs);
            }
        } catch (SQLException e) {
            logException(e);
        }
        return student;
    }

    @Override
    public Student delete(int id) {
        Student student = get(id);
        return delete(student) ? student : null;
    }

    @Override
    public boolean delete(Student item) {
        int result = 0;
        if (item != null && item.getSurname() != null) {
            result = DML.delete(TABLE, "id = " + item.getId());
        }
        return result == 1;
    }

    public int countAll() {
        int count = 0;
        try (ResultSet rs = DML.select(TABLE, "count(*)", "")) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            logException(e);
        }
        return count;
    }

    @Override
    public List<Student> getAll() {
        List<Student> students = new ArrayList<>(countAll());
        try (ResultSet rs = DML.select(TABLE, "*", "")) {
            while (rs.next()) {
                students.add(getStudentFromResultSet(rs));
            }
        } catch (SQLException e) {
            logException(e);
        }
        return students;
    }
}
