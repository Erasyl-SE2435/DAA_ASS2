package algorithms;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Комплексный набор юнит-тестов для проверки корректности InsertionSort.
 * Покрывает краевые случаи, случайные данные и кросс-валидацию.
 */
class InsertionSortTest {

    // --- 1. Тесты Краевых Случаев (Edge Cases) ---

    @Test
    void testEmptyArray() {
        int[] input = {};
        InsertionSort.sort(input);
        // Массив должен остаться пустым и не выбросить ошибку
        assertTrue(input.length == 0);
    }

    @Test
    void testNullArray() {
        int[] input = null;
        // Проверяем, что алгоритм обрабатывает null-вход без исключений
        assertDoesNotThrow(() -> InsertionSort.sort(input));
    }

    @Test
    void testSingleElementArray() {
        int[] input = {42};
        InsertionSort.sort(input);
        assertArrayEquals(new int[]{42}, input);
    }

    @Test
    void testArrayWithDuplicates() {
        int[] input = {5, 2, 8, 2, 5, 1};
        int[] expected = {1, 2, 2, 5, 5, 8};
        InsertionSort.sort(input);
        assertArrayEquals(expected, input);
    }

    // --- 2. Тесты Производительности (Best/Worst Cases) ---

    @Test
    void testAlreadySortedArray_BestCase() {
        int[] input = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] expected = Arrays.copyOf(input, input.length);
        InsertionSort.sort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    void testReverseSortedArray_WorstCase() {
        int[] input = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        InsertionSort.sort(input);
        assertArrayEquals(expected, input);
    }

    // --- 3. Property-Based Testing (Проверка свойств на случайных данных) ---

    @Test
    void testSortingCorrectness_RandomInputs() {
        final int NUM_TESTS = 100;
        final int MAX_SIZE = 1000;
        Random random = new Random();

        for (int i = 0; i < NUM_TESTS; i++) {
            int size = random.nextInt(MAX_SIZE) + 1; // Размер от 1 до MAX_SIZE
            int[] input = random.ints(size, -1000, 1000).toArray();
            int[] expected = Arrays.copyOf(input, input.length);

            // Сортировка нашим алгоритмом
            InsertionSort.sort(input);

            // Кросс-валидация: Сравниваем с эталонным результатом Java
            Arrays.sort(expected); // [cite: 98]

            // Проверяем, что результат нашего алгоритма соответствует эталонному
            assertArrayEquals(expected, input, "Failed on random array of size " + size);
        }
    }
}