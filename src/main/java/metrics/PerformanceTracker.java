package metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Класс для отслеживания ключевых метрик производительности (сравнений, обменов, обращений к массиву).
 * Используется для эмпирической валидации теоретического анализа.
 */
public class PerformanceTracker {

    // --- Метрики ---
    private static long comparisons = 0;
    private static long swaps = 0; // Для Insertion Sort это будут "логические" обмены/сдвиги
    private static long arrayAccesses = 0; // Чтения и записи

    private PerformanceTracker() {
        // Приватный конструктор для предотвращения создания экземпляров (статический класс)
    }

    // --- Методы для инкремента ---

    public static void incrementComparisons() {
        comparisons++;
    }

    /**
     * Учитывает одну операцию обмена.
     * Обмен обычно состоит из трех обращений к массиву (чтение, чтение, запись).
     */
    public static void incrementSwaps() {
        swaps++;
        incrementArrayAccesses(3); // Одно чтение, два записи (для типичного a=b, b=c, c=temp)
    }

    /**
     * В Insertion Sort часто происходит "сдвиг" (одна запись и одно чтение).
     */
    public static void incrementShift() {
        arrayAccesses += 2; // Одно чтение и одна запись (e.g., arr[j+1] = arr[j])
    }

    /**
     * Для прямого учета обращений (например, для чтения или установки одного элемента).
     * @param count Количество обращений к массиву.
     */
    public static void incrementArrayAccesses(int count) {
        arrayAccesses += count;
    }

    // --- Управление метриками ---

    public static void resetMetrics() {
        comparisons = 0;
        swaps = 0;
        arrayAccesses = 0;
    }

    // --- Экспорт данных ---

    /**
     * Экспортирует текущие собранные метрики в CSV-файл.
     * @param inputSize Размер массива, на котором проводилось тестирование.
     * @param dataType Тип данных (RANDOM, SORTED, REVERSE_SORTED и т.д.).
     * @param timeNano Фактическое время выполнения в наносекундах.
     */
    public static void exportToCSV(int inputSize, String dataType, long timeNano) {
        final String fileName = "performance_data.csv";
        final String dataDir = "performance-data"; // Создадим отдельную папку

        try {
            // Создание папки, если она не существует (в соответствии с docs/performance-plots/)
            Files.createDirectories(Paths.get(dataDir));
            String filePath = dataDir + "/" + fileName;

            // Проверяем, нужно ли записывать заголовок
            boolean fileExists = Files.exists(Paths.get(filePath)) && Files.size(Paths.get(filePath)) > 0;

            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
                if (!fileExists) {
                    // Заголовок CSV
                    writer.println("InputSize,DataType,Time_ns,Comparisons,Swaps,ArrayAccesses");
                }

                // Запись данных
                writer.printf("%d,%s,%d,%d,%d,%d\n",
                        inputSize,
                        dataType,
                        timeNano,
                        comparisons,
                        swaps,
                        arrayAccesses
                );
            }
            // В консоли можно выводить информацию о сохранении
            // System.out.println("Metrics saved to " + filePath);

        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }
}