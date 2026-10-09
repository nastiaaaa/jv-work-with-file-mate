package core.basesyntax;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WorkWithFile {
    private static final int ZERO_DEFAULT_VALUE = 0;

    private static final int ZERO_INDEX = 0;
    private static final int FIRST_INDEX = 1;

    private static final String RESULT = "result";
    private static final String SUPPLY = "supply";
    private static final String BUY = "buy";

    private final Map<String, Integer> content = new HashMap<>();

    public void getStatistic(String fromFileName, String toFileName) {
        content.clear();
        getContent(fromFileName);
        fillFileWithResult(toFileName);
    }

    private void getContent(String fileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String type = line.split(",")[ZERO_INDEX];
                String value = line.split(",")[FIRST_INDEX];
                content.merge(type, Integer.parseInt(value), Integer::sum);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int supply = content.get(SUPPLY);
        int buy = content.get(BUY);
        content.put(RESULT, supply - buy);
    }

    private void fillFileWithResult(String fileName) {
        String[] reportOrder = new String[]{SUPPLY, BUY, RESULT};
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (String key : reportOrder) {
                bufferedWriter.write(key);
                bufferedWriter.write(",");
                bufferedWriter.write(String.valueOf(content.getOrDefault(key, ZERO_DEFAULT_VALUE)));
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
