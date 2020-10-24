package by.Andrey.jis3telegram.htmlfrom;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class fromHtmlWords {

    public static void getWordsFromHtml() throws IOException {
        Document doc = Jsoup.connect("https://dictionary.cambridge.org/dictionary/english/get-off").get();
//        log.info(doc.title());
//        Elements newsHeadlines = doc.select("#mp-itn b a");
//        for (Element headline : newsHeadlines) {
//            log.info(String.format("%s\n\t%s",
//                    headline.attr("title"), headline.absUrl("href")));
//        }
//
//        Element element = doc.getElementById("mp-tfa");
//        Elements paragraphs = element.getElementsByTag("p");
//        for (Element paragraph : paragraphs) {
//            log.info(paragraph.text());

        Elements element = doc.getElementsByClass("def ddef_d db");
       // System.out.println(element);
        String fileTowrite = Jsoup.parse(element.toString()).text();
        Elements element1 = doc.getElementsByClass("examp dexamp");
        String fileToWrite2 = Jsoup.parse(element1.toString()).text();
//        for(Element element1: element){
//            Elements result = element1.getElementsByTag("a");
//            System.out.println(element1.childNode(0));
//            for (Element element2: result){
//                System.out.println(element2.childNode(0));
//
//            }

    }
}
