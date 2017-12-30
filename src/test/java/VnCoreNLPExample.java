import vn.pipeline.*;
import java.io.*;

public class VnCoreNLPExample {
    public static void main(String[] args) throws IOException {
        String str = "Bà Ngọc Lan đang đến thăm Hà Nội. " +
                "Sau đó, bà Lan sẽ quay về Hồ Chí Minh.";
        PrintStream outputPrinter = new PrintStream("output.txt");
        // wseg for Word Segmentation
        // pos for Part-of-speech Tagging
        // ner for Name Entity Recognition
        // parse for Dependency Parsing
        String[] annotators = {"wseg", "pos", "ner", "parse"};
        VnCoreNLP pipeline = new VnCoreNLP(annotators);
        Annotation annotation = new Annotation(str);
        pipeline.annotate(annotation);
        pipeline.printToFile(annotation, outputPrinter);
        String annotatedStr = annotation.toString();
        System.out.println(annotatedStr);

        // You can also get a sentence from VnCoreNLP for analysing individually
        Sentence firstSentence = annotation.getSentences().get(0);
        String annotatedSentenceStr = firstSentence.toString();
    }
}
