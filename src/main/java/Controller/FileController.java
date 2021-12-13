package Controller;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * read and write from txt file
 *
 * @author Cheo Kai Wen
 * @version 1.0
 * @since 9/12/2021
 */
public class FileController {

    /**
     * write score into score.txt file
     * @param score score
     * @throws IOException
     */
    public static void appendToFile(int score) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/score.txt", true));
        writer.write(score + "\n");

        writer.close();
    }

    /**
     * read score from score.txt file
     * @return score in integer array
     * @throws IOException
     */
    public static Integer[] readFromFile() throws IOException {
        Stream<String> stream = Files.lines(Paths.get("src/main/resources/score.txt"));
        return stream.map(Integer::valueOf).sorted().toArray(Integer[]::new);
    }

    /**
     * write name into name.txt file
     * @param name name of player
     * @throws IOException
     */
    public static void appendToFileName(String name) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/name.txt", true));
        writer.write(name + "\n");

        writer.close();
    }

    /**
     * read name from name.txt file
     * @return name in string array
     * @throws IOException
     */
    public static String[] readFromFileName() throws IOException {
        FileReader fileReader = new FileReader("src/main/resources/name.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        List<String> lines = new ArrayList<String>();
        String line = null;

        while ((line = bufferedReader.readLine()) != null)
            lines.add(line);

        bufferedReader.close();
        return lines.toArray(new String[lines.size()]);
    }
}