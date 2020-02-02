import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.zip.GZIPInputStream;

public class reader {

    //TODO check (x) finished (x)
    public static ArrayList<HashMap<String, goEntry>> generateRootMapsFromOneMap(HashMap<String, goEntry> mapVON) {
        //reader r = new reader();
        ArrayList<HashMap<String, goEntry>> rere = new ArrayList<>();
        HashMap<String, goEntry> biological_process = new HashMap<>();
        HashMap<String, goEntry> cellular_component = new HashMap<>();
        HashMap<String, goEntry> molecular_function = new HashMap<>();

        for (String goID : mapVON.keySet()) {
            goEntry goEntry = mapVON.get(goID);
            if (goEntry.getParents().isEmpty()) {
                goEntry.setRoot(true);
            }
            switch (goEntry.getNamespace()) {
                case "biological_process":
                    biological_process.put(goID, goEntry);
                    break;
                case "cellular_component":
                    cellular_component.put(goID, goEntry);
                    break;
                case "molecular_function":
                    molecular_function.put(goID, goEntry);
                    break;
            }
        }
        rere.add(biological_process);
        rere.add(cellular_component);
        rere.add(molecular_function);
        return rere;
        //r.setBiological_process(biological_process);
        //r.setCellular_component(cellular_component);
        //r.setMolecular_function(molecular_function);
    }

    //TODO check (x) finished (x)
    public static Gene retGeneWithSameId(HashSet<Gene> geneSet, String id) {
        if (geneSet.stream().filter(x -> x.getId().equals(id)).findFirst().isPresent())
            return geneSet.stream().filter(x -> x.getId().equals(id)).findFirst().get();
        else
            return null;
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
                g.setFc(g2.getFc());
                g.setSignif(g2.isSignif());
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
    public static HashMap<String, goEntry> addGenesToGoEntries(HashMap<String, goEntry> mapZU, HashMap<String, HashSet<Gene>> gosWithGenesListVON) {
        for (String goID1 : gosWithGenesListVON.keySet()) {
            HashSet<Gene> genes1 = gosWithGenesListVON.get(goID1);
            if (mapZU.containsKey(goID1)) {
                goEntry goEntry = mapZU.get(goID1);
                goEntry.setGenesSet(genes1);
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

    //TODO check (x) finished (x)
    public static HashMap<String, goEntry> generateRootMapBasedOnMappingType(String oboPath, String
            mappingFile, String mappingType, String root, String enrichFilePath) throws IOException {


        ArrayList<Object> objects = readEnrichFile2(enrichFilePath);
        ArrayList<String> enrichedGOs = (ArrayList<String>) objects.get(0);
        HashSet<Gene> genesWithFcSignif = (HashSet<Gene>) objects.get(1);


        HashMap<String, goEntry> rootMap = new HashMap<>();
        HashMap<String, goEntry> goEntries = generateGoEntriesFromOboFile(oboPath, enrichedGOs);
        HashSet<Gene> genesWithGoLists = new HashSet<>();
        if (mappingType.equals("go")) {
            genesWithGoLists = generateGeneSetFromGafGO(mappingFile, genesWithFcSignif);
        } else if (mappingType.equals("ensembl")) {
            genesWithGoLists = generateGeneSetFromEnsembleTsv(mappingFile, genesWithFcSignif);
        }

        HashMap<String, HashSet<Gene>> gosWithGenesList = generateGoMapWithGeneSet(genesWithGoLists);
        HashMap<String, goEntry> goEntries1 = addGenesToGoEntries(goEntries, gosWithGenesList);
        ArrayList<HashMap<String, goEntry>> r = generateRootMapsFromOneMap(goEntries1);
        switch (root) {
            case "biological_process":
                return r.get(0);
            case "cellular_component":
                return r.get(1);
            case "molecular_function":
                r.get(2);
                break;
        }
        rootMap = returnMapWithGeneAsHashSet(rootMap);


        return rootMap;
    }

    //TODO check (x) finished (x)
    public static ArrayList<Object> readEnrichFile2(String enrichmentFilePath) throws IOException {
        ArrayList<Object> objects = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(enrichmentFilePath));
        String line;

        ArrayList<String> enrichedGOs = new ArrayList<>();
        HashSet<Gene> genesWithFcSignif = new HashSet<>();


        while ((line = br.readLine()) != null) {
            if (line.startsWith("#")) {
                enrichedGOs.add(line.substring(1));
            } else if (!line.startsWith("id")) {
                String[] splitted = line.split("\t");
                genesWithFcSignif.add(new Gene(splitted[0], Double.parseDouble(splitted[1]), splitted[2].equals("true")));
            }
        }
        objects.add(enrichedGOs);
        objects.add(genesWithFcSignif);


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

    public static void main(String[] args) throws IOException {
        String oboPath = "/home/issra/Schreibtisch/GOBI/GO_Files/go.obo";
        String goOrEnsemblPath = "/home/issra/Schreibtisch/GOBI/GO_Files/goa_human_ensembl.tsv";
        String mappingType = "ensembl";
        String root = "biological_process";
        String enrichFilePath = "/home/issra/Schreibtisch/simul_exp_go_bp_ensembl.tsv";

        HashMap<String, goEntry> rootMap = generateRootMapBasedOnMappingType(oboPath, goOrEnsemblPath, mappingType, root, enrichFilePath);

        PrintStream fileOut = new PrintStream("./out.txt");
        System.setOut(fileOut);
        rootMap.forEach((key, value) -> System.out.println(key + " " + value));


    }

/*TODO how to print out a HashMap
private HashMap<String, ArrayList<String>> gosWithGenesList = new HashMap<>();

goEntries1.forEach((key, value) -> System.out.println(key + " " + value));
    OR
gosWithGenesList.entrySet().forEach(entry->{
    System.out.println(entry.getKey() + " " + entry.getValue());
 });
*/

}