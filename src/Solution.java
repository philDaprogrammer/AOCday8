import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Solution {
    private ArrayList<Entry> entries;

    public Solution(String filename) { this.entries = parse(filename); }

    private ArrayList<Entry> parse(String filename) {
        ArrayList<Entry> entries = new ArrayList<>();

        try {
            File file         = new File(filename);
            FileReader fr     = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line = br.readLine()) != null) {
                String[] in =  line.split("\\|");
                entries.add(new Entry(in[0].split(" "), Arrays.copyOfRange(in[1].split(" "), 1, 5)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return entries;
    }

    private Character mapComplex(ArrayList<String> unknown, String one) {
        for (String num : unknown) {
            Character c1 = one.charAt(0);
            Character c2 = one.charAt(1);

            if ((num.contains(c1.toString())) && (!num.contains(c2.toString()))) { return c2; }
            if ((!num.contains(c1.toString())) && (num.contains(c2.toString()))) { return c1; }
        }

        return null;
    }

    private String filter(String s, String filter) {
        StringBuilder filtered = new StringBuilder();

        for (Character c : s.toCharArray()) {
            if (!filter.contains(c.toString())) { filtered.append(c); }
        }

        return filtered.toString();
    }

    public void solveP1() {
        int total = 0;

        for (Entry entry : this.entries) {
            for (String out : entry.outputSignals) {
                if ((out.length() == 4) || (out.length() == 2) || (out.length() == 7) || (out.length() == 3)) {
                    total++;
                }
            }
        }

        System.out.println("Unique signals: " + total);
    }

    public void solveP2() {
        String seven = "";
        String four  = "";
        String one   = "";
        String eight = "";

        for (Entry entry : this.entries) {
            Map<Character, Character> charMapping = new HashMap<>();
            ArrayList<String> length6             = new ArrayList<>();
            ArrayList<String> length5             = new ArrayList<>();

            for (String signal: entry.signalPatterns) {
                switch (signal.length()) {
                    case 2: one   = signal; break;
                    case 3: seven = signal; break;
                    case 4: four  = signal; break;
                    case 7: eight = signal; break;
                    case 6: length6.add(signal); break;
                    case 5: length5.add(signal); break;
                }
            }

            String bd          = filter(four, one);
            Character aMapping = filter(seven, one).charAt(0);
            Character cMapping = mapComplex(length6, one);
            Character fMapping = filter(one, cMapping.toString()).charAt(0);
            Character dMapping = mapComplex(length5, bd);
            Character bMapping = filter(bd, dMapping.toString()).charAt(0);

            String collected = aMapping.toString()
                    + cMapping.toString()
                    + fMapping.toString()
                    + dMapping.toString()
                    + bMapping.toString();

            String eg          = filter(eight, collected);
            Character eMapping = mapComplex(length6, eg);
            Character gMapping = filter(eg, eMapping.toString()).charAt(0);

            charMapping.put(aMapping, 'a');
            charMapping.put(cMapping, 'c');
            charMapping.put(fMapping, 'f');
            charMapping.put(dMapping, 'd');
            charMapping.put(bMapping, 'b');
            charMapping.put(eMapping, 'e');
            charMapping.put(gMapping, 'g');

            System.out.println(charMapping);
        }
    }

    private static class Entry {
        public String[] signalPatterns;
        public String[] outputSignals;

        public Entry(String[] signalPatterns, String[] outputSignals) {
            this.signalPatterns = signalPatterns;
            this.outputSignals  = outputSignals;
        }
    }
}