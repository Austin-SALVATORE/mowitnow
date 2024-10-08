package ports;

import domain.MowerData;

import java.util.List;

public interface InputPort {
    List<MowerData> loadMowers();
}
