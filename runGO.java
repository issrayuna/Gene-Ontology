import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class runGO {

    public static void main(String[] args) {
        Option obo = new Option("obo", "obo", true, "obo file path");
        Option root = new Option("root", "root", true, "specifiy which namespace to use");
        Option mapping = new Option("mapping", "mapping", true, "mapping file path");
        Option mappingtype = new Option("mappingtype", "mappingtype", true, "mappingtype: ensemble or go");
        Option overlapout = new Option("overlapout", "overlapout", true, "output of ");
        Option enrich = new Option("enrich", "enrich", true, "simulation file path for enrichment analysis");
        Option minsize = new Option("minsize", "minsize", true, "minimal number of overlapping genes");
        Option maxsize = new Option("maxsize", "maxsize", true, "maximal number of overlapping genes");
        Option o = new Option("o", "o", true, "output file path [tsv]");

        obo.setRequired(true);
        root.setRequired(true);
        mapping.setRequired(true);
        mappingtype.setRequired(true);
        overlapout.setRequired(false);
        enrich.setRequired(true);
        minsize.setRequired(true);
        maxsize.setRequired(true);
        o.setRequired(true);

        Options options = new Options();
        options.addOption(obo);
        options.addOption(root);
        options.addOption(mapping);
        options.addOption(mappingtype);
        options.addOption(overlapout);
        options.addOption(enrich);
        options.addOption(minsize);
        options.addOption(minsize);
        options.addOption(maxsize);
        options.addOption(o);


        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);
            String oboPath = cmd.getOptionValue("obo");
            String rootOpt = cmd.getOptionValue("root");
            String mappingFilePath = cmd.getOptionValue("mapping");
            String mappingType = cmd.getOptionValue("mappingtype");
            String overlapOut = cmd.getOptionValue("overlapout");
            String enrichFile = cmd.getOptionValue("enrich");
            int minSize = Integer.parseInt(cmd.getOptionValue("minsize"));
            int maxSize = Integer.parseInt(cmd.getOptionValue("maxsize"));
            String outPath = cmd.getOptionValue("o");


            reader.runOverlap(oboPath, mappingFilePath, mappingType, rootOpt, enrichFile, minSize, maxSize, overlapOut);







        } catch (ParseException | IOException pe) {
            pe.printStackTrace();
        }


    }
}















