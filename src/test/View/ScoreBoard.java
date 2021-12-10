package test.View;

import test.Controller.FileController;
import test.Controller.GameFrame;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class ScoreBoard {
    private static final String NAME = "Score Board";

    private int scoresToPrint = 5;

    private String displayScores() throws IOException {
        Integer[] scores = FileController.readFromFile();
        Arrays.sort(scores, Collections.reverseOrder());
        return Arrays.toString(Arrays.stream(scores).limit(scoresToPrint).toArray());


    }
}

/*package test.Controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileController {

    public static void appendToFile(int score) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("Filetxt/HighScore.txt", true));
        writer.write(score + "\n");

        writer.close();
    }

    public static Integer[] readFromFile() throws IOException {
        Stream<String> stream = Files.lines(Paths.get("Filetxt/HighScore.txt"));
        return stream.map(x -> Integer.valueOf(x)).sorted().toArray(Integer[]::new);
    }


}

    private String displayScores() throws IOException{
        Integer[] scores = FileController.readFromFile();
        Arrays.sort(scores, Collections.reverseOrder());
        return Arrays.toString(Arrays.stream(scores).limit(scoresToPrint).toArray());
    }

 */