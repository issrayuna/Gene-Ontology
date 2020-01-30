import it.unimi.dsi.fastutil.floats.Float2ObjectRBTreeMap;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class reader {
    //public ArrayList<goEntry> goEntries; //String id


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
                    genes.add(new GenesWithGoList(cols[0], new ArrayList<>(Arrays.asList(gos))));//geneID, listOfGOs
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
        int containsNum = 0;
        for (String goID1 : gosWithGenesListVON.keySet()) {
            ArrayList<String> genes1 = gosWithGenesListVON.get(goID1);

            if (mapZU.containsKey(goID1)) {
                goEntry goEntry = mapZU.get(goID1);
                //add list of genes to map.goEntry Object
                goEntry.setGenes(genes1);
                //update map.goEntry
                mapZU.put(goID1, goEntry);
                containsNum++;
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


    public static void main(String[] args) throws FileNotFoundException {
        String path = "/home/issra/Desktop/GO_Files/go.obo";
        ArrayList<goEntry> goEntries = generateGoEntriesFromOboFile(path);
        goEntries = removeObsolete(goEntries);//! main hashmap
        String ensemblPath = "/home/issra/Schreibtisch/goa_human_ensembl.txt";
        ArrayList<GenesWithGoList> genesWithGoLists = generateGeneListFromEnsembleTsv(ensemblPath);
        HashMap<String, ArrayList<String>> gosWithGenesList = generateGoWithGenesList(genesWithGoLists);

        HashMap<String, goEntry> goEntries1 = addGenesToGoEntries(goEntries, gosWithGenesList);
        //goEntries1.forEach((key, value) -> System.out.println(key + " " + value));

    }
/*private HashMap<String, ArrayList<String>> gosWithGenesList = new HashMap<>();

for (String name: gosWithGenesList.keySet()){
            String key = name.toString();
            String value = gosWithGenesList.get(name).toString();
            System.out.println(key + " " + value);
}

Update for Java8:

 gosWithGenesList.entrySet().forEach(entry->{
    System.out.println(entry.getKey() + " " + entry.getValue());
 });
*/

}
