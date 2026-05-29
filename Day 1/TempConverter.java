public class TempConverter {
    public static void main(String[] args){
        float tempInC = 33.5f;
        float tempInF = 90.23f;

        float toCelsius = (tempInF - 32) * (5/9);
        float toFahrenheit = (tempInC * (9/5)) + 32;

        System.out.println(tempInC + "°C Temperature in °F: " + toFahrenheit);
        System.out.println(tempInF + "°F Temperature in °C: " + toCelsius);
        System.out.println(tempInC + "°C Temperature in Kelvin: " + (tempInC + 273.15));
        System.out.println(tempInF + "°F Temperature in Kelvin: " + ((tempInF - 32) / 1.8) + 273.15);
    }   
}