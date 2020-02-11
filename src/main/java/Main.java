import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String pathHtml = parseFile("data/code.html");
        File output = new File("data/image");

        Document doc = Jsoup.parse(pathHtml);
        Elements img = doc.getElementsByTag("img");
        ArrayList<String> src = new ArrayList<>();
        img.forEach(element -> src.add(element.absUrl("src")));
        for (String urls : src){
            try {
                downloadImage(urls, output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }
    private static String parseFile(String path){
        StringBuilder builder = new StringBuilder();

        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            lines.forEach(line -> builder.append(line + "\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
    private static void downloadImage (String src, File path) throws IOException {
        URL url = new URL(src);
        FileUtils.copyURLToFile(url, path);
    }

}
