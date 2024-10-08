package application;

import config.LocalizationConfig;
import domain.Direction;
import domain.Lawn;
import domain.MowerData;
import org.junit.jupiter.api.Test;
import ports.InputPort;
import ports.OutputPort;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.mockito.Mockito.*;

class MowerServiceTest {

    @Test
    void testExecuteMowers() {

        InputPort inputPort = mock(InputPort.class);
        OutputPort outputPort = mock(OutputPort.class);
        Lawn lawn = new Lawn(5, 5);

        MowerData mowerData = new MowerData(1, 2, Direction.N, "GAGAGAGAA");
        List<MowerData> mowerDataList = List.of(mowerData);
        when(inputPort.loadMowers()).thenReturn(mowerDataList);

        MowerService mowerService = new MowerService(lawn, inputPort, outputPort, 3, new LocalizationConfig(Locale.ENGLISH));
        mowerService.execute();

        verify(outputPort, times(1)).writeResults(List.of("1 3 N"));
    }
}