package vn.corenlp.ner;

import edu.emory.mathcs.nlp.common.util.NLPUtils;
import edu.emory.mathcs.nlp.component.template.NLPComponent;

import edu.emory.mathcs.nlp.component.template.lexicon.GlobalLexica;
import edu.emory.mathcs.nlp.component.template.node.FeatMap;
import edu.emory.mathcs.nlp.component.template.node.NLPNode;
import edu.emory.mathcs.nlp.decode.NLPDecoder;
import org.apache.log4j.Logger;
import vn.corenlp.wordsegmenter.Vocabulary;
import vn.pipeline.LexicalInitializer;
import vn.pipeline.Word;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NerRecognizer {
    private NLPDecoder nlpDecoder ;
    public final static Logger LOGGER = Logger.getLogger(NerRecognizer.class);
    private static NerRecognizer nerRecognizer;
    public static NerRecognizer initialize() throws IOException{
        if(nerRecognizer == null) {
            nerRecognizer = new NerRecognizer();
        }
        return nerRecognizer;
    }


    public NerRecognizer() throws IOException{
        LOGGER.info("Loading NER model");
        nlpDecoder = new NLPDecoder();
        List<NLPComponent<NLPNode>> components = new ArrayList();

        String modelPath = System.getProperty("user.dir") + "/models/ner/vi-ner.xz";
        if (!new File(modelPath).exists()) throw new IOException("NerRecognizer: " + modelPath + " is not found!");
        GlobalLexica lexica = LexicalInitializer.initialize(true).initializeLexica();
        if(lexica != null) {
            components.add(lexica);
        }
        components.add(NLPUtils.getComponent(modelPath));
        nlpDecoder.setComponents(components);

    }


    public void tagSentence(List<Word> sentenceWords) {
        NLPNode[] decodedNodes = nlpDecoder.decode(toNodeArray(sentenceWords));
        for(int i = 0; i < sentenceWords.size(); i++) {
            Word word = sentenceWords.get(i);
            word.setNerLabel(decodedNodes[i + 1].getNamedEntityTag().replace("U-", "B-").replace("L-", "I-"));
        }
    }

    private NLPNode[] toNodeArray(List<Word> sentenceWords) {
        NLPNode[] nlpNodes = new NLPNode[sentenceWords.size() + 1];
        nlpNodes[0] = new NLPNode();
        for(int i = 0; i < sentenceWords.size(); i++) {
            Word word = sentenceWords.get(i);
            nlpNodes[i + 1] = new NLPNode(word.getIndex(), word.getForm(), word.getForm(), addLabelForPOSTag(word), new FeatMap());

        }
        return nlpNodes;
    }

    public String addLabelForPOSTag(Word word) {
        String[] tokens = word.getForm().split("_");
        String output = word.getPosTag();
        if (word.getPosTag() != null && word.getPosTag().equals("Np")) {
            if (Vocabulary.VN_FAMILY_NAMES.contains(tokens[0].toLowerCase())
                || (tokens.length > 1 && Vocabulary.VN_MIDDLE_NAMES.contains(tokens[1].toLowerCase())))
                output = word.getPosTag() + "-1";
            else output = word.getPosTag() + "-0";
        }
        return output;
    }

    public static void main(String[] args) {


    }
}
