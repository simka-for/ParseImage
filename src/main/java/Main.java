import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static  String output = "data/image/";
    public static File outFile = new File("data/image");

    public static void main(String[] args) {

        String pathHtml = parseFile();

        Document doc = Jsoup.parse(pathHtml);
        Elements img = doc.getElementsByTag("img");
        ArrayList<String> src = new ArrayList<>();
        img.forEach(element -> src.add(element.absUrl("src")));
        src.forEach(Main::download);

    }
    private static String parseFile(){
        StringBuilder builder = new StringBuilder();

        try {
            List<String> lines = Files.readAllLines(Paths.get("data/code.html"));
            lines.forEach(line -> builder.append(line + "\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
    public static void download(String src){
        try (InputStream in = new URL(src).openStream()) {
            String fileName = src.substring(src.lastIndexOf('/'));
            Files.copy(in, Paths.get(output + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
