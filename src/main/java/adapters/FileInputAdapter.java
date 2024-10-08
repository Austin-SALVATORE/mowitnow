package adapters;

import config.LocalizationConfig;
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
    private final LocalizationConfig localization;
    private final String fileName;

    public FileInputAdapter(String fileName, LocalizationConfig localization) {
        this.fileName = fileName;
        this.localization = localization;
    }

    @Override
    public List<MowerData> loadMowers() {
        List<MowerData> mowerDataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(fileName))))) {
            String lawnDimensionsLine = br.readLine();
            if (lawnDimensionsLine == null || lawnDimensionsLine.trim().isEmpty()) {
                throw new IllegalArgumentException(localization.getMessage("error.invalid.lawn.dimensions"));
            }

            String[] lawnDimensions = lawnDimensionsLine.split(" ");
            if (lawnDimensions.length != 2) {
                throw new IllegalArgumentException(localization.getMessage("error.invalid.lawn.dimensions"));
            }
// Make sure there is not invalid lawn dimensions
            int maxX;
            int maxY;
            try {
                maxX = Integer.parseInt(lawnDimensions[0]);
                maxY = Integer.parseInt(lawnDimensions[1]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(localization.getMessage("error.invalid.lawn.dimensions"), e);
            }

            // Read each mower's initial position and instructions
            String line;
            while ((line = br.readLine()) != null) {
                // Parse the mower's initial position (e.g., "1 2 N")
                String[] initialPosition = line.split(" ");
                if (initialPosition.length != 3) {
                    throw new InvalidMowerPositionException(localization.getMessage("error.invalid.mower.position"));
                }

                int x;
                int y;
                Direction orientation;
                try {
                    x = Integer.parseInt(initialPosition[0]);
                    y = Integer.parseInt(initialPosition[1]);
                    orientation = Direction.valueOf(initialPosition[2]);
                } catch (IllegalArgumentException e) {
                    throw new InvalidMowerPositionException(localization.getMessage("error.invalid.mower.position"));
                }
                String instructions = br.readLine();
                if (instructions == null || instructions.trim().isEmpty()) {
                    throw new InvalidInstructionException(localization.getMessage("error.invalid.instructions"));
                }

                // Add the mower data to the list
                mowerDataList.add(new MowerData(x, y, orientation, instructions));
            }
        } catch (FileNotFoundException e) {
            logger.error(localization.getMessage("error.file.not.found", fileName));
        } catch (IOException e) {
            throw new RuntimeException(localization.getMessage("error.file.read")+ fileName, e);
        }
        return mowerDataList;
    }
}
