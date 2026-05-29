public class LoopsInJava {
    public static void main(String[] args){
        int limit = 10;
        int i=0;

        // If you do not the number of steps to complete the task, condition will break the loop
        System.out.println("Numbers from 0 to 10:");
        while(i <= limit){
            System.out.println(i);
            i++;
        }
        
        System.out.println("Even Numbers from 0 to 10:");
        int j = 0;
        while(j <= limit) {
            if(j % 2 == 0){
                System.out.println(j);
            }
            j++;
        }

        // Do-while Loop It will run the loop atleast once before exiting and continues until the condition becomes false

        // The condition is false but it will print the k value atleast 1 time
        int k = 10;
        do {
            System.out.println("The value of k: " + k);
        } while(k > 20);
        
        int l = 25;
        do {
            System.out.println("The value of l: " + l);
            l--;
        } while(l > 20);

        // For Loop
        // When the number of steps already known
        // Multiple of 5
        for(int m = 1; m <= 10; m++){
            System.out.println("5 x " + m + ": " + (5 * m));
        }
    }    
}