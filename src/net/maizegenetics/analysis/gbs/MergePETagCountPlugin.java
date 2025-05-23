/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.maizegenetics.analysis.gbs;

import net.maizegenetics.dna.tag.PETagCounts;
import net.maizegenetics.dna.tag.TagsByTaxa.FilePacking;
import net.maizegenetics.plugindef.AbstractPlugin;
import net.maizegenetics.plugindef.DataSet;
import net.maizegenetics.util.ArgsEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Merge PETagCounts file of each taxon into one master PETagCounts file (collapsed and sorted)
 * @author Fei Lu
 */
public class MergePETagCountPlugin extends AbstractPlugin {
    static long timePoint1;
    private ArgsEngine engine = null;
    private Logger logger = LogManager.getLogger(MergePETagCountPlugin.class);
    private String inputDirS = null;
    private String outputFileS = null;
    
    public MergePETagCountPlugin() {
        super(null, false);
    }

    public MergePETagCountPlugin(Frame parentFrame) {
        super(parentFrame, false);
    }

    private void printUsage() {
        logger.info(
                "\n\nUsage is as follows:\n"
                + " -i  input directory containing .pe.cnt for each taxa\n"
                + " -o  output filename for merged PETagCounts file\n");
    }

    @Override
    public DataSet performFunction(DataSet input) {
        mergePETagCount();
        return null;
    }
    
    /**
     * Merge each individual PETagCounts file into one master PETagCounts file
     */
    public void mergePETagCount () {
        File[] infiles = new File(inputDirS).listFiles();
        PETagCounts prime = new PETagCounts(infiles[0].getAbsolutePath(), FilePacking.Byte).getCollapsedPETagCounts();
        for (int i = 1; i < infiles.length; i++) {
            prime = prime.getMergedPETagCounts(new PETagCounts(infiles[i].getAbsolutePath(), FilePacking.Byte), false);
        }
        prime.writeDistFile(outputFileS, FilePacking.Byte, 0);
    }
    
    @Override
    public void setParameters(String[] args) {
        if (args.length == 0) {
            printUsage();
            throw new IllegalArgumentException("\n\nPlease use the above arguments/options.\n\n");
        }

        if (engine == null) {
            engine = new ArgsEngine();
            engine.add("-i", "--input-directory", true);
            engine.add("-o", "--output-file", true);
            engine.parse(args);
        }

        if (engine.getBoolean("-i")) {
            inputDirS = engine.getString("-i");
        } else {
            printUsage();
            throw new IllegalArgumentException("Please specify the input directory of PETagCounts files");
        }

        if (engine.getBoolean("-o")) {
            outputFileS = engine.getString("-o");
        } else {
            printUsage();
            throw new IllegalArgumentException("Please specify the merged PETagCounts file");
        }
        
    }
    
    @Override
    public ImageIcon getIcon() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getButtonName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getToolTipText() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
