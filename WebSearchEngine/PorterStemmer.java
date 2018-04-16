package Main;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PorterStemmer {

    private StringBuilder SB;
    private int M[]; // m denotes the (CV)^m where C is the not vowels chars and V is the vowels
    // M is the array where we but m for each substring
    private int V[]; // number of vowels for each substring
    static Map<String, String> Stemms = new HashMap<>(); // A map to savee the words stemmed before

    
    final private List<String> StopWords;
    public PorterStemmer(){
        StopWords = Arrays.asList(new String[]{"","a","about","above","after","again","against","all","am","an","and","any","are","aren't","as","at","be"
                ,"because","been","before","being","below","between","both","but","by","can't","cannot","could","couldn't","did","didn't"
                ,"do","does","doesn't","doing","don't","down","during","each","few","for","from","further","had","hadn't","has","hasn't"
                ,"have","haven't","having","he","he'd","he'll","he's","her","here","here's","hers","herself","him","himself","his","how",
                "how's","i","i'd","i'll","i'm","i've","if","in","into","is","isn't","it","it's","its","itself","let's","me","more","most"
                ,"mustn't","my","myself","no","nor","not","of","off","on","once","only","or","other","ought","our","ours","ourselves","out"
                ,"over","own","same","shan't","she'd","she","she's","should","shouldn't","so","some","such","than","that","that's","the",
                "their","theirs","them","themselves","then","there","there's","these","they","they'd","they'll","they're","they've","this"
                ,"those","through","to","too","under","until","up","very","was","wasn't","we","we'd","we'll","we're","we've","were",
                "weren't","what","what's","when","when's","where","where's","which","while","who","who's","whom","why","why's","with",
                "won't","would","wouldn't","you","you'd","you'll","you're","you've","your","yours","yourself","yourselves"});
    }
    
    ArrayList<String> StemText(String Text) {
        ArrayList<String> res = new ArrayList<>();
        Text = Text.trim().toLowerCase();
        String[] arr = Text.split("[\\s!#%^@|+` —\n\t\r \\[\\]{}\"&*(:;)\\\\<>?,/_.?$-]+");
        for (String word : arr) {
            if (!StopWords.contains(word)) {
                res.add(this.STEM(word));
            }
        }
        return res;
    }

    public String STEM(String S) {
        // Initialize data
        if (Stemms.containsKey(S)) {
            return Stemms.get(S);
        }

        SB = new StringBuilder(S);
        if(S.length() > 2){
            M = new int[SB.length()];
            V = new int[SB.length()];
            calculateMs();
            calculateVs();

            Step1a();
            Step1b();
            Step1c();
            Step2();
            Setp3();
            Step4();
            Step5a();
            Step5b();
        }
        String res = "";
        try {
            res = new String(SB.toString().getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PorterStemmer.class.getName()).log(Level.SEVERE, null, ex);
        }

        Stemms.put(S, res);
        return res;
    }

    private void Step5b() {
        if (SB.length() > 2 && M[SB.length() - 1] > 1 && SB.charAt(SB.length() - 1) == SB.charAt(SB.length() - 2)
                && SB.charAt(SB.length() - 1) == 'l') {
            SB.deleteCharAt(SB.length() - 1);
        }
    }

    private void Step5a() {
        if ((SB.length() > 1 && M[SB.length() - 2] > 1 && SB.charAt(SB.length() - 1) == 'e')
                || (SB.length() > 3 && M[SB.length() - 2] == 1 && SB.charAt(SB.length() - 1) == 'e'
                && !(!vowel(SB.length() - 2) && vowel(SB.length() - 3) && !vowel(SB.length() - 4)))) {
            SB.deleteCharAt(SB.length() - 1);
        }

    }

    private void Step4() {
        boolean CondStatisfied;
        if (SB.length() > 2 && ends("al") && M[SB.length() - 3] > 1) {
            replaceWithV("al", "");
            return;
        }
        CondStatisfied = ends("al");
        if (SB.length() > 4 && !CondStatisfied && (ends("ance") && M[SB.length() - 5] > 1)) {
            replaceWithV("ance", "");
            return;
        }
        CondStatisfied |= ends("ance");
        if (SB.length() > 4 && !CondStatisfied && (ends("ence") && M[SB.length() - 5] > 1)) {
            replaceWithV("ence", "");
            return;
        }
        CondStatisfied |= ends("ence");
        if (SB.length() > 2 && !CondStatisfied && (ends("er") && M[SB.length() - 3] > 1)) {
            replaceWithV("er", "");
            return;
        }
        CondStatisfied |= ends("er");
        if (SB.length() > 2 && !CondStatisfied && (ends("ic") && M[SB.length() - 3] > 1)) {
            replaceWithV("ic", "");
            return;
        }
        CondStatisfied |= ends("ic");
        if (SB.length() > 4 && !CondStatisfied && (ends("able") && M[SB.length() - 5] > 1)) {
            replaceWithV("able", "");
            return;
        }
        CondStatisfied |= ends("able");
        if (SB.length() > 4 && !CondStatisfied && (ends("ible") && M[SB.length() - 5] > 1)) {
            replaceWithV("ible", "");
            return;
        }
        CondStatisfied |= ends("ible");

        if (SB.length() > 3 && !CondStatisfied && (ends("ant") && M[SB.length() - 4] > 1)) {
            replaceWithV("ant", "");
            return;
        }
        CondStatisfied |= ends("ant");
        if (SB.length() > 5 && !CondStatisfied && (ends("ement") && M[SB.length() - 6] > 1)) {
            replaceWithV("ement", "");
            return;
        }
        CondStatisfied |= ends("ement");
        if (SB.length() > 4 && !CondStatisfied && (ends("ment") && M[SB.length() - 5] > 1)) {
            replaceWithV("ment", "");
            return;
        }
        CondStatisfied |= ends("ment");
        if (SB.length() > 3 && !CondStatisfied && (ends("ent") && M[SB.length() - 4] > 1)) {
            replaceWithV("ent", "");
            return;
        }
        CondStatisfied |= ends("ent");
        if (SB.length() > 3 && !CondStatisfied && (ends("ent") && M[SB.length() - 4] > 1)) {
            replaceWithV("ent", "");
            return;
        }
        CondStatisfied |= ends("ent");
        if (SB.length() > 4 && !CondStatisfied && (ends("sion") && M[SB.length() - 5] > 1)) {
            replaceWithV("sion", "");
            return;
        }
        CondStatisfied |= ends("sion");
        if (SB.length() > 4 && !CondStatisfied && (ends("tion") && M[SB.length() - 5] > 1)) {
            replaceWithV("tion", "");
            return;
        }
        CondStatisfied |= ends("tion");
        if (SB.length() > 2 && !CondStatisfied && (ends("ou") && M[SB.length() - 3] > 1)) {
            replaceWithV("ou", "");
            return;
        }
        CondStatisfied |= ends("ou");
        if (SB.length() > 3 && !CondStatisfied && (ends("ism") && M[SB.length() - 4] > 1)) {
            replaceWithV("ism", "");
            return;
        }
        CondStatisfied |= ends("ism");
        if (SB.length() > 3 && !CondStatisfied && (ends("ate") && M[SB.length() - 4] > 1)) {
            replaceWithV("ate", "");
            return;
        }
        CondStatisfied |= ends("ate");
        if (SB.length() > 3 && !CondStatisfied && (ends("iti") && M[SB.length() - 4] > 1)) {
            replaceWithV("iti", "");
            return;
        }
        CondStatisfied |= ends("iti");
        if (SB.length() > 3 && !CondStatisfied && (ends("ous") && M[SB.length() - 4] > 1)) {
            replaceWithV("ous", "");
            return;
        }
        CondStatisfied |= ends("ous");

        if (SB.length() > 3 && !CondStatisfied && (ends("ive") && M[SB.length() - 4] > 1)) {
            replaceWithV("ive", "");
            return;
        }
        CondStatisfied |= ends("ive");
        if (SB.length() > 3 && !CondStatisfied && (ends("ize") && M[SB.length() - 4] > 1)) {
            replaceWithV("ize", "");
            return;
        }
        CondStatisfied |= ends("ize");

    }

    private void Setp3() {
        boolean CondStatisfied;
        if (SB.length() > 5 && ends("icate") && M[SB.length() - 6] > 0) {
            replaceWithV("icate", "ic");
            return;
        }
        CondStatisfied = ends("icate");
        if (SB.length() > 5 && !CondStatisfied && (ends("ative") && M[SB.length() - 6] > 0)) {
            replaceWithV("ative", "");
            return;
        }
        CondStatisfied |= ends("ative");
        if (SB.length() > 5 && !CondStatisfied && (ends("alize") && M[SB.length() - 6] > 0)) {
            replaceWithV("alize", "al");
            return;
        }
        CondStatisfied |= ends("alize");
        if (SB.length() > 5 && !CondStatisfied && (ends("iciti") && M[SB.length() - 6] > 0)) {
            replaceWithV("iciti", "ic");
            return;
        }
        CondStatisfied |= ends("iciti");
        if (SB.length() > 4 && !CondStatisfied && (ends("ical") && M[SB.length() - 5] > 0)) {
            replaceWithV("ical", "ic");
            return;
        }
        CondStatisfied |= ends("ical");
        if (SB.length() > 3 && !CondStatisfied && (ends("ful") && M[SB.length() - 4] > 0)) {
            replaceWithV("ful", "");
            return;
        }
        CondStatisfied |= ends("ful");
        if (SB.length() > 4 && !CondStatisfied && (ends("ness") && M[SB.length() - 5] > 0)) {
            replaceWithV("ness", "");
            return;
        }
        CondStatisfied |= ends("ness");
    }

    private void Step2() {
        boolean CondStatisfied;
        if (SB.length() > 7 && ends("ational") && M[SB.length() - 8] > 0) {
            replaceWithV("ational", "ate");
            return;
        }
        CondStatisfied = ends("ational");
        if (SB.length() > 6 && !CondStatisfied && (ends("tional") && M[SB.length() - 7] > 0)) {
            replaceWithV("tional", "tion");
            return;
        }
        CondStatisfied |= ends("tional");
        if (SB.length() > 4 && !CondStatisfied && (M[SB.length() - 5] > 0 && ends("enci"))) {
            replaceWithV("enci", "ence");
            return;
        }
        CondStatisfied |= ends("enci");
        if (SB.length() > 4 && !CondStatisfied && (M[SB.length() - 5] > 0 && ends("anci"))) {
            replaceWithV("anci", "ance");
            return;
        }
        CondStatisfied |= ends("anci");
        if (SB.length() > 4 && !CondStatisfied && (M[SB.length() - 5] > 0 && ends("izer"))) {
            replaceWithV("izer", "ize");
            return;
        }
        CondStatisfied |= ends("izer");
        if (SB.length() > 4 && !CondStatisfied && (M[SB.length() - 5] > 0 && ends("abli"))) {
            replaceWithV("abli", "able");
            return;
        }
        CondStatisfied |= ends("able");
        if (SB.length() > 4 && !CondStatisfied && (M[SB.length() - 5] > 0 && ends("alli"))) {
            replaceWithV("alli", "al");
            return;
        }
        CondStatisfied |= ends("alli");
        if (SB.length() > 5 && !CondStatisfied && (M[SB.length() - 6] > 0 && ends("entli"))) {
            replaceWithV("entli", "ent");
            return;
        }
        CondStatisfied |= ends("entli");
        if (SB.length() > 3 && !CondStatisfied && (M[SB.length() - 4] > 0 && ends("eli"))) {
            replaceWithV("eli", "e");
            return;
        }
        CondStatisfied |= ends("eli");
        if (SB.length() > 5 && !CondStatisfied && (M[SB.length() - 6] > 0 && ends("ousli"))) {
            replaceWithV("ousli", "ous");
            return;
        }
        CondStatisfied |= ends("ousli");
        if (SB.length() > 7 && !CondStatisfied && (M[SB.length() - 8] > 0 && ends("ization"))) {
            replaceWithV("ization", "ize");
            return;
        }
        CondStatisfied |= ends("ization");
        if (SB.length() > 5 && !CondStatisfied && (M[SB.length() - 6] > 0 && ends("ation"))) {
            replaceWithV("ation", "ate");
            return;
        }
        CondStatisfied |= ends("ation");
        if (SB.length() > 4 && !CondStatisfied && (M[SB.length() - 5] > 0 && ends("ator"))) {
            replaceWithV("ator", "ate");
            return;
        }
        CondStatisfied |= ends("ator");
        if (SB.length() > 5 && !CondStatisfied && (M[SB.length() - 6] > 0 && ends("alism"))) {
            replaceWithV("alism", "al");
            return;
        }
        CondStatisfied |= ends("alism");
        if (SB.length() > 7 && !CondStatisfied && (M[SB.length() - 8] > 0 && ends("iveness"))) {
            replaceWithV("iveness", "ive");
            return;
        }
        CondStatisfied |= ends("iveness");
        if (SB.length() > 7 && !CondStatisfied && (M[SB.length() - 8] > 0 && ends("fulness"))) {
            replaceWithV("fulness", "ful");
            return;
        }
        CondStatisfied |= ends("fulness");
        if (SB.length() > 7 && !CondStatisfied && (M[SB.length() - 8] > 0 && ends("ousness"))) {
            replaceWithV("ousness", "ous");
            return;
        }
        CondStatisfied |= ends("ousnesss");
        if (SB.length() > 5 && !CondStatisfied && (M[SB.length() - 6] > 0 && ends("aliti"))) {
            replaceWithV("aliti", "al");
            return;
        }
        CondStatisfied |= ends("aliti");
        if (SB.length() > 5 && !CondStatisfied && (M[SB.length() - 6] > 0 && ends("iviti"))) {
            replaceWithV("iviti", "ive");
            return;
        }
        CondStatisfied |= ends("iviti");
        if (SB.length() > 5 && !CondStatisfied && (M[SB.length() - 6] > 0 && ends("biliti"))) {
            replaceWithV("biliti", "ble");
        }
    }

    private void Step1c() {
        if (SB.length() > 1 && SB.charAt(SB.length() - 1) == 'y' && V[SB.length() - 2] > 0) {
            SB.deleteCharAt(SB.length() - 1);
            SB.append("i");
        }
    }

    private void Step1b() {
        boolean okay = false;
        boolean condSatisfied;
        condSatisfied = ends("eed");
        if (condSatisfied && M[SB.length() - 4] > 0) {
            replaceWithV("eed", "ee");
            return;
        }
        if (!condSatisfied && (ends("ed") && V[SB.length() - 3] > 0)) {
            okay = true;
            replaceWithV("ed", "");
            condSatisfied = true;
        }
        condSatisfied |= ends("ed");
        if (!condSatisfied && (ends("ing") && V[SB.length() - 4] > 0)) {
            okay = true;
            replaceWithV("ing", "");
        }
        if (!okay) {
            return;
        }
        if (ends("at")) {
            replaceWithV("at", "ate");
        } else if (ends("bl")) {
            replaceWithV("bl", "ble");
        } else if (ends("iz")) {
            replaceWithV("iz", "ize");
        } else if (SB.length() > 1 && SB.charAt(SB.length() - 1) == SB.charAt(SB.length() - 2)
                && SB.charAt(SB.length() - 1) != 's' && SB.charAt(SB.length() - 1) != 'l'
                && SB.charAt(SB.length() - 1) != 'z') {
            SB.deleteCharAt(SB.length() - 1);
        } else if (SB.length() > 2 && M[SB.length()] == 1 && !vowel(SB.length() - 1) && vowel(SB.length() - 2) && !vowel(SB.length() - 3)
                && SB.charAt(SB.length() - 1) != 'w' && SB.charAt(SB.length() - 1) != 'x'
                && SB.charAt(SB.length() - 1) != 'y') {
            SB.append("e");
        }
    }

    private void Step1a() {
        if (ends("sses")) {
            replaceWithV("sses", "ss");
            return;
        }
        if (ends("ies")) {
            replaceWithV("ies", "i");
            return;
        }
        if (ends("ss")) {
            return;
        }
        if (ends("s")) {
            replaceWithV("s", "");
        }
    }

    private void calculateVs() {
        V[0] = (vowel(0)) ? 1 : 0;
        for (int i = 1; i < SB.length(); i++) {
            V[i] = V[i - 1];
            V[i] += (vowel(i)) ? 1 : 0;
        }
    }

    private void calculateMs() {
        for (int i = 0; i < SB.length(); i++) {
            calculateM(i);
        }
    }

    private void calculateM(int l) {
        Double x = 0.0; // indicate for m
        boolean vOrC = true;// true searching for vowel , False searching for C
        for (int i = 0; i <= l; i++) {
            if (vowel(i) == vOrC) {
                x += 0.5;
                vOrC = !vOrC;
            }
        }
        this.M[l] = x.intValue();
    }

    private boolean vowel(int i) {
        if (SB.charAt(i) == 'y' && i > 0) {
            if (!vowel(i - 1)) {
                return true;
            }
        }

        return SB.charAt(i) == 'a' || SB.charAt(i) == 'e' || SB.charAt(i) == 'i' || SB.charAt(i) == 'o'
                || SB.charAt(i) == 'u';
    }

    private boolean ends(String string) {
        if (SB.length() - string.length() <= 0) {
            return false;
        }
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(string.length() - 1 - i) != SB.charAt(SB.length() - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    private void replaceWithV(String S, String V) {
        SB.replace(SB.length() - S.length(), SB.length(), V);
    }

}
