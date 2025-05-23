package calculator.history;

import java.util.List;

public interface HistoryManager {
    void addRecord(String record);
    List<String> getHistory();
    void clearHistory();

    Double getLastResult();
    void setLastResult(Double result);
    void clearLastResult();
}
