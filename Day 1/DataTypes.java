public class DataTypes {
    public static void main(String args[]){
        int age = 24;
        float height = 5.2f;
        String name = "Akshay Rao";
        char initial = 'J';
        boolean isStudent = false;
        String company = "ParvaM";

        System.out.println("Full Name: " + name + "." + initial);
        System.out.println("Age: " + age);
        System.out.println("Height: " + height);
        // println() will have a line break
        System.out.println("Is the person student?" + isStudent);
        // print() will continue in same line
        System.out.print("Working at " + company);
    }   
}