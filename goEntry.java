import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class goEntry {
    public String id;
    public String name;
    public String namespace;
    public boolean isEnriched;
    public boolean root;
    public boolean isObsolete;

    public ArrayList<String> parents;
    public ArrayList<String> genes;

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

    public String getName() {
        return name;
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

    public boolean isEnriched() {
        return isEnriched;
    }

    public void setEnriched(boolean enriched) {
        isEnriched = enriched;
    }

    public boolean isRoot() {
        return root;
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

    public ArrayList<String> getGenes() {
        return genes;
    }

    public void setGenes(ArrayList<String> genes) {
        this.genes = genes;
    }

    public boolean isObsolete() {
        return isObsolete;
    }

    public void setObsolete(boolean obsolete) {
        isObsolete = obsolete;
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
                ", parents=" + parents +
                ", genes=" + genes +
                ", genesSet=" + genesSet +
                '}';
    }
}
