package model;

import java.util.List;

public class Person {

        private String name;
        private int age;
        private List<String> phones;

        public Person() {}

        public Person(String name, int age, List<String> phones) {
            this.name = name;
            this.age = age;
            this.phones = phones;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public List<String> getPhones() {
            return phones;
        }

        public void setPhones(List<String> phones) {
            this.phones = phones;
        }

        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + ", phones=" + phones + '}';
        }
    }
