import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class overlap {

    public goEntry goEntry1;
    public goEntry goEntry2;
    public boolean isRelative;
    public int pathLength;//TODO ()
    public int numOverlapping;
    public double maxOvPercent;

    public overlap(goEntry goEntry1, goEntry goEntry2) {
        this.goEntry1 = goEntry1;
        this.goEntry2 = goEntry2;
    }


    public void setRelative(boolean relative) {
        isRelative = relative;
    }

    public int getPathLength() {
        return pathLength;
    }

    public void setPathLength(int pathLength) {
        this.pathLength = pathLength;
    }


    public void setNumOverlapping(int numOverlapping) {
        this.numOverlapping = numOverlapping;
    }


    public void setMaxOvPercent(double maxOvPercent) {
        this.maxOvPercent = maxOvPercent;
    }

    //TODO check (x) finished (x)
    public static boolean checkIfRelative(goEntry goEntry1, goEntry goEntry2) {
        //true if goEntry1 is child OR parent of goEntry2
        // -> goEntry1.parents.contains(goEntry2) => goEntry1 is child of goEntry2
        // -> goEntry2.parents.contains(goEntry1) => goEntry1 is parent of goEntry2

        if (goEntry1.getParents().contains(goEntry2.getId())) {
            return true;
        } else if (goEntry2.getParents().contains(goEntry1.getId())) {
            return true;
        } else {
            return false;
        }
    }


    //TODO check (x) finished (x)
    public static int calcOverlap(goEntry goEntry1, goEntry goEntry2) {
        int sumOv = 0;

        if (goEntry1.getGenesSet() != null && goEntry2.getGenesSet() != null) {
            ArrayList<String> geneIDs1 = new ArrayList<>();
            ArrayList<String> geneIDs2 = new ArrayList<>();
            for (Gene g1 : goEntry1.getGenesSet()) {
                geneIDs1.add(g1.getId());
            }
            for (Gene g2 : goEntry2.getGenesSet()) {
                geneIDs2.add(g2.getId());
            }

            for (String g1 : geneIDs1) {
                for (String g2 : geneIDs2) {
                    if (g1.equals(g2)) {
                        sumOv++;
                    }
                }
            }
            return sumOv;

        } else {
            return 0;
        }
    }


    //TODO check (x) finished (x)
    public static float calcOverlapPercent(HashSet<Gene> genes1, HashSet<Gene> genes2, int numOverlapping) {
        return Math.max(((numOverlapping / (float) genes1.size()) * 100), ((numOverlapping / (float) genes2.size()) * 100));
    }

    //TODO check (x) finished (x)
    public static void printOverlapEntriesToFile(String file, ArrayList<overlap> overlapEntries) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write("term1\tterm2\tis_relative\tpath_length\tnum_overlapping\tmax_ov_percent\n");
            for (overlap o : overlapEntries) {
                bw.write(o.toString());
            }
            bw.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


    }

    //TODO check () finished ()
    public static void calcPath(goEntry goEntry1, goEntry goEntry2) {

    }


    //TODO check () finished ()
    public static void generateOverlapEntryAndPrint(HashMap<String, goEntry> map, int minsize, int maxsize, String overlapout) {
        ArrayList<overlap> overlapEntries = new ArrayList<>();
        for (goEntry goEntry1 : map.values()) {
            for (goEntry goEntry2 : map.values()) {
                if (!goEntry1.equals(goEntry2)) {
                    int numOverlap = calcOverlap(goEntry1, goEntry2);
                    if (numOverlap > 0 && numOverlap > minsize && numOverlap < maxsize) {
                        overlap overlap = new overlap(goEntry1, goEntry2);
                        overlap.setNumOverlapping(numOverlap);
                        overlap.setRelative(checkIfRelative(goEntry1, goEntry2));
                        overlap.setMaxOvPercent(calcOverlapPercent(goEntry1.getGenesSet(), goEntry2.getGenesSet(), numOverlap));

                        overlap.setPathLength(0);

                        overlapEntries.add(overlap);
                    }
                }
            }
        }
        printOverlapEntriesToFile(overlapout, overlapEntries);
    }

    @Override
    public String toString() {
        return goEntry1.getId() + "\t" +
                goEntry2.getId() + "\t" +
                isRelative + "\t" +
                pathLength + "\t" +
                numOverlapping + "\t" +
                String.format(java.util.Locale.US, "%.2f", maxOvPercent) + "\n";
    }
}
