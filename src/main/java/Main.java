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

public class Main {

    public static  String output = "data/image/";
    public static File outFile = new File("data/image");

    public static void main(String[] args) throws IOException {

        Document doc = Jsoup.connect("https://lenta.ru/").maxBodySize(0).get();
        Elements img = doc.getElementsByTag("img");
        ArrayList<String> src = new ArrayList<>();
        img.forEach(element -> src.add(element.absUrl("src")));
        src.forEach(Main::download);

    }

    public static void download(String src){
        try (InputStream in = new URL(src).openStream()) {
            String fileName = src.substring(src.lastIndexOf('/')+ 1).replace("?", "");
            ArrayList<String> imageName = checkImage();
            if (imageName.contains(fileName)){
                System.out.println("File " + fileName + " already exists");
            }else if (!fileName.contains(".jpg") && !fileName.contains(".png")){
                System.out.println("File " + fileName + " has an unknown format");
            }else{
                Files.copy(in, Paths.get(output + fileName));
                System.out.println("File " + fileName + " uploaded successfully. Directory : " + outFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<String> checkImage(){
        ArrayList<String> imageName = new ArrayList<>();
        for (File item : outFile.listFiles()){
            imageName.add(item.getName());
        }
        return imageName;
    }
}
