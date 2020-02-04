import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class output {

    public String term;//goID
    public String name;
    public int size;//measuredGenesSize
    public boolean is_true;
    public int noverlap;//numOfSignificantGenes
    public float hg_pval;
    public float hg_fdr;
    public float fej_pval;
    public float fej_fdr;
    public float ks_stat;
    public float ks_pval;
    public float ks_fdr;
    public int shorest_path_to_a_true;


    //TODO check () finished ()
    public static ArrayList<output> generateOuputsAndPrint(String o, HashMap<String, goEntry> map) {
        ArrayList<output> outputs = new ArrayList<>();





        return outputs;
    }


    //TODO check () finished (x)
    public static void writeOutputToFile(String o, ArrayList<output> outputs) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(o));
            bw.write("term\tname\tsize\tis_true\tnoverlap\thg_pval\thg_fdr\tfej_pval\tfej_fdr\tks_stat\tks_pval\tks_fdr\tshortest_path_to_a_true\n");
            for (output out : outputs) {
                bw.write(out.toString());
            }
            bw.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return term + "\t" +
                name + "\t" +
                size + "\t" +
                is_true + "\t" +
                noverlap + "\t" +
                hg_pval + "\t" +
                hg_fdr + "\t" +
                fej_pval + "\t" +
                fej_fdr + "\t" +
                ks_stat + "\t" +
                ks_pval + "\t" +
                ks_fdr + "\t" +
                shorest_path_to_a_true + "\n";
    }
}
