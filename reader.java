import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;

public class reader {

    //TODO check () finished (x)
    public static HashMap<String, goEntry> generateRootMap(HashMap<String, goEntry> mapVON, String root) {
        HashMap<String, goEntry> mapZU = new HashMap<>();
        int n = 0;
        for (String goID : mapVON.keySet()) {
            goEntry goEntry = mapVON.get(goID);
            if (goEntry.getParents().isEmpty()) {
                goEntry.setRoot(true);
            }

            if (goEntry.getNamespace().equals(root)) {

                n = (int) calcNumOfMeasuredGenes(goEntry).get(0);
                goEntry.setMeasuredGeneSet((HashSet<Gene>) calcNumOfMeasuredGenes(goEntry).get(1));
                goEntry.setMeasuredGenesSize(n);
                mapZU.put(goID, goEntry);
            }
        }
        return mapZU;
    }

    //TODO check (x) finished (x)
    public static ArrayList<Object> calcNumOfMeasuredGenes(goEntry goEntry) {
        ArrayList<Object> objects = new ArrayList<>();
        HashSet<Gene> measuredGenesFC = new HashSet<>();
        int sum = 0;
        if (goEntry.getGenesSet() != null) {
            for (Gene g : goEntry.getGenesSet()) {
                if (g.getFc() != 0) {
                    measuredGenesFC.add(g);
                    sum++;
                }
            }
        }
        objects.add(sum);
        objects.add(measuredGenesFC);

        return objects;
    }


    //TODO check (x) finished (x)
    public static Gene retGeneWithSameId(HashSet<Gene> geneSet, String id) {
        if (geneSet.stream().filter(x -> x.getId().equals(id)).findFirst().isPresent()) {
            return geneSet.stream().filter(x -> x.getId().equals(id)).findFirst().get();
        } else {
            return null;
        }
    }

    //TODO check (x) finished (x)
    public static HashSet<Gene> generateGeneSetFromGafGO(String goOptionPath, HashSet<Gene> genesWithFcSignif) throws IOException {
        HashSet<Gene> geneSet = new HashSet<>();
        HashMap<String, ArrayList<String>> genesWithGoList = new HashMap<>();

        InputStream gzipStream = new GZIPInputStream(new FileInputStream(goOptionPath));
        BufferedReader br = new BufferedReader(new InputStreamReader(gzipStream));
        String line;

        try {
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("!")) {
                    String[] cols = line.split("\t");
                    if (!genesWithGoList.containsKey(cols[2])) {
                        ArrayList<String> gos = new ArrayList<>();
                        gos.add(cols[4]);
                        genesWithGoList.put(cols[2], gos);
                    } else if (genesWithGoList.containsKey(cols[2])) {
                        ArrayList<String> gos = genesWithGoList.get(cols[2]);
                        gos.add(cols[4]);
                        genesWithGoList.put(cols[2], gos);
                    }
                }
            }
            gzipStream.close();

            for (String geneId : genesWithGoList.keySet()) {
                Gene g = new Gene();
                g.setId(geneId);
                g.setGoEntryHashSet(new HashSet<>(genesWithGoList.get(geneId)));
                Gene g2 = retGeneWithSameId(genesWithFcSignif, geneId);
                if (g2 != null) {
//                    System.out.println("mappedGene: " + g.id + " || measuredGene: " + g2.id);
                    g.setFc(g2.getFc());
                    g.setSignif(g2.isSignif());
                }
                geneSet.add(g);
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


        return geneSet;
    }

    //TODO check (x) finished (x)
    public static HashSet<Gene> generateGeneSetFromEnsembleTsv(String ensemblPath, HashSet<Gene> genesWithFcSignif) throws FileNotFoundException {
        HashSet<Gene> genes = new HashSet<>();

        BufferedReader br = new BufferedReader(new FileReader(ensemblPath));
        String line;

        try {
            while ((line = br.readLine()) != null) {
                if (line.startsWith("ENSG")) {
                    String[] cols = line.split("\t");
                    String[] gos = cols[2].split("\\|");

                    Gene g2 = retGeneWithSameId(genesWithFcSignif, cols[1]);
                    if (g2 == null) {
                        g2 = new Gene();
                    }

                    Gene g = new Gene(cols[1], g2.getFc(), g2.isSignif(), new HashSet<>(new ArrayList<>(Arrays.asList(gos))));

                    genes.add(g);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return genes;
    }

    //TODO check (x) finished (x)
    public static HashMap<String, goEntry> generateGoEntriesFromOboFile(String oboPath, ArrayList<String> enrichedGOs) {
        HashMap<String, goEntry> oboMap = new HashMap<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(oboPath));
            String line;
            ArrayList<String> parents;
            goEntry goEntry;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("[Term]")) {
                    goEntry = new goEntry();
                    parents = new ArrayList<>();
                    while ((line = br.readLine()) != null) {
                        if (line.startsWith("id: GO:")) {
                            goEntry.setId(line.substring(4));
                            if (enrichedGOs.contains(line.substring(4))) {
                                goEntry.setEnriched(true);
                            }
                        } else if (line.startsWith("name:")) {
                            goEntry.setName(line.substring(6));
                        } else if (line.startsWith("namespace")) {
                            goEntry.setNamespace(line.substring(11));
                        } else if (line.startsWith("is_a")) {
                            parents.add(line.substring(6, 16));
                        } else if (line.startsWith("is_obsolete: true")) {
                            goEntry.setObsolete(true);
                        } else if (line.isEmpty()) {
                            goEntry.setParents(parents);
                            oboMap.put(goEntry.getId(), goEntry);
                            break;
                        }
                    }
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        oboMap = removeObsolete(oboMap);

        return oboMap;
    }

    //TODO check (x) finished (x)
    public static HashMap<String, goEntry> removeObsolete(HashMap<String, goEntry> goEntries) {
        HashMap<String, goEntry> withoutObsolete = new HashMap<>();
        for (String goID : goEntries.keySet()) {
            if (!goEntries.get(goID).isObsolete()) {
                withoutObsolete.put(goID, goEntries.get(goID));
            }
        }
        return withoutObsolete;
    }


    //TODO check (x) finished (x)
    public static int calcNumOfNoverlap(HashSet<Gene> genes) {
        int sum = 0;

        for (Gene g : genes) {
            if (g.isSignif()) sum++;
        }
        return sum;
    }


    //TODO check (x) finished (x)
    public static HashMap<String, goEntry> addGenesToGoEntries(HashMap<String, goEntry> mapZU, HashMap<String, HashSet<Gene>> gosWithGenesListVON) {
        for (String goID1 : gosWithGenesListVON.keySet()) {
            HashSet<Gene> genes1 = gosWithGenesListVON.get(goID1);
            if (mapZU.containsKey(goID1)) {
                goEntry goEntry = mapZU.get(goID1);
                goEntry.setGenesSet(genes1);
                goEntry.setNumOfSignificantGenes(calcNumOfNoverlap(genes1));
                mapZU.put(goID1, goEntry);
            }
        }
        return mapZU;
    }

    //TODO check (x) finished (x)
    public static HashMap<String, HashSet<Gene>> generateGoMapWithGeneSet(HashSet<Gene> geneAsKey) {
        HashMap<String, HashSet<Gene>> map = new HashMap<>();//String -> GO ID & HashSet -> of Gene IDs
        for (Gene g : geneAsKey) {
            for (String goID : g.getGoEntryHashSet()) {
                if (map.containsKey(goID)) {
                    HashSet<Gene> genes = map.get(goID);
                    genes.add(g);
                    map.put(goID, genes);
                } else if (!map.containsKey(goID)) {
                    HashSet<Gene> genes = new HashSet<>();
                    genes.add(g);
                    map.put(goID, genes);
                }
            }
        }
        return map;
    }

    //TODO check (x) finished (x) improved () [GENERATE ONLY ONE ROOTMAP FROM THE BEGINNING????????????????? SAVES A LOT OF TIME!!!!!!!!!!!!!!!!]
    public static rootMapWithValues generateRootMapBasedOnMappingType(String oboPath, String
            mappingFile, String mappingType, String root, String enrichFilePath) throws IOException {

        rootMapWithValues rootMapWithValues = new rootMapWithValues();

        ArrayList<Object> objects = readEnrichFile(enrichFilePath);
        ArrayList<String> enrichedGOs = (ArrayList<String>) objects.get(0);
        HashSet<Gene> genesWithFcSignif = (HashSet<Gene>) objects.get(1);

        HashMap<String, goEntry> goEntries = generateGoEntriesFromOboFile(oboPath, enrichedGOs);
        HashSet<Gene> genesWithGoLists = new HashSet<>();

        if (mappingType.equals("go")) {
            genesWithGoLists = generateGeneSetFromGafGO(mappingFile, genesWithFcSignif);
        } else if (mappingType.equals("ensembl")) {
            genesWithGoLists = generateGeneSetFromEnsembleTsv(mappingFile, genesWithFcSignif);
        }

        HashMap<String, HashSet<Gene>> gosWithGenesList = generateGoMapWithGeneSet(genesWithGoLists);
        HashMap<String, goEntry> goEntries1 = addGenesToGoEntries(goEntries, gosWithGenesList);
        HashMap<String, goEntry> rootMap = generateRootMap(goEntries1, root);

        rootMap = returnMapWithGeneAsHashSet(rootMap);
        rootMap = overlap.addChildren(rootMap);

        //
        rootMapWithValues.setRootMap(rootMap);
        rootMapWithValues.setEnrichValues((enrichValues) objects.get(2));
        rootMapWithValues.setNumOfAllGenes(genesWithGoLists.size());
        return rootMapWithValues;
    }

    //TODO check (x) finished (x)
    public static ArrayList<Object> readEnrichFile(String enrichmentFilePath) throws IOException {
        ArrayList<Object> objects = new ArrayList<>();

        enrichValues enrichValues = new enrichValues();

        BufferedReader br = new BufferedReader(new FileReader(enrichmentFilePath));
        String line;

        ArrayList<String> enrichedGOs = new ArrayList<>();
        HashSet<Gene> genesWithFcSignif = new HashSet<>();

        int numOfSignGenes = 0;

        while ((line = br.readLine()) != null) {
            if (line.startsWith("#")) {
                enrichedGOs.add(line.substring(1));
            } else if (!line.startsWith("id")) {
                String[] splitted = line.split("\t");
                genesWithFcSignif.add(new Gene(splitted[0], Double.parseDouble(splitted[1]), splitted[2].equals("true")));
                numOfSignGenes++;
            }
        }

        objects.add(enrichedGOs);
        objects.add(genesWithFcSignif);

        //TODO
        enrichValues.setEnrichedGoIds(new HashSet<>(enrichedGOs));
        enrichValues.setNumOfAllSignGenes(numOfSignGenes);
        enrichValues.setSignGenes(genesWithFcSignif);
        objects.add(enrichValues);


        return objects;
    }


    //TODO check (x) finished (x)
    public static HashMap<String, goEntry> returnMapWithGeneAsHashSet(HashMap<String, goEntry> mapVON) {
        HashMap<String, goEntry> mapZU = new HashMap<>();

        for (String goID : mapVON.keySet()) {
            if (!(mapVON.get(goID).getGenesSet() == null)) {
                goEntry goEntry = mapVON.get(goID);
                goEntry.setGenesSet(new HashSet<>(mapVON.get(goID).getGenesSet()));
                mapZU.put(goID, goEntry);
            }
        }
        return mapZU;
    }


    public static void runOverlapAndEnrichmentAnalysis(String outPath, String oboPath, String
            mappingFile, String mappingType, String root, String enrichFilePath, int minsize, int maxsize, String overlapout) throws IOException {
        rootMapWithValues rootMapWithValues = generateRootMapBasedOnMappingType(oboPath, mappingFile, mappingType, root, enrichFilePath);

//        overlap.generateOverlapEntryAndPrint(rootMapWithValues.getRootMap(), minsize, maxsize, overlapout);
        output.generateOuputs(outPath, rootMapWithValues);

    }

//    public static void main(String[] args) throws IOException {
//        String oboPath = "/home/issra/Schreibtisch/GOBI/GO_Files/go.obo";
//        String goOrEnsemblPath = "/home/issra/Schreibtisch/GOBI/GO_Files/goa_human.gaf.gz";
//        String mappingType = "go";
//        String root = "biological_process";
//        String enrichFilePath = "/home/issra/Schreibtisch/simul_exp_go_bp_ensembl.tsv";
//        String overlapout = "./overlapout.txt";
//
//        PrintStream fileOut = new PrintStream("./out.txt");
//        System.setOut(fileOut);

//
//
//    }

/*TODO how to print out a HashMap
private HashMap<String, ArrayList<String>> gosWithGenesList = new HashMap<>();

goEntries1.forEach((key, value) -> System.out.println(key + " " + value));
    OR
gosWithGenesList.entrySet().forEach(entry->{
    System.out.println(entry.getKey() + " " + entry.getValue());
 });
*/

}