package vn.corenlp.parser;

import edu.emory.mathcs.nlp.common.util.NLPUtils;
import edu.emory.mathcs.nlp.component.template.NLPComponent;
import edu.emory.mathcs.nlp.component.template.node.FeatMap;
import edu.emory.mathcs.nlp.component.template.node.NLPNode;
import edu.emory.mathcs.nlp.decode.NLPDecoder;
import org.apache.log4j.Logger;
import vn.pipeline.Word;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DependencyParser {
    private NLPDecoder nlpDecoder ;
    public final static Logger LOGGER = Logger.getLogger(DependencyParser.class);
    private static DependencyParser dependencyParser;
    public static DependencyParser initialize() throws IOException {
        if(dependencyParser == null) {
            dependencyParser = new DependencyParser();
        }
        return dependencyParser;
    }

    public DependencyParser() throws IOException {
        LOGGER.info("Loading Dependency Parsing model");
        nlpDecoder = new NLPDecoder();
        List<NLPComponent<NLPNode>> components = new ArrayList();
        String modelPath = System.getProperty("user.dir") + "/models/dep/vi-dep.xz";
        if (!new File(modelPath).exists()) throw new IOException("DependencyParser: " + modelPath + " is not found!");
        components.add(NLPUtils.getComponent(modelPath));
        nlpDecoder.setComponents(components);

    }

    public void tagSentence(List<Word> sentenceWords) {
        NLPNode[] decodedNodes = nlpDecoder.decode(toNodeArray(sentenceWords));
        for(int i = 0; i < sentenceWords.size(); i++) {
            Word word = sentenceWords.get(i);
            word.setHead(decodedNodes[i + 1].getDependencyHead().getID());
            word.setDepLabel(decodedNodes[i + 1].getDependencyLabel());
            if(word.getPosTag() != null && word.getPosTag().equals("CH")) word.setDepLabel("punct");
        }
    }

    private NLPNode[] toNodeArray(List<Word> sentenceWords) {
        NLPNode[] nlpNodes = new NLPNode[sentenceWords.size() + 1];
        nlpNodes[0] = new NLPNode();
        for(int i = 0; i < sentenceWords.size(); i++) {
            Word word = sentenceWords.get(i);
            //int id, String form, String lemma, String posTag, String namentTag, FeatMap feats
            nlpNodes[i + 1] = new NLPNode(word.getIndex(), word.getForm(), word.getForm(),
                    word.getPosTag(), word.getNerLabel() == null?"O" : word.getNerLabel(), new FeatMap());

        }
        return nlpNodes;
    }

    public static void main(String[] args) {


    }
}
