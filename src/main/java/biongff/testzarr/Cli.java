/*
 * Copyright (C) 2025 University of Dundee & Open Microscopy Environment.
 * All rights reserved.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package biongff.testzarr;

import java.io.IOException;
import java.nio.file.Path;
import ucar.ma2.InvalidRangeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(
    name = "testzarr",
    mixinStandardHelpOptions = true,
    version = "1.0",
    description = "Command line tool for creating NGFF test Zarrs. You can " +
            "create < 5d images by either setting c/t/z to 0 or removing the dimensions " +
            "from the order."
)
public class Cli implements Runnable {
    
    private static final Logger logger = LoggerFactory.getLogger(Cli.class);
    
    @Parameters(index = "0", description = "Output file path (e.g., ./test.zarr)")
    private String filePath;

    @Option(names = {"-x"}, description = "Width of the image (default: 512)")
    private int sizeX = 512;

    @Option(names = {"-y"}, description = "Height of the image (default: 256)")
    private int sizeY = 256;

    @Option(names = {"-z"}, description = "Number of Z planes (default: 5)")
    private int sizeZ = 5;

    @Option(names = {"-t"}, description = "Number of timepoints (default: 10)")
    private int sizeT = 10;

    @Option(names = {"-c"}, description = "Number of channels (default: 3)")
    private int sizeC = 3;

    @Option(names = {"-o"}, description = "Dimension order (default: TCZYX)")
    private String order = "TCZYX";

    @Override
    public void run() {
        if (filePath != null) {
            logger.info("Creating Zarr file at: {}", filePath);
            try {
                new TestZarr()
                    .setPath(Path.of(filePath))
                    .setOverwrite(true)
                    .setSizeX(sizeX)
                    .setSizeY(sizeY)
                    .setSizeZ(sizeZ)
                    .setSizeT(sizeT)
                    .setSizeC(sizeC)
                    .setOrder(order)
                    .init()
                    .createImage()
                    .createMetadata();
                logger.info("Successfully created Zarr file at: {}", filePath);
            } catch (IOException e) {
                logger.error("Error processing file: {}", e.getMessage(), e);
            } catch (InvalidRangeException e) {
                logger.error("Error with data ranges: {}", e.getMessage(), e);
            }
        } else {
            logger.error("No file path specified");
        }
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Cli()).execute(args);
        System.exit(exitCode);
    }
}
