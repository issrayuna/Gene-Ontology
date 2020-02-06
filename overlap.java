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

    //TODO check () finished ()-----------------------------------------------------------------------------TRUE WHEN ASCENDANT/DESCENDENT NOT WHEN CHILD/PARENT
//    public static boolean checkIfRelative(goEntry goEntry1, goEntry goEntry2) {
//        //true if goEntry1 is child OR parent of goEntry2
//        // -> goEntry1.parents.contains(goEntry2) => goEntry1 is child of goEntry2 //TODO true when
//        // -> goEntry2.parents.contains(goEntry1) => goEntry1 is parent of goEntry2
//
//        if (goEntry1.getParents().contains(goEntry2.getId())) {
//            return true;
//        } else if (goEntry2.getParents().contains(goEntry1.getId())) {
//            return true;
//        } else {
//            return false;
//        }
//    }

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
    public static void calcShortestPath(HashMap<String, goEntry> map, String goID1, String goID2) {
        int pathLength = 0;


    }

    //TODO check (x) finished (x)
    public static HashMap<String, goEntry> addChildren(HashMap<String, goEntry> map) {
        HashMap<String, goEntry> newMap = new HashMap<>();

        for (String goID : map.keySet()) {
            if (!map.get(goID).getParents().isEmpty()) {
                for (String parent : map.get(goID).getParents()) {
                    if (map.get(parent) != null) {
                        goEntry goEntry = map.get(parent);
                        ArrayList<String> children;
                        if (goEntry.getChildren() == null) {
                            goEntry.setChildren(new ArrayList<>());
                        }
                        children = goEntry.getChildren();
                        children.add(goID);
                        goEntry.setChildren(children);
                        newMap.put(parent, goEntry);
                    }

                }
            }
        }
//        for (HashMap.Entry<String, goEntry> entry : map.entrySet()) {
//            if (!entry.getValue().isRoot()) {
//                goEntry go = entry.getValue();
//                for (String s : go.getParents()) {
//                    goEntry goEntry = map.get(s);
//                    goEntry.children.add(s);
//                    newMap.put(s, goEntry);
//                }
//            }
//        }

        return newMap;
    }

    //TODO check () finished () TEST--------------------------------------------------------OUTPUT ALL FROM RESUTLT.TAR.GZ
    public static boolean checkParentsAndChildren(HashMap<String, goEntry> map, String goID1, String goID2) {
        if (goID1.equals("GO:0006414") && goID2.equals("GO:0006412")) {
            System.out.println("GO:0006414 parents: " + map.get(goID1).getParents());
            System.out.println("GO:0006414 children: " + map.get(goID1).getChildren());
            System.out.println("GO:0006412 parents: " + map.get(goID2).getParents());
            System.out.println("GO:0006412 children: " + map.get(goID2).getChildren());
        }
        if (map.containsKey(goID1)) {
            ArrayList<String> parents = map.get(goID1).getParents();
            ArrayList<String> children = map.get(goID1).getChildren();
            if (parents != null) {
                if (parents.contains(goID2)) {
                    return true;
                } else {
                    for (String parent : parents) {
                        if (map.containsKey(parent)) {
                            if (!map.get(parent).isRoot()) {
                                return checkParentsAndChildren(map, parent, goID2);
                            }
                        } else return false;
                    }
                }
            } else if (children != null) {
                if (children.contains(goID2)) {
                    return true;
                } else {
                    for (String child : children) {
                        if (map.containsKey(child)) {
                            return checkParentsAndChildren(map, child, goID2);
                        } else return false;
                    }
                }
            } else return false;
        } else return false;

        return false;
    }


    //TODO check () finished (x)
//    public static boolean checkIfRelativeAscDesc(HashMap<String, goEntry> map, String goID1, String goID2) {
//        boolean isRl = false;
//
//        if (map.get(goID1) != null && map.get(goID2) != null) {
//            //TODO check direction of parents
//            if (map.get(goID1).getParents() != null) {
//                for (String parent : map.get(goID1).getParents()) {
//
//                    if (parent.equals(goID2)) {
//                        return true;
//                    } else if (map.get(parent) != null) {
//                        if (!map.get(parent).isRoot()) {
//                            checkIfRelativeAscDesc(map, parent, goID2);
//                        }
////                } else if (map.get(goID1).getChildren() != null) {
////                    if (map.get(goID1).getChildren().contains(goID2)) {
////                        return true;
////                    }
//                    } else {
//                        return false;
//                    }
//                }
//            }
//            //TODO check direction of children
//            if (map.get(goID1).getChildren() != null) {
//                for (String child : map.get(goID1).getChildren()) {
//                    if (child.equals(goID2)) {
//                        return true;
//                    } else if (map.get(child) != null) {
//                        checkIfRelativeAscDesc(map, child, goID2);
//                    } else {
//                        return false;
//                    }
//                }
//            }
//
//        }
//
////        if (map.get(goID2) != null && map.get(goID2).getParents() != null) {
////            for (String parent : map.get(goID2).getParents()) {
////                if (parent.equals(goID1)) {
////                    return true;
////                } else if (map.get(parent) != null) {
////                    if (!map.get(parent).isRoot()) {
////                        checkIfRelativeAscDesc(map, parent, goID1);
////                    }
////                } else if (map.get(goID2).getChildren() != null) {
////                    if (map.get(goID2).getChildren().contains(goID1)) {
////                        return true;
////                    }
////                } else {
////                    return false;
////                }
////            }
////        }
//        return isRl;
//    }

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
                        overlap.setRelative(checkParentsAndChildren(map, goEntry1.getId(), goEntry2.getId()));
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
