//import java.io.FileNotFoundException;
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//
//public class Test {
//
//
//    public HashMap<String, Gene> gene_test = new HashMap<>();
//    public HashMap<String, goEntry> goEntry_annotations_min_max_test = new HashMap<>();
//    public String namespace = "namespace";
//
//    public Test() throws FileNotFoundException, UnsupportedEncodingException {
//        // TEST
//        Gene g1 = new Gene("goEntry:1");
//        g1.id = "goid1";
//        gene_test.put("gen:1", g1);
//
//        Gene g2 = new Gene("goEntry:2");
//        g2.id = "goid2";
//        gene_test.put("gen:2", g2);
//
//        Gene g3 = new Gene("goEntry:3");
//        g3.id = "goid3";
//        gene_test.put("gen:3", g3);
//
//        Gene g4 = new Gene("goEntry:4");
//        g4.id = "goid4";
//        gene_test.put("gen:4", g4);
//
//        goEntry goEntry1 = new goEntry("goEntry:1", "name1", "namespace", new ArrayList<>(Collections.singleton("goEntry:2")));
//        gene_test.get("gen:1");
//        goEntry1.genesSet.add(gene_test.get("gen:1"));
//        goEntry1.genesSet.add(gene_test.get("gen:2"));
//        goEntry_annotations_min_max_test.put("goEntry:1", goEntry1);
//
//        goEntry goEntry2 = new goEntry("goEntry:2", "name2", "namespace", new ArrayList<>(Collections.singleton("goEntry:3")));
//        goEntry2.genesSet.add(gene_test.get("gen:2"));
//        goEntry_annotations_min_max_test.put("goEntry:2", goEntry2);
//
//        goEntry goEntry3 = new goEntry("goEntry:3", "name3", "namespace", new ArrayList<>(Collections.singleton("goEntry:4")));
//        goEntry3.genesSet.add(gene_test.get("gen:3"));
//        goEntry_annotations_min_max_test.put("goEntry:3", goEntry3);
//
//        goEntry goEntry4 = new goEntry("goEntry:4", "name4", "namespace", new ArrayList<>());
//        goEntry4.genesSet.add(gene_test.get("gen:4"));
//        goEntry_annotations_min_max_test.put("goEntry:4", goEntry4);
//
//
//        //HashMap<String, goEntry> map, int minsize, int maxsize, String overlapout
//        overlap.generateOverlapEntryAndPrint(goEntry_annotations_min_max_test, 0, 20, "/home/issra/Schreibtisch/overlapOut.txt");
//
////        dagdrawer_test("/home/yannick/Desktop/5_Semeseter/goEntryBi/Assignment_5");
//        System.exit(0);
//        // TEST END
//    }
//
//    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
//        Test t = new Test();
//    }
//}
