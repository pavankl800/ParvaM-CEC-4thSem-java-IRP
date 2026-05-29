// Multi-level Inheritance
// Person -> Student -> Employee
// Base -> Derived(1) -> Derived(2)
// Level1 -> Level2 -> Level3

// Level 3(Derived Class) derived from Student(Level 2)
// Student -> Employee
class Employee extends Student {
    String company;
    String role;
    float experience;

    // setter: setVariable()
    void setCompany(String company) {
        this.company = company;
    }

    // setter: getVariable()
    String getCompany() {
        return company;
    }

    void setCompanyDetails(String role, float experience) {
        this.role = role;
        this.experience = experience;
    }

    void getCompanyDetails() {
        String companyName = getCompany();
        System.out.println("The person is working at " + companyName + " as a " + " and has an experience of "
                + experience + " years");
    }
}

// Level 2(Base Class) derived from Person(Level 1)
// Person -> Student
class Student extends Person {
    String college;
    String branch;

    void setStudentInfo(String college, String branch) {
        this.college = college;
        this.branch = branch;
    }

    void getStudentInfo() {
        System.out.println("The person has graduated from " + branch + " department from " + college + " college");
    }
}

// Level 1(Base Class)
class Person {
    String name;
    int age;

    void setPersonInfo(String name, int age) {
        this.name = name;
        this.age = age;
    }

    void getPersonInfo() {
        System.out.println("The name of the person is " + name + " and he is " + age + " years old.");
    }
}

// Herirachical Inheritance
// Base Class
class College {
    String college;
    String location;

    void setCollegeInfo(String college, String location) {
        this.college = college;
        this.location = location;
    }

    void getCollegeInfo() {
        System.out.println("The name of the college is " + college + " and it is located at " + location);
    }
}

// College -> Stream
// Derived Class 1
class Stream extends College {
    String streamType;
    int duration;

    void setStreamInfo(String streamType, int duration) {
        this.streamType = streamType;
        this.duration = duration;
    }

    void getStreamInfo() {
        System.out.println("The college consists of " + streamType + " and it takes " + duration
                + " years to complete the degree.");
    }
}

// College -> Branch
// Derived Class 2
class Branch extends College {
    String branch;
    int seatLimit;

    void setBranchInfo(String branch, int seatLimit) {
        this.branch = branch;
        this.seatLimit = seatLimit;
    }

    void getBranchInfo() {
        System.out.println("The College offers degree in " + branch + " branch and it includes " + seatLimit
                + " seats for enrollment.");
    }
}

public class InheritanceTypes {
    public static void main(String[] args) {
        Employee emp = new Employee();
        emp.setCompany("ParvaM");
        emp.setCompanyDetails("Full Stack Web Developer & Technical Trainer", 3.8f);
        emp.setStudentInfo("City Engineering", "Computer Science & Engineering");
        emp.setPersonInfo("Akshay Rao", 24);
        emp.getPersonInfo();
        emp.getStudentInfo();
        emp.getCompanyDetails();

        Branch branch = new Branch();
        branch.setBranchInfo("Computer Science & Engineering", 120);
        branch.setCollegeInfo("City Engineering College", "Doddakallasandra");
        branch.getCollegeInfo();
        branch.getBranchInfo();

        Stream stream = new Stream();            stream.setStreamInfo("Engineering", 4);
        stream.setCollegeInfo("NIT Surathkal", "Mangalore");
        stream.getCollegeInfo();
        stream.getStreamInfo();
    }
}