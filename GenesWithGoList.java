import java.util.ArrayList;

public class GenesWithGoList {
    public String geneID;
    public ArrayList<String> gos;

    public GenesWithGoList(String geneID, ArrayList<String> gos) {
        this.geneID = geneID;
        this.gos = gos;
    }

    public String getGeneID() {
        return geneID;
    }

    public void setGeneID(String geneID) {
        this.geneID = geneID;
    }

    public ArrayList<String> getGos() {
        return gos;
    }

    public void setGos(ArrayList<String> gos) {
        this.gos = gos;
    }

    @Override
    public String toString() {
        return "GenesWithGoList{" +
                "geneID='" + geneID + '\'' +
                ", gos=" + gos +
                '}';
    }
}
