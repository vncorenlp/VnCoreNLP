package vn.corenlp.wordsegmenter;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author DatQuocNguyen
 */
public class WordSegmenter {
    private  Node root;
    private static WordSegmenter wordSegmenter = null;
    public final static Logger LOGGER = Logger.getLogger(WordSegmenter.class);
    public WordSegmenter()
            throws IOException {
        LOGGER.info("Loading Word Segmentation model");
        String modelPath = System.getProperty("user.dir") + "/models/wordsegmenter/wordsegmenter.rdr";
        if (!new File(modelPath).exists())
            throw new IOException("WordSegmenter: " + modelPath + " is not found!");

        this.constructTreeFromRulesFile(modelPath);
    }

    public static WordSegmenter initialize() throws IOException {
        if(wordSegmenter == null) {
            wordSegmenter = new WordSegmenter();
        }
        return wordSegmenter;
    }
    private void constructTreeFromRulesFile(String rulesFilePath)
            throws IOException {
        BufferedReader buffer = new BufferedReader(
                new InputStreamReader(new FileInputStream(new File(rulesFilePath)), "UTF-8"));
        String line = buffer.readLine();

        this.root = new Node(new FWObject(false), "NN", null, null, null, 0);

        Node currentNode = this.root;
        int currentDepth = 0;

        for (; (line = buffer.readLine()) != null; ) {
            int depth = 0;
            for (int i = 0; i <= 6; i++) { // Supposed that the maximum
                // exception level is up to 6.
                if (line.charAt(i) == '\t')
                    depth += 1;
                else
                    break;
            }

            line = line.trim();
            if (line.length() == 0)
                continue;

            if (line.contains("cc:"))
                continue;

            FWObject condition = Utils.getCondition(line.split(" : ")[0].trim());
            String conclusion = Utils.getConcreteValue(line.split(" : ")[1].trim());

            Node node = new Node(condition, conclusion, null, null, null, depth);

            if (depth > currentDepth) {
                currentNode.setExceptNode(node);
            } else if (depth == currentDepth) {
                currentNode.setIfnotNode(node);
            } else {
                while (currentNode.getDepth() != depth)
                    currentNode = currentNode.getFatherNode();
                currentNode.setIfnotNode(node);
            }
            node.setFatherNode(currentNode);

            currentNode = node;
            currentDepth = depth;
        }
        buffer.close();
    }

    private Node findFiredNode(FWObject object) {
        Node currentN = root;
        Node firedN = null;
        while (true) {
            if (currentN.satisfy(object)) {
                firedN = currentN;
                if (currentN.getExceptNode() == null) {
                    break;
                } else {
                    currentN = currentN.getExceptNode();
                }
            } else {
                if (currentN.getIfnotNode() == null) {
                    break;
                } else {
                    currentN = currentN.getIfnotNode();
                }
            }

        }

        return firedN;
    }

    private List<WordTag> getInitialSegmentation(String sentence)
    {
        List<WordTag> wordtags = new ArrayList<>();

        for (String regex : Utils.NORMALIZER_KEYS)
            if (sentence.contains(regex))
                sentence = sentence.replaceAll(regex, Utils.NORMALIZER.get(regex));

        List<String> tokens = Arrays.asList(sentence.split("\\s+"));
        List<String> lowerTokens = Arrays.asList(sentence.toLowerCase().split("\\s+"));

        int senLength = tokens.size();
        int i = 0;
        while (i < senLength) {
            String token = tokens.get(i);
            if (token.chars().allMatch(Character::isLetter)) {

                if (Character.isLowerCase(token.charAt(0)) && (i + 1) < senLength) {
                    if (Character.isUpperCase(tokens.get(i + 1).charAt(0))) {
                        wordtags.add(new WordTag(token, "B"));
                        i++;
                        continue;
                    }
                }

                boolean isSingleSyllabel = true;
                for (int j = Math.min(i + 4, senLength); j > i + 1; j--) {
                    String word = String.join(" ", lowerTokens.subList(i, j));
                    if (Vocabulary.VN_DICT.contains(word)
                            || Vocabulary.VN_LOCATIONS.contains(word) || Vocabulary.COUNTRY_L_NAME.contains(word)) {

                        wordtags.add(new WordTag(token, "B"));
                        for (int k = i + 1; k < j; k++)
                            wordtags.add(new WordTag(tokens.get(k), "I"));

                        i = j - 1;

                        isSingleSyllabel = false;
                        break;
                    }
                }
                if (isSingleSyllabel) {
                    String lowercasedToken = lowerTokens.get(i);

                    if (Vocabulary.VN_FIRST_SENT_WORDS.contains(lowercasedToken)
                            || Character.isLowerCase(token.charAt(0))
                            || token.chars().allMatch(Character::isUpperCase)
                            || Vocabulary.COUNTRY_S_NAME.contains(lowercasedToken)
                            || Vocabulary.WORLD_COMPANY.contains(lowercasedToken)) {

                        wordtags.add(new WordTag(token, "B"));
                        i++;
                        continue;

                    }

                    // Capitalized
                    int ilower = i + 1;
                    for (ilower = i + 1; ilower < Math.min(i + 4, senLength); ilower++) {
                        String ntoken = tokens.get(ilower);
                        if (Character.isLowerCase(ntoken.charAt(0))
                                || !ntoken.chars().allMatch(Character::isLetter)
                                || ntoken.equals("LBKT") || ntoken.equals("RBKT")) {
                            break;
                        }
                    }

                    if (ilower > i + 1) {
                        boolean isNotMiddleName = true;
                        if (Vocabulary.VN_MIDDLE_NAMES.contains(lowercasedToken) && (i >= 1)) {
                            String prevT = tokens.get(i - 1);
                            if (Character.isUpperCase(prevT.charAt(0))) {
                                if (Vocabulary.VN_FAMILY_NAMES.contains(prevT.toLowerCase())) {
                                    wordtags.add(new WordTag(token, "I"));
                                    isNotMiddleName = false;
                                }
                            }
                        }
                        if (isNotMiddleName)
                            wordtags.add(new WordTag(token, "B"));
                        for (int k = i + 1; k < ilower; k++)
                            wordtags.add(new WordTag(tokens.get(k), "I"));

                        i = ilower - 1;
                    }
                    else {
                        wordtags.add(new WordTag(token, "B"));
                    }
                }
            }
            else {
                wordtags.add(new WordTag(token, "B"));
            }

            i++;
        }

        return wordtags;

    }

    public String segmentTokenizedString(String str)
            throws IOException {
        StringBuilder sb = new StringBuilder();

        String line = str.trim();
        if (line.length() == 0) {
            return "\n";
        }

        List<WordTag> wordtags = this.getInitialSegmentation(line);

        int size = wordtags.size();
        for (int i = 0; i < size; i++) {
            FWObject object = Utils.getObject(wordtags, size, i);
            Node firedNode = findFiredNode(object);
            if (firedNode.getDepth() > 0) {
                if (firedNode.getConclusion().equals("B"))
                    sb.append(" " + wordtags.get(i).form);
                else
                    sb.append("_" + wordtags.get(i).form);
            }
            else {// Fired at root, return initialized tag
                if (wordtags.get(i).tag.equals("B"))
                    sb.append(" " + wordtags.get(i).form);
                else
                    sb.append("_" + wordtags.get(i).form);
            }
        }
        return sb.toString().trim();
    }

}

