import org.apache.commons.math3.distribution.HypergeometricDistribution;
import org.apache.commons.math3.stat.inference.KolmogorovSmirnovTest;


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

    //TODO check () finished ()
    public static void calcHgPVal() {//TODO enrichment p-value given by the hypergeometric distribution (hg_pval)
//        return new HypergeometricDistribution().upperCumulativeProbability();
    }


    //TODO check () finished ()
    public static double ksDistBasedEnrichmentValuesTest(double[] in_set_distrib, double[] bg_distrib) {//TODO with fc
        return new KolmogorovSmirnovTest().kolmogorovSmirnovTest(in_set_distrib, bg_distrib);
    }

    //TODO check () finished ()
    public static double ksDistBasedEnrichmentValuesStatistic(double[] in_set_distrib, double[] bg_distrib) {
        return new KolmogorovSmirnovTest().kolmogorovSmirnovStatistic(in_set_distrib, bg_distrib);
    }


    //TODO hg_fdr check () finished ()
    public static void calcHgFdr() {//TODO  Benjamini-Hochberg corrected hg_pval

    }


    //TODO fej_pval check () finished ()
    public static void calcFejPVal() {//TODO enrichment p-value given by Fischer’s Exact test using jackknifing

    }


    //TODO fej_fdr check () finished ()
    public static void calcFejFdr() {//TODO Benjamini-Hochberg corrected fej_pval

    }


    //TODO ks_stat check () finished ()
    public static void calcKsStat() {//TODO  the statistic value given by KS test comparing the log 2 fc dist. of the measured genes associated to the goEntry vs. the background fc dist.

    }


    //TODO ks_fdr check () finished ()
    public static void calcKsFdr() {//TODO Benjamini-Hochberg corrected ks_pval

    }


    //TODO shortest_path_to_a_true check () finished ()
    public static void calcShortestPathToATrue() {//TODO

    }

}
