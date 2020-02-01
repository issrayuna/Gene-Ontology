import java.util.HashSet;

public class Gene {
    public String id;
    public double fc;
    public boolean signif;

    public HashSet<String> goEntryHashSet;


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
}
