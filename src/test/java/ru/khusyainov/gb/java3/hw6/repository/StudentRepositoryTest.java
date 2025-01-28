package ru.khusyainov.gb.java3.hw6.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import ru.khusyainov.gb.java3.hw6.entity.Student;
import ru.khusyainov.gb.java3.hw6.service.ConMan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.khusyainov.gb.java3.hw6.service.ErrorLogHelper.logException;
import static ru.khusyainov.gb.java3.hw6.service.TestHelper.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudentRepositoryTest {
    private static final Logger LOGGER = LogManager.getLogger(StudentRepository.class);
    private static final StudentRepository repository = new StudentRepository();
    private Student testStudent;

    @BeforeAll
    void beforeAll() {
        renameTableFile();
    }

    @Test
    @Order(1)
    void createRepository() {
        try (Statement st = ConMan.getConnection().createStatement()) {
            st.executeUpdate("DROP TABLE IF EXISTS " + TABLE + ";");
            repository.createRepository();
            assertTrue(tableExists());
        } catch (SQLException e) {
            logException(e);
        }
    }

    @Test
    @Order(2)
    void fillRepositoryByRandomData() {
        try (Statement st = ConMan.getConnection().createStatement()) {
            repository.fillRepositoryByRandomData(BATCH_COUNT);
            ResultSet rs = st.executeQuery("SELECT count(*) FROM " + TABLE + ";");
            assertTrue(rs.next() && BATCH_COUNT == rs.getInt(1));
        } catch (SQLException e) {
            logException(e);
            fail();
        }
    }

    @Test
    @Order(11)
    void clearRepository() {
        repository.clearRepository();
        try {
            assertEquals(0, countTableData());
        } catch (SQLException e) {
            logException(e);
            fail();
        }
    }

    @Test
    @Order(12)
    void deleteRepository() {
        try {
            ConMan.closeConnection();
            repository.deleteRepository();
            assertFalse(tableExists());
        } catch (SQLException e) {
            logException(e);
            fail();
        }
    }

    private Student getStudentFromResultSet(ResultSet rs) throws SQLException {
        return new Student(rs.getInt(1), rs.getString(2), rs.getFloat(3));
    }

    Student getStudentFromDB(int id) {
        Student student = null;
        try (Statement st = ConMan.getConnection().createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM " + TABLE + " WHERE id = " + id + ";")) {
            if (rs.next()) {
                student = getStudentFromResultSet(rs);
            }
        } catch (SQLException e) {
            logException(e);
        }
        LOGGER.info("Student by id {}: {}.", id, student);
        return student;
    }

    @Test
    @Order(7)
    void saveExisted() {
        testStudent.setSurname(getTestCurrentTime());
        assertTrue(repository.save(testStudent));
        assertEquals(testStudent, getStudentFromDB(testStudent.getId()));
    }

    @Test
    @Order(9)
    void saveNew() {
        testStudent.setId(BATCH_COUNT + 1);
        assertTrue(repository.save(testStudent));
        assertEquals(testStudent, getStudentFromDB(testStudent.getId()));
    }

    @Test
    @Order(6)
    void update() {
        testStudent.setSurname(getTestCurrentTime());
        LOGGER.info(testStudent);
        assertTrue(repository.update(testStudent));
        assertEquals(testStudent, getStudentFromDB(testStudent.getId()));
    }

    @Test
    @Order(5)
    void get() {
        testStudent = getStudentFromDB(getRandomTestId());
        assertEquals(testStudent, repository.get(testStudent.getId()));
        LOGGER.info(testStudent);
    }

    @Test
    @Order(8)
    void deleteById() {
        assertEquals(testStudent, repository.delete(testStudent.getId()));
        assertNull(getStudentFromDB(testStudent.getId()));
        LOGGER.info("passed");
    }

    @Test
    @Order(10)
    void deleteByItem() {
        assertTrue(repository.delete(testStudent));
        assertNull(getStudentFromDB(testStudent.getId()));
        LOGGER.info("passed");
    }

    @Test
    @Order(3)
    void countAll() {
        int actual = repository.countAll();
        assertEquals(BATCH_COUNT, actual);
        LOGGER.info("Expected: {}, actual: {}.", BATCH_COUNT, actual);
    }

    @Test
    @Order(4)
    void getAll() {
        try (Statement st = ConMan.getConnection().createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM " + TABLE + ";")) {
            List<Student> expected = new ArrayList<>(BATCH_COUNT);
            while (rs.next()) {
                expected.add(getStudentFromResultSet(rs));
            }
            List<Student> actual = repository.getAll();
            LOGGER.info("Expected: {}.", expected.toString());
            LOGGER.info("Actual:   {}.", actual.toString());
            assertEquals(expected, repository.getAll());
        } catch (SQLException e) {
            logException(e);
        }
    }

    @AfterAll
    void afterAll() {
        restoreTableFile();
    }
}