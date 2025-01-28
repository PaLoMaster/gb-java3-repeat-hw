package ru.khusyainov.gb.java3.hw6.entity;

import java.util.Objects;

public class Student {
    private int id;
    private String surname;
    private float score;

    public Student(String surname, float score) {
        this.surname = surname;
        this.score = score;
    }

    public Student(int id, String surname, float score) {
        this.id = id;
        this.surname = surname;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && Float.compare(student.score, score) == 0 && Objects.equals(surname, student.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, surname, score);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", Surname='" + surname + '\'' +
                ", score=" + score +
                '}';
    }
}
