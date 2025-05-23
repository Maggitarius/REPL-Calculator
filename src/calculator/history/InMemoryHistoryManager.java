package calculator.history;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<String> history = new ArrayList<>();
    private Double lastResult = null;

    @Override
    public void addRecord(String record) {
        history.add(record);
    }

    @Override
    public List<String> getHistory() {
        return new ArrayList<>(history);
    }

    @Override
    public void clearHistory() {
        history.clear();
    }

    @Override
    public Double getLastResult() {
        return lastResult;
    }

    @Override
    public void setLastResult(Double result) {
        this.lastResult = result;
    }

    @Override
    public void clearLastResult() {
        this.lastResult = null;
    }
}
