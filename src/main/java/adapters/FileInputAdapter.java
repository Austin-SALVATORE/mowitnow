package adapters;

import domain.Direction;
import domain.MowerData;
import exceptions.InvalidInstructionException;
import exceptions.InvalidMowerPositionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.InputPort;

import java.io.*;
import java.util.*;

public class FileInputAdapter implements InputPort {
    private static final Logger logger = LoggerFactory.getLogger(FileInputAdapter.class);
    private final String fileName;

    public FileInputAdapter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<MowerData> loadMowers() {
        List<MowerData> mowerDataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(fileName))))) {
            String lawnDimensionsLine = br.readLine();
            if (lawnDimensionsLine == null || lawnDimensionsLine.trim().isEmpty()) {
                throw new IllegalArgumentException("Lawn dimensions missing or empty in the input file.");
            }

            String[] lawnDimensions = lawnDimensionsLine.split(" ");
            if (lawnDimensions.length != 2) {
                throw new IllegalArgumentException("Lawn dimensions should contain exactly two values (e.g., '5 5').");
            }
// Make sure there is not invalid lawn dimensions
            int maxX;
            int maxY;
            try {
                maxX = Integer.parseInt(lawnDimensions[0]);
                maxY = Integer.parseInt(lawnDimensions[1]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid lawn dimensions. Must be integers.", e);
            }

            // Read each mower's initial position and instructions
            String line;
            while ((line = br.readLine()) != null) {
                // Parse the mower's initial position (e.g., "1 2 N")
                String[] initialPosition = line.split(" ");
                if (initialPosition.length != 3) {
                    throw new InvalidMowerPositionException("Mower's initial position must contain exactly two coordinates and one orientation.");
                }

                int x;
                int y;
                Direction orientation;
                try {
                    x = Integer.parseInt(initialPosition[0]);
                    y = Integer.parseInt(initialPosition[1]);
                    orientation = Direction.valueOf(initialPosition[2]);
                } catch (IllegalArgumentException e) {
                    throw new InvalidMowerPositionException("Invalid mower initial position or orientation. Coordinates must be integers, and orientation must be one of [N, E, S, W].");
                }
                String instructions = br.readLine();
                if (instructions == null || instructions.trim().isEmpty()) {
                    throw new InvalidInstructionException("Mower's instructions missing or empty.");
                }

                // Add the mower data to the list
                mowerDataList.add(new MowerData(x, y, orientation, instructions));
            }
        } catch (FileNotFoundException e) {
            logger.error("Error: The file '{}' could not be found in the resources folder.", fileName);
        } catch (IOException e) {
            throw new RuntimeException("Error reading the input file: " + fileName, e);
        }
        return mowerDataList;
    }
}
