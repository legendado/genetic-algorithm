import java.util.Arrays;

/**
 * Array test for correct work of algorithm, because we know value of minimal commission = 1.
 * [1, 2, 3, 4, 5, 6, 7, 8, 9, 10] or [2, 1, 3, 4, 5, 6, 7, 8, 9, 10] - result.
 **/

public class Test {
    public static void main(String[] args) {
        int[] test = new int[]{2, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        GeneticAlgorithm ga = new GeneticAlgorithm(test);
        Arrays.asList(ga.run()).forEach(x -> System.out.print(Arrays.toString(x) + "\t"));
    }
}
