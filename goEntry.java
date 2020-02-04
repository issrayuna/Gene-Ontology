import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class goEntry {
    public String id;
    public String name;
    public String namespace;
    public boolean isEnriched;
    public boolean root;
    public boolean isObsolete;
    public int measuredGenesSize;//size //TODO ()
    public int numOfSignificantGenes;//noverlap //TODO (x)

    public void setMeasuredGenesSize(int measuredGenesSize) {
        this.measuredGenesSize = measuredGenesSize;
    }

    public void setNumOfSignificantGenes(int numOfSignificantGenes) {
        this.numOfSignificantGenes = numOfSignificantGenes;
    }

    public ArrayList<String> parents;

    public HashSet<Gene> genesSet;

    public HashSet<Gene> getGenesSet() {
        return genesSet;
    }

    public void setGenesSet(HashSet<Gene> genesSet) {
        this.genesSet = genesSet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setEnriched(boolean enriched) {
        isEnriched = enriched;
    }


    public void setRoot(boolean root) {
        this.root = root;
    }

    public ArrayList<String> getParents() {
        return parents;
    }

    public void setParents(ArrayList<String> parents) {
        this.parents = parents;
    }

    public boolean isObsolete() {
        return isObsolete;
    }

    public void setObsolete(boolean obsolete) {
        isObsolete = obsolete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof goEntry)) return false;
        goEntry goEntry = (goEntry) o;
        return getId().equals(goEntry.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "goEntry{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", namespace='" + namespace + '\'' +
                ", isEnriched=" + isEnriched +
                ", root=" + root +
                ", isObsolete=" + isObsolete +
                ", size=" + measuredGenesSize +
                ", noverlap=" + numOfSignificantGenes +
                ", parents=" + parents +
                ", genesSet=" + genesSet +
                '}';
    }
}
