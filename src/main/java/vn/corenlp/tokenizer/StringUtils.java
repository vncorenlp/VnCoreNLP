package vn.corenlp.tokenizer;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils
{

    public static void testFoundByRegex(String s, String regex)
    {
        System.out.println("Test string: " + s);

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            System.out.println(s.substring(0, matcher.start()));
            System.out.println(s.substring(matcher.start(), matcher.end()));
            System.out.println(s.substring(matcher.end()));
        }
    }

    public static String char2Hex(Character c)
    {
        return String.format("\\u%04x", (int) c);
    }

    public static Character hex2Char(String hex)
    {
        int hexToInt = Integer.parseInt(hex.substring(2), 16);
        return (char) hexToInt;
    }

    public static boolean hasPunctuation(String s)
    {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isLetterOrDigit(s.charAt(i)))
                return true;
        }

        return false;
    }

    public static boolean isPunctuation(String s)
    {
        for (int i = 0; i < s.length(); i++) {
            if (Character.isLetterOrDigit(s.charAt(i)))
                return false;
        }

        return true;
    }

    public static boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }

    // Modified by Dat Quoc Nguyen
    public static boolean isBrace(String string)
    {
        if (string.equals("”") || string.equals("�") || string.equals("'") || string.equals(")")
                || string.equals("}") || string.equals("]")) {
            return true;
        }
        return false;
    }

    public static HashSet<String> VN_abbreviation;
    public static HashSet<String> VN_exception;
    static {
        VN_abbreviation = new HashSet<String>();
        VN_exception = new HashSet<String>();

        VN_abbreviation.add("M.City");
        VN_abbreviation.add("V.I.P");
        VN_abbreviation.add("PGS.Ts");
        VN_abbreviation.add("MRS.");
        VN_abbreviation.add("Mrs.");
        VN_abbreviation.add("Man.United");
        VN_abbreviation.add("Mr.");
        VN_abbreviation.add("SHB.ĐN");
        VN_abbreviation.add("Gs.Bs");
        VN_abbreviation.add("U.S.A");
        VN_abbreviation.add("TMN.CSG");
        VN_abbreviation.add("Kts.Ts");
        VN_abbreviation.add("R.Madrid");
        VN_abbreviation.add("Tp.");
        VN_abbreviation.add("T.Ư");
        VN_abbreviation.add("D.C");
        VN_abbreviation.add("Gs.Tskh");
        VN_abbreviation.add("PGS.KTS");
        VN_abbreviation.add("GS.BS");
        VN_abbreviation.add("KTS.TS");
        VN_abbreviation.add("PGS-TS");
        VN_abbreviation.add("Co.");
        VN_abbreviation.add("S.H.E");
        VN_abbreviation.add("Ths.Bs");
        VN_abbreviation.add("T&T.HN");
        VN_abbreviation.add("MR.");
        VN_abbreviation.add("Ms.");
        VN_abbreviation.add("T.T.P");
        VN_abbreviation.add("TT.");
        VN_abbreviation.add("TP.");
        VN_abbreviation.add("ĐH.QGHN");
        VN_abbreviation.add("Gs.Kts");
        VN_abbreviation.add("Man.Utd");
        VN_abbreviation.add("GD-ĐT");
        VN_abbreviation.add("T.W");
        VN_abbreviation.add("Corp.");
        VN_abbreviation.add("ĐT.LA");
        VN_abbreviation.add("Dr.");
        VN_abbreviation.add("T&T");
        VN_abbreviation.add("HN.ACB");
        VN_abbreviation.add("GS.KTS");
        VN_abbreviation.add("MS.");
        VN_abbreviation.add("Prof.");
        VN_abbreviation.add("GS.TS");
        VN_abbreviation.add("PGs.Ts");
        VN_abbreviation.add("PGS.BS");
        VN_abbreviation.add("﻿BT.");
        VN_abbreviation.add("Ltd.");
        VN_abbreviation.add("ThS.BS");
        VN_abbreviation.add("Gs.Ts");
        VN_abbreviation.add("SL.NA");
        //VN_abbreviation.add("P.");
        VN_abbreviation.add("Th.S");
        VN_abbreviation.add("Gs.Vs");
        VN_abbreviation.add("PGs.Bs");
        VN_abbreviation.add("T.O.P");
        VN_abbreviation.add("PGS.TS");
        VN_abbreviation.add("HN.T&T");
        VN_abbreviation.add("SG.XT");
        VN_abbreviation.add("O.T.C");
        VN_abbreviation.add("TS.BS");
        VN_abbreviation.add("Yahoo!");
        VN_abbreviation.add("Man.City");
        VN_abbreviation.add("MISS.");
        VN_abbreviation.add("HA.GL");
        VN_abbreviation.add("GS.Ts");
        VN_abbreviation.add("TBT.");
        VN_abbreviation.add("GS.VS");
        VN_abbreviation.add("GS.TSKH");
        VN_abbreviation.add("Ts.Bs");
        VN_abbreviation.add("M.U");
        VN_abbreviation.add("Gs.TSKH");
        VN_abbreviation.add("U.S");
        VN_abbreviation.add("Miss.");
        VN_abbreviation.add("GD.ĐT");
        VN_abbreviation.add("PGs.Kts");
        //VN_abbreviation.add("Q.");
        VN_abbreviation.add("St.");
        VN_abbreviation.add("Ng.");
        VN_abbreviation.add("Inc.");
        VN_abbreviation.add("Th.");
        VN_abbreviation.add("N.O.V.A");

        VN_exception.add("Wi-fi");
        VN_exception.add("17+");
        VN_exception.add("km/h");
        VN_exception.add("M7");
        VN_exception.add("M8");
        VN_exception.add("21+");
        VN_exception.add("G3");
        VN_exception.add("M9");
        VN_exception.add("G4");
        VN_exception.add("km3");
        VN_exception.add("m/s");
        VN_exception.add("km2");
        VN_exception.add("5g");
        VN_exception.add("4G");
        VN_exception.add("8K");
        VN_exception.add("3g");
        VN_exception.add("E9");
        VN_exception.add("U21");
        VN_exception.add("4K");
        VN_exception.add("U23");
        VN_exception.add("Z1");
        VN_exception.add("Z2");
        VN_exception.add("Z3");
        VN_exception.add("Z4");
        VN_exception.add("Z5");
        VN_exception.add("Jong-un");
        VN_exception.add("u19");
        VN_exception.add("5s");
        VN_exception.add("wi-fi");
        VN_exception.add("18+");
        VN_exception.add("Wi-Fi");
        VN_exception.add("m2");
        VN_exception.add("16+");
        VN_exception.add("m3");
        VN_exception.add("V-League");
        VN_exception.add("Geun-hye");
        VN_exception.add("5G");
        VN_exception.add("4g");
        VN_exception.add("Z3+");
        VN_exception.add("3G");
        VN_exception.add("km/s");
        VN_exception.add("6+");
        VN_exception.add("u21");
        VN_exception.add("WI-FI");
        VN_exception.add("u23");
        VN_exception.add("U19");
        VN_exception.add("6s");
        VN_exception.add("4s");
    }

}
