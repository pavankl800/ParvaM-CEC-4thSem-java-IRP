public class TypeCasting {
    public static void main(String[] args){
        int marks= 84;
        // Implicit (Automatic) TypeCasting (Widening)
        double percentage = marks;
        // int -> double
        System.out.println("Marks: " + marks);
        System.out.println("Percentage: " + percentage);
        
        // Explicit (Manual) TypeCasting (Narrowing)
        double height = 5.75;
        // double -> int (explicitly from user)
        // (int) is the indication of explicit conversion
        int convertedHeight = (int) height;

        System.out.println("Height(double datatype): " + height);
        System.out.println("Height(int datatype): " + convertedHeight);

        // Implicit TypeCasting
        char letter = 'A';
        int asciiValue = letter;
        
        // Explicit TypeCasting
        float asciiValueFloat = (float) asciiValue;
        System.out.println("Char: " + letter + " & its ASCII Value: " + asciiValue);
        System.out.println("Char: " + letter + " & its ASCII Value in float: " + asciiValueFloat);

        int num1 = 35;
        // Integer Division
        System.out.println(num1 / 3);
        // Float Division with Explicit TypeCasting
        System.out.println((float) num1 / 3);
        System.out.println(num1 + 10);
        System.out.println((float) num1 + 10);
    }   
}