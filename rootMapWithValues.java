import java.util.HashMap;

public class rootMapWithValues {
    public HashMap<String, goEntry> rootMap;
    public enrichValues enrichValues;
    public int numOfAllGenes;


    public int getNumOfAllGenes() {
        return numOfAllGenes;
    }

    public void setNumOfAllGenes(int numOfAllGenes) {
        this.numOfAllGenes = numOfAllGenes;
    }

    public HashMap<String, goEntry> getRootMap() {
        return rootMap;
    }

    public void setRootMap(HashMap<String, goEntry> rootMap) {
        this.rootMap = rootMap;
    }

    public enrichValues getEnrichValues() {
        return enrichValues;
    }

    public void setEnrichValues(enrichValues enrichValues) {
        this.enrichValues = enrichValues;
    }
}
