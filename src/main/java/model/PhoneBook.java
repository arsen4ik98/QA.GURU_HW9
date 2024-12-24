package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PhoneBook {
    @JsonProperty("people")
    private List<Person> people;

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    @Override
    public String toString() {
        return "PhoneBook{" + "people=" + people + '}';
    }
}
