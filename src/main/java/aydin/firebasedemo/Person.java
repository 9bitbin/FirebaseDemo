//package aydin.firebasedemo;
//
//public class Person {
//    private String name;
//    private int age;
//
//    public Person(String name, int age) {
//        this.name = name;
//        this.age = age;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//}


//Himals Updated Code:
package aydin.firebasedemo;

public class Person {
    private String name;
    private int age;
    private String phoneNumber;  // Added phone number field

    // Constructor to initialize the Person object with name, age, and phone number
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    // Getter method for the name field
    public String getName() {
        return name;
    }

    // Setter method for the name field
    public void setName(String name) {
        this.name = name;
    }

    // Getter method for the age field
    public int getAge() {
        return age;
    }

    // Setter method for the age field
    public void setAge(int age) {
        this.age = age;
    }

    // Getter method for the phone number field
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Setter method for the phone number field
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

// Note: You need to update the part of your code that interacts with Firebase to include the phoneNumber field as well.
