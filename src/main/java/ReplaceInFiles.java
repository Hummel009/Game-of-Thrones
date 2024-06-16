import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

public class ReplaceInFiles {
	public static void main(String[] args) {
		String oldWord = "Arryn";
		String newWord = "Westerlands";
		String folderPath = "C:\\Users\\humme\\OneDrive\\Працоўны стол\\incubator";

		// Переименование файлов
		renameFiles(folderPath, oldWord, newWord);

		// Замена слов в файлах
		replaceWordsInFiles(folderPath, oldWord, newWord);

		System.out.println("Замена выполнена успешно!");
	}

	private static void renameFiles(String folderPath, CharSequence oldWord, CharSequence newWord) {
		File folder = new File(folderPath);
		for (File file : folder.listFiles()) {
			if (file.isFile() && file.getName().contains(oldWord)) {
				String newName = file.getName().replace(oldWord, newWord);
				File newFile = new File(folderPath + '/' + newName);
				file.renameTo(newFile);
			}
		}
	}

	private static void replaceWordsInFiles(String folderPath, String oldWord, String newWord) {
		File folder = new File(folderPath);
		for (File file : folder.listFiles()) {
			if (file.isFile()) {
				try {
					// Читаем содержимое файла в StringBuilder
					StringBuilder content = new StringBuilder();
					try (Scanner scanner = new Scanner(file)) {
						while (scanner.hasNextLine()) {
							String line = scanner.nextLine();
							// Заменяем слова в каждой строке
							line = line.replaceAll(oldWord, newWord);
							line = line.replaceAll(oldWord.toUpperCase(Locale.ROOT), newWord.toUpperCase(Locale.ROOT));
							line = line.replaceAll(oldWord.toLowerCase(Locale.ROOT), newWord.toLowerCase(Locale.ROOT));
							content.append(line).append("\r\n");
						}
					}

					// Перезаписываем файл с новым содержимым
					try (PrintWriter writer = new PrintWriter(file)) {
						writer.write(content.toString());
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
}