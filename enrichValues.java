import java.util.ArrayList;
import java.util.HashSet;

public class enrichValues {
    public int numOfAllSignGenes;
    public HashSet<Gene> signGenes;
    public ArrayList<Double> measuredGenesFC;

    public ArrayList<Double> getMeasuredGenesFC() {
        return measuredGenesFC;
    }

    public void setMeasuredGenesFC(ArrayList<Double> measuredGenesFC) {
        this.measuredGenesFC = measuredGenesFC;
    }

    public HashSet<String> enrichedGoIds;

//    public int numOfGenes;
//    public HashSet<Gene> notSignGenes;
//    public int getNumOfGenes() {
//        return numOfGenes;
//    }
//
//    public void setNumOfGenes(int numOfGenes) {
//        this.numOfGenes = numOfGenes;
//    }
//    public HashSet<Gene> getNotSignGenes() {
//        return notSignGenes;
//    }
//
//    public void setNotSignGenes(HashSet<Gene> notSignGenes) {
//        this.notSignGenes = notSignGenes;
//    }

    public int getNumOfAllSignGenes() {
        return numOfAllSignGenes;
    }

    public void setNumOfAllSignGenes(int numOfAllSignGenes) {
        this.numOfAllSignGenes = numOfAllSignGenes;
    }

    public HashSet<Gene> getSignGenes() {
        return signGenes;
    }

    public void setSignGenes(HashSet<Gene> signGenes) {
        this.signGenes = signGenes;
    }

    public HashSet<String> getEnrichedGoIds() {
        return enrichedGoIds;
    }

    public void setEnrichedGoIds(HashSet<String> enrichedGoIds) {
        this.enrichedGoIds = enrichedGoIds;
    }
}
