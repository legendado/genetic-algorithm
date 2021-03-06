import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.IntStream;

class GeneticAlgorithm {
    private static int COUNT = 10; // Count of payment points
    private int currentGeneration = 0;
    private Random random = new Random();

    private int generationCount = 1000; // Count of generation
    private int individualCount = 100; // Count of genom on 1 generation

    private int[][] genomListParents;
    private int[][] genomListOffsprings;
    private int[] commission;

    public GeneticAlgorithm(int[] commission) {
        this.commission = commission;
    }

    public int[] run() {
        this.genomListParents = new int[this.individualCount][];
        this.genomListOffsprings = new int[this.individualCount][];

        this.generateFirstGeneration();

        while (this.currentGeneration < this.generationCount) {
            this.selection();
            this.crossing();
            this.mutation();

            int[][] tmp = this.genomListParents;
            this.genomListParents = this.genomListOffsprings;
            this.genomListOffsprings = tmp;

            this.currentGeneration++;
        }

        Arrays.sort(this.genomListParents, Comparator.comparing(this::setFitnessFunctionResult));
        return this.genomListParents[0];
    }

    // Create first generation
    private void generateFirstGeneration() {
        for (int i = 0; i < this.individualCount; i++) {
            this.genomListParents[i] = this.generateGenom();
        }
    }

    // Create random genom
    private int[] generateGenom() {
        int[] result = new int[COUNT];

        for (int i = 0; i < COUNT; i++) {
            int value = random.nextInt(COUNT) + 1;
            boolean contains = isContains(result, value);
            while (contains) {
                value = random.nextInt(COUNT) + 1;
                contains = isContains(result, value);
            }
            result[i] = value;
        }
        return result;
    }

    // Check on unique
    private boolean isContains(int[] array, int value) {
        return IntStream.of(array).anyMatch(x -> x == value);
    }

    // Calculate fitness-function
    private int setFitnessFunctionResult(int[] genom) {
        int result = 0;
        for (int i = 0; i < COUNT; i++) {
            result += commission[i] - genom[i] >= 0 ? commission[i] - genom[i] : 0;
        }
        return result;
    }

    // Selection
    private void selection() {
        Arrays.sort(genomListParents, Comparator.comparing(this::setFitnessFunctionResult));        
        for (int i = 0; i < this.individualCount / 2; i++) {
            this.genomListOffsprings[i] = genomListParents[i].clone();
        }
    }

    // Crossing of ranking method
    private void crossing() {
        int i = individualCount / 2;
        while (i < individualCount) {
            int index1 = random.nextInt(this.individualCount / 2);
            int index2 = random.nextInt(this.individualCount / 2);
            int[] offset = cross(this.genomListOffsprings[index1], this.genomListOffsprings[index2]);
            if (!isAdaptability(offset)) {
                this.genomListOffsprings[i] = offset;
                i++;
            }
        }
    }

    // true, if genom is unique
    private boolean isAdaptability(int[] genom) {
        return (int) IntStream.of(genom).distinct().count() < genom.length;
    }

    // Crossing 2 genom
    private int[] cross(int[] genom1, int[] genom2) {
        int[] offset = new int[COUNT];
        System.arraycopy(genom1, 0, offset, 0, COUNT / 2);
        if (COUNT - COUNT / 2 >= 0) System.arraycopy(genom2, COUNT / 2, offset, COUNT / 2, COUNT - COUNT / 2);
        return offset;
    }

    // Mutation
    private void mutation() {
        for (int[] genom : this.genomListOffsprings) {
            double MUTATION_PERCENT = 0.02; // Процент мутации
            if (random.nextDouble() < MUTATION_PERCENT) {
                mutate(genom);
            }
        }
    }

    // Mutate of 1 genom
    private void mutate(int[] genom) {
        int index1 = this.random.nextInt(COUNT);
        int index2 = this.random.nextInt(COUNT);
        int swapMask = genom[index1] ^ genom[index2];

        genom[index1] ^= swapMask;
        genom[index2] ^= swapMask;
    }    
     
    public void setGenerationCount(int generationCount) {
        this.generationCount = generationCount;
    }

    public void setIndividualCount(int individualCount) throws Exception {
        if (individualCount < 2) throw new Exception("Individual count must be > 1");
        this.individualCount = individualCount;
    }
}
