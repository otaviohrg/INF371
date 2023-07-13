public class Factorial {
    public static void main(String[] args) {
        long factorial = 1;
        for(long i = 1; i<=20; i++)
            factorial *= i;
        System.out.println(factorial);
    }
}
