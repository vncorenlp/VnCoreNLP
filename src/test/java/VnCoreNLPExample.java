import vn.pipeline.*;
import java.io.*;

public class VnCoreNLPExample {
    public static void main(String[] args) throws IOException {
        String str = "Bà Ngọc Lan đang đến thăm Hà Nội.";
        // wseg: word segmentation; pos: part of speech tagging; ner: name entity recognition; parse: dependence parsing
        // Using word segmentation only
        String[] annotators = {"wseg"};
        VnCoreNLP pipeline = new VnCoreNLP(annotators);
        Annotation annotation = new Annotation(str);
        pipeline.annotate(annotation);
        for (Word word : annotation.getWords())
            System.out.println(word.getForm());
    }
}
