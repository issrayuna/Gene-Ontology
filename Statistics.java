import org.apache.commons.math3.distribution.HypergeometricDistribution;
import org.apache.commons.math3.stat.inference.KolmogorovSmirnovTest;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

import java.util.*;

public class Statistics {
    //TODO check () finished ()
//    public static int searchNearestPath(HashMap<String, goEntry> map, String goID1, String goID2) {
//        int path_length = 0;
//        ArrayList<Integer> lengths = new ArrayList<>();
//        for (String parent : map.get(goID1).getParents()) {
//
//        }
//
//
//        return path_length;
//    }


    //parameter pro GoEntry: alle gene, alle signifikanten Gene, alle gene im goentry, alle sign gene im goentry
    //TODO check () finished (x)
    public static float calcHgPVal(int numOfAllGenes, int numOfAllSignGenes, int numOfGenesInGoEntry, int numOfSignGenesInGoEntry) {//TODO enrichment p-value given by the hypergeometric distribution (hg_pval)
        return (float) new HypergeometricDistribution(numOfAllGenes, numOfAllSignGenes, numOfGenesInGoEntry).upperCumulativeProbability(numOfSignGenesInGoEntry);
    }

    //inset: alle logFold werte im goentry
    //bgDist: logFold von allen gene (vllt ohne die im goentry)
    //TODO check () finished (x)
    public static float ksDistBasedEnrichmentValuesTest(double[] in_set_distrib, double[] bg_distrib) {//TODO with fc
        return (float) new KolmogorovSmirnovTest().kolmogorovSmirnovTest(in_set_distrib, bg_distrib);
    }

    //TODO check () finished (x)
    public static float ksDistBasedEnrichmentValuesStatistic(double[] in_set_distrib, double[] bg_distrib) {
        return (float) new KolmogorovSmirnovTest().kolmogorovSmirnovStatistic(in_set_distrib, bg_distrib);
    }

    //selbe wie calchgpval nur mit minus 1
    //TODO fej_pval check () finished ()
    public static float calcFejPVal(int numOfAllGenes, int numOfAllSignGenes, int numOfGenesInGoEntry, int numOfSignGenesInGoEntry) {//TODO enrichment p-value given by Fischer’s Exact test using jackknifing
        return (float) new HypergeometricDistribution(numOfAllGenes - 1, numOfAllSignGenes - 1, numOfGenesInGoEntry - 1).upperCumulativeProbability(numOfSignGenesInGoEntry - 1);

    }

    //benjamini hochberg:
    // 1. sortieren der pvalues desc
    // 2. n = anzahl element in list
    // am anfang: k = n
    // 3. durch sortierte liste gehen: fdr = pval * n / k -> minwert von fdr updaten (pro element)
    // 4. nach jedem element: k - 1

    //TODO Benjamini Hochberg Correction check () finished ()
    public static HashMap<String, Double> benjHochCorrPValues(HashMap<String, Double> mapOfPValues) {
        LinkedHashMap<String, Double> pVals = mapOfPValues.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        HashMap<String, Double> adjPValues = new HashMap<>();

        Double fdRK;
        Double minFdr = Double.MAX_VALUE;
        int n = pVals.size();
        int rank = n;
        Double currentMin;

        for (Map.Entry<String, Double> entry : pVals.entrySet()) {
            fdRK = (entry.getValue() * n) / rank;
            currentMin = minFdr;
            if (fdRK < minFdr) {
                minFdr = fdRK;
            }
            adjPValues.put(entry.getKey(), minFdr);
            rank--;

        }
        return adjPValues;
    }


    //TODO shortest_path_to_a_true check () finished ()
    public static void calcShortestPathToATrue() {//TODO

    }


}
