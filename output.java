import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
    public int shortest_path_to_a_true;

    public void setHg_fdr(float hg_fdr) {
        this.hg_fdr = hg_fdr;
    }

    public void setFej_fdr(float fej_fdr) {
        this.fej_fdr = fej_fdr;
    }

    public void setKs_fdr(float ks_fdr) {
        this.ks_fdr = ks_fdr;
    }

    //TODO check () finished ()
    public static void generateOuputs(String outPath, rootMapWithValues rootMapWithValues) {
        ArrayList<output> outputs = new ArrayList<>();

        HashMap<String, goEntry> map = rootMapWithValues.getRootMap();
        enrichValues enrichValues = rootMapWithValues.getEnrichValues();

        HashSet<Gene> allGenes = enrichValues.getSignGenes();

        //TODO
        int numOfAllGenes = rootMapWithValues.getNumOfAllGenes();
        int numOfAllSignGenes = enrichValues.getNumOfAllSignGenes();

        output output;
        for (goEntry goEntry : map.values()) {
            int numOfGenesInGoEntry = goEntry.measuredGenesSize;
            //TODO MEASURED GENES != SIGNIFICANT GENES
            int numOfSignGenesInGoEntry = goEntry.numOfSignificantGenes;

            ArrayList<Double> inSet = new ArrayList<>();
            for (Gene g : goEntry.getMeasuredGeneSet()) {
                inSet.add(g.getFc());
            }

            ArrayList<Double> bgDist = new ArrayList<>();
            for (Gene gene : allGenes) {
                if (!goEntry.getMeasuredGeneSet().contains(gene)) {
                    bgDist.add(gene.getFc());
                }
            }
            double[] backGroundDist = new double[bgDist.size()];
            for (int j = 0; j < backGroundDist.length; j++) {
                backGroundDist[j] = bgDist.get(j);
            }
            double[] insetDist = new double[inSet.size()];
            for (int k = 0; k < inSet.size(); k++) {
                insetDist[k] = inSet.get(k);
            }


            output = new output();
            output.term = goEntry.id;
            output.name = goEntry.name;
            output.size = goEntry.measuredGenesSize;
            output.is_true = goEntry.isEnriched;
            output.noverlap = goEntry.numOfSignificantGenes;
            //TODO
            output.hg_pval = Statistics.calcHgPVal(numOfAllGenes, numOfAllSignGenes, numOfGenesInGoEntry, numOfSignGenesInGoEntry);
            output.hg_fdr = 0;
            output.fej_pval = Statistics.calcFejPVal(numOfAllGenes, numOfAllSignGenes, numOfGenesInGoEntry, numOfSignGenesInGoEntry);
            output.fej_fdr = 0;
            if (insetDist.length > 1 && backGroundDist.length > 1) {
                output.ks_stat = Statistics.ksDistBasedEnrichmentValuesStatistic(insetDist, backGroundDist);
                output.ks_pval = Statistics.ksDistBasedEnrichmentValuesTest(insetDist, backGroundDist);
            }
            output.ks_fdr = 0;
            output.shortest_path_to_a_true = 0;
            outputs.add(output);
        }
        HashMap<String, Double> pValuesHg = new HashMap<>();

        HashMap<String, Double> pValuesFej = new HashMap<>();

        HashMap<String, Double> pValuesKs = new HashMap<>();

        for (output value : outputs) {
            pValuesHg.put(value.term, (double) value.hg_pval);
            pValuesFej.put(value.term, (double) value.fej_pval);
            pValuesKs.put(value.term, (double) value.ks_pval);
        }
        HashMap<String, Double> correctedHg = Statistics.benjHochCorrPValues(pValuesHg);
        HashMap<String, Double> correctedFej = Statistics.benjHochCorrPValues(pValuesFej);
        HashMap<String, Double> correctedKs = Statistics.benjHochCorrPValues(pValuesKs);
        for (int w = 0; w < outputs.size(); w++) {
            if (correctedHg.containsKey(outputs.get(w).term)) {
                outputs.get(w).setHg_fdr(correctedHg.get(outputs.get(w).term));
            }
            if (correctedFej.containsKey(outputs.get(w).term)) {
                outputs.get(w).setFej_fdr(correctedFej.get(outputs.get(w).term));
            }
            if (correctedKs.containsKey(outputs.get(w).term)){
                outputs.get(w).setKs_fdr(correctedKs.get(outputs.get(w).term));
            }
        }

        writeOutputToFile(outPath, outputs);
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
                shortest_path_to_a_true + "\n";
    }
}
