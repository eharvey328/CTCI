import java.math.BigInteger;

public class MathFunctions {
    public static void main(String[] args) {

    }

    public static boolean isPrime(int n) {
        if (n < 2) return false;

        int sqrt = (int) Math.sqrt(n);

        for (int i = 2; i <= sqrt; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public static void fibonacci(int n) {
        java.math.BigInteger[] fib = new BigInteger[n + 1];
        fib[0] = BigInteger.valueOf(0);
        fib[1] = BigInteger.valueOf(1);
        for (int i = 2; i <= n; i++) {
            fib[i] = fib[i - 1].add(fib[i - 2]);
        }

        for(BigInteger value : fib) {
            System.out.println(value);
        }
    }

    //7.3
    //check if two lines will intersect
    public class Line {
        double epsilon = 0.000001;
        double slope;
        double yInt;

        public Line(double s, double y) {
            slope = s;
            yInt = y;
        }

        public boolean intersect(Line line2) {
            return Math.abs(slope - line2.slope) > epsilon || Math.abs(yInt - line2.yInt) < epsilon;
        }
    }

    //7.4
    //Math operations only using addition
    public static int subtract(int a, int b) {
        return a + negate(b);
    }

    public static int negate(int x) {
        int neg = 0;
        int opposite = x < 0 ? 1 : -1;
        while (x != 0) {
            neg += opposite;
            x += opposite;
        }
        return neg;
    }

    public static int multiply(int a, int b) {
        if (a < b) return multiply(b, a); //faster if a > b

        int sum = 0;
        for (int i = absolute(b); i > 0; i--) {
            sum += a;
        }

        if (b < 0) sum = negate(sum);

        return sum;
    }

    public static int absolute(int x) {
        if (x < 0) return negate(x);
        else return x;
    }

    public int divide(int a, int b) throws java.lang.ArithmeticException {
        if (b == 0) {
            throw new java.lang.ArithmeticException("Divide by zero");
        }

        int absA = absolute(a);
        int absB = absolute(b);

        int product = 0;
        int x = 0;

        while (product + absB <= absA) {
            product += absB;
            x++;
        }

        if ((a < 0 && b < 0) || (a > 0 && b > 0)) return x;
        else return negate(x);
    }

    public static java.util.List<Integer> primeFactors(int numbers) {
        int n = numbers;
        java.util.List<Integer> factors = new java.util.ArrayList<Integer>();
        for (int i = 2; i <= n / i; i++) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }
        if (n > 1) {
            factors.add(n);
        }
        return factors;
    }
}
