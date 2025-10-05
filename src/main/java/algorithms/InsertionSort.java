package algorithms;

import metrics.PerformanceTracker;
import java.util.Arrays;

/**
 * Реализация сортировки вставками (Insertion Sort) с оптимизацией
 * для почти отсортированных данных (используя бинарный поиск).
 */
public class InsertionSort {

    /**
     * Сортирует массив с использованием Insertion Sort.
     * @param arr Массив целых чисел для сортировки.
     */
    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        // Внешний цикл проходит по всем элементам, начиная со второго (индекс 1)
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i]; // Текущий элемент для вставки
            PerformanceTracker.incrementArrayAccesses(1); // Чтение 'arr[i]'

            int j = i - 1;

            /*
             * Оптимизация для почти отсортированных данных (используя бинарный поиск):
             * Находим позицию, куда нужно вставить key, используя бинарный поиск,
             * чтобы уменьшить количество сравнений в цикле.
             */
            int insertionIndex = binarySearch(arr, key, 0, i);

            // В этот момент insertionIndex - это место, куда должен быть вставлен 'key'.
            // Теперь нужно сдвинуть все элементы на одну позицию вправо.

            // Внутренний цикл: сдвигаем элементы, которые больше 'key', вправо
            while (j >= insertionIndex) {
                // Это сдвиг, а не обмен (swap). Считаем 2 обращения (чтение и запись)
                arr[j + 1] = arr[j];
                PerformanceTracker.incrementShift();

                j--;
            }

            // Вставляем элемент на найденную позицию
            arr[j + 1] = key;
            PerformanceTracker.incrementArrayAccesses(1); // Запись 'key'
        }
    }

    /**
     * Вспомогательный метод: Бинарный поиск для нахождения позиции вставки.
     * Он находит индекс первого элемента, который больше или равен 'key'.
     * @param arr Массив (или его отсортированная часть) для поиска.
     * @param key Значение для вставки.
     * @param low Начало диапазона поиска.
     * @param high Конец диапазона поиска (исключительно).
     * @return Индекс, куда должен быть вставлен key.
     */
    private static int binarySearch(int[] arr, int key, int low, int high) {
        int comparisonsMade = 0;

        while (low < high) {
            int mid = low + (high - low) / 2;

            // Считаем сравнение
            comparisonsMade++;

            if (arr[mid] < key) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        // Добавляем все сравнения, сделанные в бинарном поиске, к трекеру
        // Это обеспечивает корректный подсчет метрик
        for (int k = 0; k < comparisonsMade; k++) {
            PerformanceTracker.incrementComparisons();
        }

        // Возвращаем позицию вставки
        return low;
    }
}