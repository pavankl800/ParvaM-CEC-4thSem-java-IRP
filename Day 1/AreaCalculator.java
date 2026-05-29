public class AreaCalculator {
    public static void main(String[] args){
        int length = 20;
        int breadth = 32;
        float radius = 23.7f;

        int areaOfSquare = length * length;
        int areaOfRectangle = length * breadth;
        double areaOfCircleV1 = Math.PI * radius * radius;
        // Math.PI is a constant used by Math Library
        // Math.pow() is a math module method
        double areaOfCircleV2 = Math.PI * Math.pow(radius, 2);
        double circumference = 2 * Math.PI * radius;
        
        System.out.println("Area of Square: " + areaOfSquare);
        System.out.println("Area of Rectangle: " + areaOfRectangle);
        System.out.println("Area of Circle (V1): " + areaOfCircleV1);
        System.out.println("Area of Circle (V2): " + areaOfCircleV2);
        System.out.println("Circumference of Circle: " + circumference);
    }   
}