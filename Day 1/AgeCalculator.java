public class AgeCalculator {
    public static void main(String[] args){
        int currentYear = 2026;
        int birthYear = 2001;

        int age = currentYear - birthYear;

        System.out.println("Birth Year: " + birthYear);
        System.out.println("Age: " + age + "(in Years)");
        
        int ageInMonth = age * 12;
        int ageInDays = age * 365;
        int ageInWeeks = age * 52;
        
        System.out.println("Age: " + ageInMonth + "(in Months)");    
        System.out.println("Age: " + ageInDays + "(in Days)");    
        System.out.println("Age: " + ageInWeeks + "(in Weeks)");    
    }   
}