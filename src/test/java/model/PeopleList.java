package model;
import java.util.List;

public class PeopleList {

    private List<Structure> people;

    // Конструктор
    public PeopleList() {}

    // Геттеры и сеттеры
    public List<Structure> getPeople() {
        return people;
    }

    public void setPeople(List<Structure> people) {
        this.people = people;
    }


}
