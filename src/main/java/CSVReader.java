import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class CSVReader {

    public static HashMap<String,String> tsvReader(String inputPath) {

        String csvFile = inputPath;
        String line = "";
        String cvsSplitBy = "\t";
        HashMap resultMap = new HashMap<String, String>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] predict_data = line.split(cvsSplitBy);
//                System.out.println(predict_data[0]);
//                System.out.println(predict_data[1]);

                resultMap.put(predict_data[0], predict_data[1]);
            }
            return resultMap;

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
    }

    public static HashMap<String,HashMap<String,String>> tsvReader1(String inputPath) {

        String[] split = "607,612,601,602,610,605,614,613,611,616,619,626,620,604,625,647,606,618,603,627".split(",");
        List<String> catids = Arrays.asList(split);
        String csvFile = inputPath;
        String line = "";
        String cvsSplitBy = "\t";
        HashMap<String,HashMap<String,String>> res = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] predict_data = line.split(cvsSplitBy);
//                System.out.println(predict_data[0]);
//                System.out.println(predict_data[1]);
                String catid_leaf = predict_data[1];
                String catid = catid_leaf.substring(0,3);

                if(catids.contains(catid)) {
                    HashMap<String,String> m = res.getOrDefault(catid, new HashMap<String,String>());
                    String predict_datum = predict_data[0];
                    String predict_datum1 = predict_data[1];
                    m.put(predict_datum, predict_datum1);
                    res.put(catid, m);
                }
            }
            return res;

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
    }
}
