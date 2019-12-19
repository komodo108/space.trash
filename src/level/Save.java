package level;

import java.io.*;

public class Save {
    private int level = 0;
    private String code = "";
    private File player = new File("data/player/save.dat");

    public void save(int id, String code) {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(player));
            pw.println("level = " + id);
            for(String line : code.split("\n")) {
                pw.println(line);
            } pw.close();
        } catch (IOException e) {
            System.err.println("Unable to save!");
        }
    }

    public void load() {
        try {
            if(player.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(player));
                level = Integer.parseInt(br.readLine().split(" = ")[1]);

                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                } code = sb.toString();
                br.close();
            }
        } catch (IOException e) {
            System.err.println("Unable to load level!");
            level = 0;
            code = "# Enter code here";
        }
    }

    public int getLevel() {
        return level;
    }

    public String getCode() {
        return code;
    }
}
