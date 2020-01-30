import java.util.ArrayList;
import java.util.HashMap;

public class goEntry {
    public String id;
    public String name;
    public String namespace;
    public boolean isTrue;
    public boolean root;
    public boolean isObsolete;

    public ArrayList<String> parents;
    public ArrayList<String> genes;
    public HashMap<String, goEntry> mapOfGOs;

    public HashMap<String, goEntry> getMapOfGOs() {
        return mapOfGOs;
    }

    public void setMapOfGOs(HashMap<String, goEntry> mapOfGOs) {
        this.mapOfGOs = mapOfGOs;
    }

    public goEntry() {
    }

    /*public goEntry(String id, String name, String namespace, boolean isTrue, boolean root, ArrayList<goEntry> parents, ArrayList<String> genes) {
        this.id = id;
        this.name = name;
        this.namespace = namespace;
        this.isTrue = isTrue;
        this.root = root;
        this.parents = parents;
        this.genes = genes;
    }*/

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

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
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
                ", isTrue=" + isTrue +
                ", root=" + root +
                ", isObsolete=" + isObsolete +
                ", parents=" + parents +
                ", genes=" + genes +
                '}';
    }
}
