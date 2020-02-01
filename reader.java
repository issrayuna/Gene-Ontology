import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

public class reader {

    HashMap<String, goEntry> biological_process;
    HashMap<String, goEntry> cellular_component;
    HashMap<String, goEntry> molecular_function;

    public HashMap<String, goEntry> getBiological_process() {
        return biological_process;
    }

    public void setBiological_process(HashMap<String, goEntry> biological_process) {
        this.biological_process = biological_process;
    }

    public HashMap<String, goEntry> getCellular_component() {
        return cellular_component;
    }

    public void setCellular_component(HashMap<String, goEntry> cellular_component) {
        this.cellular_component = cellular_component;
    }

    public HashMap<String, goEntry> getMolecular_function() {
        return molecular_function;
    }

    public void setMolecular_function(HashMap<String, goEntry> molecular_function) {
        this.molecular_function = molecular_function;
    }

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


    //TODO check () finished ()
    public static ArrayList<GenesWithGoList> generateGeneListFromGafGO(String goOptionPath) throws IOException {//TODO ArrayList<GenesWithGoList> instead of void
        //GZIPInputStream gzipInputStream = new GZIPInputStream(new FileInputStream(goOptionPath));
        HashMap<String, ArrayList<String>> genesWithGoList = new HashMap<>();
        ArrayList<GenesWithGoList> genes = new ArrayList<>();

        InputStream gzipStream = new GZIPInputStream(new FileInputStream(goOptionPath));
        BufferedReader br = new BufferedReader(new InputStreamReader(gzipStream));
        String line;

        try {
            String geneID, goID;

            while ((line = br.readLine()) != null) {

                if (!line.startsWith("!")) {
                    String[] cols = line.split("\t");
                    geneID = cols[2];
                    goID = cols[4];
                    if (!genesWithGoList.containsKey(geneID)) {
                        ArrayList<String> gos = new ArrayList<>();
                        gos.add(goID);
                        genesWithGoList.put(geneID, gos);
                    } else if (genesWithGoList.containsKey(geneID)) {
                        ArrayList<String> gos = genesWithGoList.get(geneID);
                        gos.add(goID);
                        genesWithGoList.put(geneID, gos);
                    }
                }

            }
            gzipStream.close();

            for (String geneId : genesWithGoList.keySet()) {
                GenesWithGoList genesWithGoList1 = new GenesWithGoList(geneId, genesWithGoList.get(geneId));
                genes.add(genesWithGoList1);
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


        return genes;
    }


    //TODO check (x) finished (x)
    public static ArrayList<goEntry> generateGoEntriesFromOboFile(String oboPath) {
        ArrayList<goEntry> obos = new ArrayList<>();

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
                        } else if (line.startsWith("name:")) {
                            goEntry.setName(line.substring(6));
                        } else if (line.startsWith("namespace")) {
                            goEntry.setNamespace(line.substring(11));
                        } else if (line.startsWith("is_a")) {
                            parents.add(line.substring(6, 16));
                        } else if (line.startsWith("is_obsolete: true")) {
                            goEntry.setObsolete(true);
                        } else if (line.isEmpty()) {
                            obos.add(goEntry);
                            break;
                        }
                        goEntry.setParents(parents);
                    }
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return obos;
    }

    //TODO check (x) finished (x)
    public static ArrayList<goEntry> removeObsolete(ArrayList<goEntry> goEntries) {
        ArrayList<goEntry> withoutObsolete = new ArrayList<>();
        for (int i = 0; i < goEntries.size(); i++) {
            if (!goEntries.get(i).isObsolete) {
                withoutObsolete.add(goEntries.get(i));
            }
        }
        return withoutObsolete;
    }

    //TODO check (x) finished (x)
    public static ArrayList<GenesWithGoList> generateGeneListFromEnsembleTsv(String ensemblePath) throws FileNotFoundException {
        ArrayList<GenesWithGoList> genes = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(ensemblePath));
        String line;

        try {
            while ((line = br.readLine()) != null) {
                if (line.startsWith("ENSG")) {
                    String[] cols = line.split("\t");
                    String[] gos = cols[2].split("\\|");
                    genes.add(new GenesWithGoList(cols[1], new ArrayList<>(Arrays.asList(gos))));//geneID [hgnc], listOfGOs
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return genes;
    }

    //TODO check (x) finished (x)
    public static HashMap<String, goEntry> addGenesToGoEntries(ArrayList<goEntry> goEntriesZU, HashMap<String, ArrayList<String>> gosWithGenesListVON) {
        HashMap<String, goEntry> mapZU = listToHashmap(goEntriesZU);

        for (String goID1 : gosWithGenesListVON.keySet()) {
            ArrayList<String> genes1 = gosWithGenesListVON.get(goID1);

            if (mapZU.containsKey(goID1)) {
                goEntry goEntry = mapZU.get(goID1);
                goEntry.setGenes(genes1);
                mapZU.put(goID1, goEntry);
            }
        }
//        System.out.println("containsNum: " + containsNum);
//        System.out.println("gosWithGenesListVON size: " + gosWithGenesListVON.size());
//        System.out.println("goEntriesZU size: " + goEntriesZU.size());
        return mapZU;
    }

    //TODO check (x) finished (x)
    public static HashMap<String, ArrayList<String>> generateGoWithGenesList(ArrayList<GenesWithGoList> geneAsKey) {
        HashMap<String, ArrayList<String>> map = new HashMap<>();//String -> GO ID & ArrayList -> of Gene IDs

        for (int i = 0; i < geneAsKey.size(); i++) {
            String geneID = geneAsKey.get(i).getGeneID();
            for (int j = 0; j < geneAsKey.get(i).getGos().size(); j++) {
                String goID = geneAsKey.get(i).getGos().get(j);
                if (map.containsKey(goID)) {
                    ArrayList<String> geneIDs = map.get(goID);
                    geneIDs.add(geneID);
                    map.put(goID, geneIDs);
                } else if (!map.containsKey(goID)) {
                    ArrayList<String> geneIDs = new ArrayList<>();
                    geneIDs.add(geneID);
                    map.put(goID, geneIDs);
                }
            }
        }

        return map;
    }


    //TODO check (x) finished (x)
    public static HashMap<String, goEntry> listToHashmap(ArrayList<goEntry> gosList) {
        HashMap<String, goEntry> goEntriesMap = new HashMap<>();
        for (int i = 0; i < gosList.size(); i++) {
            goEntriesMap.put(gosList.get(i).getId(), gosList.get(i));
        }
        return goEntriesMap;
    }


    public static void main(String[] args) throws IOException {
        String path = "/home/issra/Desktop/GO_Files/go.obo";
        ArrayList<goEntry> goEntries = generateGoEntriesFromOboFile(path);
        goEntries = removeObsolete(goEntries);
        String ensemblPath = "/home/issra/Schreibtisch/goa_human_ensembl.txt";
        ArrayList<GenesWithGoList> genesWithGoLists = generateGeneListFromEnsembleTsv(ensemblPath);
        HashMap<String, ArrayList<String>> gosWithGenesList = generateGoWithGenesList(genesWithGoLists);

        HashMap<String, goEntry> goEntries1 = addGenesToGoEntries(goEntries, gosWithGenesList);//! TODO main hashmap
        ArrayList<HashMap<String, goEntry>> r = generateRootMapsFromOneMap(goEntries1);
        HashMap<String, goEntry> rootMapBiol = r.get(0);
        HashMap<String, goEntry> rootMapCell = r.get(1);
        HashMap<String, goEntry> rootMapMol = r.get(2);

        String gafPath = "/home/issra/Schreibtisch/goa_human.gaf.gz";
        ArrayList<GenesWithGoList> genes = generateGeneListFromGafGO(gafPath);
        for (int i = 0; i < genes.size(); i++) {
            System.out.println(genes.get(i).toString());
        }
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







