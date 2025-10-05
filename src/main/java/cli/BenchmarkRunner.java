package cli;

import algorithms.InsertionSort;
import metrics.PerformanceTracker;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BenchmarkRunner {

    // Размеры входных данных, требуемые заданием (10^2 до 10^5)
    private static final int[] INPUT_SIZES = {100, 1000, 10000, 100000};
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        System.out.println("--- Starting Insertion Sort Benchmarks ---");

        // Запуск тестов для каждого типа входных данных
        for (int size : INPUT_SIZES) {
            runBenchmark(size, "RANDOM");
            runBenchmark(size, "SORTED");
            runBenchmark(size, "REVERSE_SORTED");
            runBenchmark(size, "NEARLY_SORTED");
        }

        System.out.println("--- Benchmarks Complete. Data saved to performance-data/performance_data.csv ---");
    }

    private static void runBenchmark(int size, String type) {
        System.out.printf("Running %s test for N=%d...\n", type, size);

        // 1. Создание входного массива
        int[] originalArray = generateArray(size, type);
        int[] arrayToSort = Arrays.copyOf(originalArray, size); // Копируем для сортировки

        // 2. Сброс метрик
        PerformanceTracker.resetMetrics();

        // 3. Измерение времени и запуск сортировки
        long startTime = System.nanoTime();
        InsertionSort.sort(arrayToSort);
        long endTime = System.nanoTime();
        long durationNano = endTime - startTime;

        // Проверка корректности (опционально, но полезно)
        if (!isSorted(arrayToSort)) {
            System.err.printf("ERROR: Array of size %d and type %s was NOT correctly sorted!\n", size, type);
        }

        // 4. Экспорт результатов
        PerformanceTracker.exportToCSV(size, type, durationNano);
    }

    private static int[] generateArray(int size, String type) {
        int[] arr = new int[size];

        switch (type) {
            case "RANDOM":
                for (int i = 0; i < size; i++) {
                    arr[i] = RANDOM.nextInt(size * 10);
                }
                break;
            case "SORTED":
                for (int i = 0; i < size; i++) {
                    arr[i] = i;
                }
                break;
            case "REVERSE_SORTED":
                for (int i = 0; i < size; i++) {
                    arr[i] = size - i;
                }
                break;
            case "NEARLY_SORTED":
                for (int i = 0; i < size; i++) {
                    arr[i] = i;
                }
                // Случайным образом меняем несколько элементов (например, 1% от размера)
                for (int i = 0; i < size / 100; i++) {
                    int idx1 = RANDOM.nextInt(size);
                    int idx2 = RANDOM.nextInt(size);
                    int temp = arr[idx1];
                    arr[idx1] = arr[idx2];
                    arr[idx2] = temp;
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown array type: " + type);
        }
        return arr;
    }

    // Вспомогательная функция для проверки корректности
    private static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
}