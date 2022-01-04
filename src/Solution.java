import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


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

    private int numChars(String s0, String s1) {
        int total = 0;

        for (Character c : s0.toCharArray()) {
            if (s1.contains(c.toString())) { total++; }
        }

        return total;
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
        int total = 0;
        String seven = "";
        String four  = "";

        for (Entry entry : this.entries) {
            StringBuilder decode = new StringBuilder();

            for (String signal: entry.signalPatterns) {
                switch (signal.length()) {
                    case 4: four  = signal; break;
                    case 3: seven = signal; break;
                }
            }

            for (String out : entry.outputSignals) {
                if (out.length() == 2) {
                    decode.append('1');
                } else if (out.length() == 3) {
                    decode.append('7');
                } else if (out.length() == 4) {
                    decode.append('4');
                } else if (out.length() == 7) {
                    decode.append('8');
                } else if ((out.length() == 5) && (numChars(out, seven) == 2) && (numChars(out, four) == 2)) {
                    decode.append('2');
                } else if ((out.length() == 5) && (numChars(out, seven) == 3)) {
                    decode.append('3');
                } else if ((out.length() == 5) && (numChars(out, seven) == 2) && (numChars(out, four) == 3)) {
                    decode.append('5');
                } else if ((out.length() == 6) && (numChars(out, seven) == 3) && (numChars(out, four) == 3)) {
                    decode.append('0');
                }  else if ((out.length() == 6) && (numChars(out, seven) == 2) && (numChars(out, four) == 3)) {
                    decode.append('6');
                } else if ((out.length() == 6) && (numChars(out, four) == 4)) {
                    decode.append('9');
                }
            }

            total += Integer.parseInt(decode.toString());
        }

        System.out.println("Decoded total: " + total);
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