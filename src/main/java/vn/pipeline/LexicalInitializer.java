package vn.pipeline;

import edu.emory.mathcs.nlp.component.template.lexicon.GlobalLexica;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Handler;
import java.util.logging.Level;

public class LexicalInitializer {
    private static LexicalInitializer lexicalInitializer;
    private HashMap<String, String> lexicalMap ;
    private boolean initLexica = false;
    private GlobalLexica globalLexica;

    public final static Logger LOGGER = Logger.getLogger(LexicalInitializer.class);

    public LexicalInitializer(boolean initLexica) throws IOException {

        this.initLexica = initLexica;
        this.lexicalMap = new HashMap<>();
        
        String lexicalPath = System.getProperty("user.dir") + "/models/ner/vi-500brownclusters.xz";
        if (!new File(lexicalPath).exists())
            throw new IOException("LexicalInitializer: " + lexicalPath + " is not found!");
        lexicalMap.put("word_clusters", lexicalPath);
        
        lexicalPath = System.getProperty("user.dir") + "/models/ner/vi-pretrainedembeddings.xz";
        if (!new File(lexicalPath).exists())
            throw new IOException("LexicalInitializer: " + lexicalPath + " is not found!");
        lexicalMap.put("word_embeddings", lexicalPath);
    }

    public static LexicalInitializer initialize(boolean initLexica) throws IOException {
        if (lexicalInitializer == null) {
            lexicalInitializer = new LexicalInitializer(initLexica);
            lexicalInitializer.initializeLexica();
        }
        return lexicalInitializer;
    }

    public GlobalLexica initializeLexica() {
        if (globalLexica == null && initLexica)
            try {

                DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
                Document xmlDoc = docBuilder.newDocument();
                Element root = xmlDoc.createElement("root");
                Element lexicals = xmlDoc.createElement("lexica");
                for(String lexicalName : lexicalMap.keySet()) {
                    Element lexical = xmlDoc.createElement(lexicalName);
                    lexical.setAttribute("field", "word_form_lowercase");
                    if(!new File(lexicalMap.get(lexicalName)).exists()) return null;
                    lexical.appendChild(xmlDoc.createTextNode(lexicalMap.get(lexicalName)));
                    lexicals.appendChild(lexical);
                }
                root.appendChild(lexicals);

                java.util.logging.Logger globalLogger = java.util.logging.Logger.getLogger("");
                globalLogger.setLevel(Level.OFF);
                Handler[] handlers = globalLogger.getHandlers();
                for(Handler handler : handlers) {
                    globalLogger.removeHandler(handler);
                }

                globalLexica = new GlobalLexica<>(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return globalLexica;
    }


}
