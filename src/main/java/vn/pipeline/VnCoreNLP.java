package vn.pipeline;

import org.apache.log4j.Logger;
import vn.corenlp.ner.NerRecognizer;
import vn.corenlp.parser.DependencyParser;
import vn.corenlp.postagger.PosTagger;
import vn.corenlp.tokenizer.Tokenizer;
import vn.corenlp.wordsegmenter.WordSegmenter;

import java.io.*;
import java.util.*;

public class VnCoreNLP {

    private PosTagger posTagger;

    public final static Logger LOGGER = Logger.getLogger(Annotation.class);

    private WordSegmenter wordSegmenter;
    private NerRecognizer nerRecognizer;
    private DependencyParser dependencyParser;

    public VnCoreNLP() throws IOException {
        String[] annotators = {"wseg", "pos", "ner", "parse"};
        initAnnotators(annotators);
    }

    public VnCoreNLP(String[] annotators) throws IOException {
        initAnnotators(annotators);

    }

    public void initAnnotators(String[] annotators) throws IOException{
        for(String annotator : annotators) {
            switch (annotator.trim()) {
                case "parse":
                    this.dependencyParser = DependencyParser.initialize();
                    break;
                case "ner":
                    this.nerRecognizer = NerRecognizer.initialize();
                    break;
                case "pos":
                    this.posTagger = PosTagger.initialize();
                    break;
                case "wseg":
                    this.wordSegmenter = WordSegmenter.initialize();
                    break;
            }
        }

    }


    public void printToFile(Annotation annotation, PrintStream printer) throws IOException {
        for(Sentence sentence : annotation.getSentences()) {
            printer.println(sentence.toString());
        }
    }

    public void printToFile(Annotation annotation, String fileOut) throws IOException {
        PrintStream printer = new PrintStream(fileOut);
        for(Sentence sentence : annotation.getSentences()) {
            printer.println(sentence.toString() + "\n");
        }
    }

    public void annotate(Annotation annotation) throws IOException {
        List<String> rawSentences = Tokenizer.joinSentences(Tokenizer.tokenize(annotation.getRawText()));
        annotation.setSentences(new ArrayList<>());
        for (String rawStentence : rawSentences) {
            if (rawStentence.trim().length() > 0) {
                Sentence sentence = new Sentence(rawStentence, wordSegmenter, posTagger, nerRecognizer, dependencyParser);
                annotation.getSentences().add(sentence);
                annotation.getTokens().addAll(sentence.getTokens());
                annotation.getWords().addAll(sentence.getWords());
                annotation.setWordSegmentedText(annotation.getWordSegmentedTaggedText() + sentence.getWordSegmentedSentence() + " ");
            }

        }

        annotation.setWordSegmentedText(annotation.getWordSegmentedTaggedText().trim());

    }


    public static void printUsage() {
        System.out.println("Usage: \n\t-fin inputFile (required)\n\t-fout outputFile (optional, default: inputFile.out)\n" +
                "\t-core functionName (optional e.g., wseg,pos,ner,parse)" +
                "\nexample: -fin sample_input.txt -fout output.txt -annotators wseg,pos,ner,parse");
    }

    public static void processPipeline(String fileIn, String fileOut, String[] annotators) throws IOException{

        FileInputStream fis = new FileInputStream(new File(fileIn));
        InputStreamReader isr = new InputStreamReader(fis);
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(new File(fileOut)));

        BufferedReader br = new BufferedReader(isr);
        VnCoreNLP pipeline = new VnCoreNLP(annotators);
        LOGGER.info("Start processing " + fileIn);
        int tokenCount = 0;
        Date start = new Date();
        while(br.ready()) {
            String line = br.readLine();
            if (line.trim().length() > 0) {
                Annotation annotation = new Annotation(line);
                pipeline.annotate(annotation);
                tokenCount += annotation.getTokens().size();
                osw.write(annotation.toString());
            }
        }
        br.close();
        isr.close();
        fis.close();
        osw.close();
        double processingTime = (new Date().getTime() - start.getTime())/1000.0;
        LOGGER.info("Wrote output to " +  fileOut);
//        LOGGER.info("Finished in " + (processingTime) + " seconds with speed: " + (int)(tokenCount/processingTime) + " tokens/s");
    }

    public static void main(String[] args) throws IOException {
        String fileIn = null, fileOut = null;
        String[] annotators = {"wseg", "pos", "ner", "parse"};
        for(int i = 0; i < args.length; i++) {
            if (args[i].equals("-fin") && i + 1 < args.length) fileIn = args[i+1];
            else if (args[i].equals("-fout") && i + 1 < args.length) fileOut = args[i+1];
            else if (args[i].equals("-annotators") && i + 1 < args.length) annotators = args[i+1].split(",");
        }

        if (fileIn == null) {
            printUsage();
            return;
        }

        if (fileOut == null) fileOut = fileIn + ".out";
        processPipeline(fileIn, fileOut, annotators);
    }

}
