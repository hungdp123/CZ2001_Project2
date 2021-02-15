package Project2Alternative;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReplaceFormat {
    List<String> lines = new ArrayList<>();
    String line = null;

    // It is to edit random graph text file to be the same as real world text file
    private void editFile(String directory) {
        try {
            File gFile = new File(directory);
            FileReader fr = new FileReader(gFile);
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                if (!(line.contains("--"))) {
                    line = line.replace(line, "");
                }
                if (line.contains("--")) {
                    if (line.contains(";"))
                        line = line.replace("	", "");
                    line = line.replace("\"", "");
                    line = line.replace("--", "	");
                    line = line.replace(";", "\n");
                }
                lines.add(line);
            }
            fr.close();
            br.close();

            FileWriter fw = new FileWriter(gFile);
            BufferedWriter out = new BufferedWriter(fw);
            for(String s : lines)
                out.write(s);
            out.flush();
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void fileMain (String directory) {
        editFile(directory);
    }
}
