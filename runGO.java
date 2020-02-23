import org.apache.commons.cli.*;

import java.io.IOException;


public class runGO {

    public static void main(String[] args) {

        Options options = new Options();

        options.addRequiredOption("obo", "obo", true, "obo file path");
        options.addRequiredOption("root", "root", true, "specifiy which namespace to use");
        options.addRequiredOption("mapping", "mapping", true, "mapping file path");
        options.addRequiredOption("mappingtype", "mappingtype", true, "mappingtype: ensemble or go");
        options.addOption("overlapout", "overlapout", true, "output of ");
        options.addRequiredOption("enrich", "enrich", true, "simulation file path for enrichment analysis");
        options.addRequiredOption("minsize", "minsize", true, "minimal number of overlapping genes");
        options.addRequiredOption("maxsize", "maxsize", true, "maximal number of overlapping genes");
        options.addRequiredOption("o", "o", true, "output file path [tsv]");

        try {

            CommandLine cmd = new DefaultParser().parse(options, args);
            String overlapOut = cmd.getOptionValue("overlapout");

            reader.runOverlapAndEnrichmentAnalysis(
                    cmd.getOptionValue("o"),
                    cmd.getOptionValue("obo"),
                    cmd.getOptionValue("mapping"),
                    cmd.getOptionValue("mappingtype"),
                    cmd.getOptionValue("root"),
                    cmd.getOptionValue("enrich"),
                    Integer.parseInt(cmd.getOptionValue("minsize")),
                    Integer.parseInt(cmd.getOptionValue("maxsize")),
                    overlapOut);


        } catch (ParseException | IOException pe) {
            pe.printStackTrace();
        }


    }
}















