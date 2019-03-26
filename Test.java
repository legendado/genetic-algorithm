import java.util.Arrays;

/**
 * Массив test чисто для проверки работы алгоритма, т.к. мы знаем заранее результат минимальной комиссии = 1.
 * [1, 2, 3, 4, 5, 6, 7, 8, 9, 10] или [2, 1, 3, 4, 5, 6, 7, 8, 9, 10] - ответ.
 * На большом кол-ве итераций работает медленно.
 **/

public class Test {
    public static void main(String[] args) {
        int[] test = new int[]{2, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        GeneticAlgorithm ga = new GeneticAlgorithm(test);
        Arrays.asList(ga.run()).forEach(x -> System.out.print(Arrays.toString(x) + "\t"));
    }
}
