import java.util.HashSet;
import java.util.Objects;

public class Gene {
    public String id;
    public double fc;
    public boolean signif;

    public HashSet<String> goEntryHashSet;

    public Gene(String id, double fc, boolean signif) {
        this.id = id;
        this.fc = fc;
        this.signif = signif;
    }

    public Gene() {
    }

    public Gene(String id, double fc, boolean signif, HashSet<String> goEntryHashSet) {
        this.id = id;
        this.fc = fc;
        this.signif = signif;
        this.goEntryHashSet = goEntryHashSet;
    }

    public HashSet<String> getGoEntryHashSet() {
        return goEntryHashSet;
    }

    public void setGoEntryHashSet(HashSet<String> goEntryHashSet) {
        this.goEntryHashSet = goEntryHashSet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getFc() {
        return fc;
    }

    public void setFc(double fc) {
        this.fc = fc;
    }

    public boolean isSignif() {
        return signif;
    }

    public void setSignif(boolean signif) {
        this.signif = signif;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gene)) return false;
        Gene gene = (Gene) o;
        return id.equals(gene.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Gene{" +
                "id='" + id + '\'' +
                ", fc=" + fc +
                ", signif=" + signif +
                ", goEntryHashSet=" + goEntryHashSet +
                '}';
    }


}
