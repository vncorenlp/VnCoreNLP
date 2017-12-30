package vn.corenlp.wordsegmenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author DatQuocNguyen
 *
 */
public class Utils
{
    public static FWObject getCondition(String strCondition)
    {
        FWObject condition = new FWObject(false);

        for (String rule : strCondition.split(" and ")) {
            rule = rule.trim();
            String key = rule.substring(rule.indexOf(".") + 1, rule.indexOf(" "));
            String value = getConcreteValue(rule);

            if (key.equals("prevWord2")) {
                condition.getContext()[4] = value;
            }
            else if (key.equals("prevTag2")) {
                condition.getContext()[5] = value;
            }
            else if (key.equals("prevWord1")) {
                condition.getContext()[2] = value;
            }
            else if (key.equals("prevTag1")) {
                condition.getContext()[3] = value;
            }
            else if (key.equals("word")) {
                condition.getContext()[1] = value;
            }
            else if (key.equals("tag")) {
                condition.getContext()[0] = value;
            }
            else if (key.equals("nextWord1")) {
                condition.getContext()[6] = value;
            }
            else if (key.equals("nextTag1")) {
                condition.getContext()[7] = value;
            }
            else if (key.equals("nextWord2")) {
                condition.getContext()[8] = value;
            }
            else if (key.equals("nextTag2")) {
                condition.getContext()[9] = value;
            }
        }

        return condition;
    }

    public static FWObject getObject(List<WordTag> wordtags, int size, int index)
    {
        FWObject object = new FWObject(true);

        if (index > 1) {
            object.getContext()[4] = wordtags.get(index - 2).word;
            object.getContext()[5] = wordtags.get(index - 2).tag;
        }

        if (index > 0) {
            object.getContext()[2] = wordtags.get(index - 1).word;
            object.getContext()[3] = wordtags.get(index - 1).tag;
        }

        String currentWord = wordtags.get(index).word;
        String currentTag = wordtags.get(index).tag;

        object.getContext()[1] = currentWord;
        object.getContext()[0] = currentTag;

        if (index < size - 1) {
            object.getContext()[6] = wordtags.get(index + 1).word;
            object.getContext()[7] = wordtags.get(index + 1).tag;
        }

        if (index < size - 2) {
            object.getContext()[8] = wordtags.get(index + 2).word;
            object.getContext()[9] = wordtags.get(index + 2).tag;
        }

        return object;
    }

    public static String getConcreteValue(String str)
    {
        if (str.contains("\"\"")) {
            if (str.contains("Word"))
                return "<W>";
            else
                return "<T>";
        }
        String conclusion = str.substring(str.indexOf("\"") + 1, str.length() - 1);
        return conclusion;
    }

    public static Map<String, String> NORMALIZER;
    public static Set<String> NORMALIZER_KEYS;
    static {
        NORMALIZER = new HashMap<String, String>();
        NORMALIZER.put("òa", "oà");
        NORMALIZER.put("óa", "oá");
        NORMALIZER.put("ỏa", "oả");
        NORMALIZER.put("õa", "oã");
        NORMALIZER.put("ọa", "oạ");
        NORMALIZER.put("òe", "oè");
        NORMALIZER.put("óe", "oé");
        NORMALIZER.put("ỏe", "oẻ");
        NORMALIZER.put("õe", "oẽ");
        NORMALIZER.put("ọe", "oẹ");
        NORMALIZER.put("ùy", "uỳ");
        NORMALIZER.put("úy", "uý");
        NORMALIZER.put("ủy", "uỷ");
        NORMALIZER.put("ũy", "uỹ");
        NORMALIZER.put("ụy", "uỵ");
        NORMALIZER.put("Ủy", "Uỷ");
        NORMALIZER_KEYS = NORMALIZER.keySet();
    }

}
