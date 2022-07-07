
import java.io.BufferedWriter;
import java.util.*;
import java.io.IOException;
import java.io.FileWriter;
import org.apache.commons.lang3.StringUtils;
import com.github.jfasttext.JFastText;


/**
 * fasttext预测过程
 */
public class FtPredict {

    private static void predictTestData( String modelPath, String testFilePath, Integer topK, Double threshold) {
        JFastText jft = new JFastText();
        // Load model from file
        jft.loadModel(modelPath + "_category_new.model.bin");
//        jft.loadModel(modelPath);

        // Load file for label prediction
        HashMap<String, String> resultMap  = CSVReader.tsvReader(testFilePath);

        List<String> res= new ArrayList<>();

        ArrayList<String>labelClusterList = new ArrayList<String>();
        ArrayList<String>trueLabelList = new ArrayList<String>();
        ArrayList<String>queryList = new ArrayList<String>();
        if (resultMap!= null){
            Integer hitCount = 0;
            Integer labelCount = 0;
            Integer testLabelCount = 0;
            for (String query : resultMap.keySet()){
                String trueLabel = resultMap.get(query);
                Double probSum = 0d;
                ArrayList<String> topN = new ArrayList<String>();
                List<JFastText.ProbLabel> probLabelList = jft.predictProba(query, topK);
                for(JFastText.ProbLabel probLabel:probLabelList){
                    probSum += Math.exp(probLabel.logProb);
                    topN.add(probLabel.label.replace("__label__", ""));
                    if(probSum > threshold){break;}
                }
                if (probSum < threshold){
                    StringBuilder str = new StringBuilder();
                    str.append(query).append(",").append(trueLabel).append(",").append("             ");
                    for (String s : topN) {
                        str.append(s).append(",");
                    }
                    res.add(str.toString());

                    topN = null;
                }
                trueLabel = trueLabel.replace("\"", "");
                String[] testCateLabel =  trueLabel.split(",");
                if (topN!=null){
                    String[] topNs = topN.toArray(new String[topN.size()]);
                    if(findIntersection(testCateLabel, topNs) != null){
                        hitCount += findIntersection(testCateLabel, topNs).length;
                    }

                    labelCount += topN.size();
                    testLabelCount += testCateLabel.length;
                    String topNStr = StringUtils.join(topNs, ", ");
                    if (topNStr!=null){
                        labelClusterList.add(topNStr);
                    }
                    else{labelClusterList.add("");
                    };
                    trueLabelList.add(trueLabel);
                    queryList.add(query);
                }
            }
            try{
                writeToFile(Config.PREDICT_RESULT_PATH, queryList, trueLabelList, labelClusterList);
            } catch(java.io.IOException e){
                System.out.println("write to outputfile failed:" + e);
            }
            Double precision = Double.valueOf(hitCount)/ Double.valueOf(labelCount);
            Double recall = Double.valueOf(hitCount)/ Double.valueOf(testLabelCount);

            System.out.println(String.format("@Precision (top %s): %s", topK.toString(),precision.toString()));
            System.out.println(String.format("@Recall (top %s): %s", topK.toString(),recall.toString()));

            try {
                String path="/Users/sunxq/Downloads/error.txt";
                BufferedWriter bw = new BufferedWriter(new FileWriter(path));
                for (String s : res) {
                    bw.write(s);
                    bw.newLine();
                    bw.flush();
                }
                bw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    private static void predictTestData1( String modelPath, String testFilePath, Integer topK, Double threshold) {
        JFastText jft = new JFastText();
        // Load model from file
        jft.loadModel(modelPath + "_category_new.model.bin");
//        jft.loadModel(modelPath);
        // Load file for label prediction
//        HashMap<String, String> resultMap  = CSVReader.tsvReader(testFilePath);
        HashMap<String,HashMap<String, String>> resultMap1  = CSVReader.tsvReader1(testFilePath);

        String[] split = "607,612,601,602,610,605,614,613,611,616".split(",");
        List<String> catids = Arrays.asList(split);

        for (String catid : catids) {
            HashMap<String, String> resultMap = resultMap1.get(catid);
            System.out.println(catid);

            ArrayList<String>labelClusterList = new ArrayList<String>();
            ArrayList<String>trueLabelList = new ArrayList<String>();
            ArrayList<String>queryList = new ArrayList<String>();
            if (resultMap!= null){
                Integer hitCount = 0;
                Integer labelCount = 0;
                Integer testLabelCount = 0;
                for (String query : resultMap.keySet()){
                    String trueLabel = resultMap.get(query);
                    Double probSum = 0d;
                    ArrayList<String> topN = new ArrayList<String>();
                    List<JFastText.ProbLabel> probLabelList = jft.predictProba(query, topK);
                    for(JFastText.ProbLabel probLabel:probLabelList){
                        probSum += Math.exp(probLabel.logProb);
                        topN.add(probLabel.label.replace("__label__", ""));
                        if(probSum > threshold){break;}
                    }
                    if (probSum < threshold){
                        topN = null;
                    }
                    trueLabel = trueLabel.replace("\"", "");
                    String[] testCateLabel =  trueLabel.split(",");
                    if (topN!=null){
                        String[] topNs = topN.toArray(new String[topN.size()]);
                        if(findIntersection(testCateLabel, topNs) != null){
                            hitCount += findIntersection(testCateLabel, topNs).length;
                        }

                        labelCount += topN.size();
                        testLabelCount += testCateLabel.length;
                        String topNStr = StringUtils.join(topNs, ", ");
                        if (topNStr!=null){
                            labelClusterList.add(topNStr);
                        }
                        else{labelClusterList.add("");
                        };
                        trueLabelList.add(trueLabel);
                        queryList.add(query);
                    }
                }
                try{
                    writeToFile(Config.PREDICT_RESULT_PATH, queryList, trueLabelList, labelClusterList);
                } catch(java.io.IOException e){
                    System.out.println("write to outputfile failed:" + e);
                }
                Double precision = Double.valueOf(hitCount)/ Double.valueOf(labelCount);
                Double recall = Double.valueOf(hitCount)/ Double.valueOf(testLabelCount);

                System.out.println(String.format("@Precision (top %s): %s", topK.toString(),precision.toString()));
                System.out.println(String.format("@Recall (top %s): %s", topK.toString(),recall.toString()));

            }
        }
    }


    private static void writeToFile(String outputFilePath, ArrayList<String> queryList, ArrayList<String> trueLabelList,
                                    ArrayList<String> labelClusterList) throws IOException {
        FileWriter writer = new FileWriter(outputFilePath);
        if ((queryList.size() == trueLabelList.size()) && (queryList.size() == labelClusterList.size())) {
            String separator = "\t";
            for (int index = 0; index < queryList.size(); index++) {
                writer.write(queryList.get(index) + separator + trueLabelList.get(index)
                        + separator + labelClusterList.get(index) + System.lineSeparator());
            }
            writer.close();

        }
    }

    private static String[] findIntersection(String[] firstArray, String[] secondArray){
        HashSet<String> set = new HashSet<>();
        String[] intersection = {};
        set.addAll(Arrays.asList(firstArray));
        set.retainAll(Arrays.asList(secondArray));
        if(set!= null){ return set.toArray(intersection);}
        else{return null;}
    }


    private static void top10pre() {
        Integer topK = 5;
        Double threshold = 0.8;
        String filePath = Config.PREDICT_PATH;
        String modelPath = Config.ABS_PATH_FT + "N_level5";
        HashMap<String, String> resultMap1  = CSVReader.tsvReader(filePath);
        HashMap<String,HashMap<String, String>> resultMap  = CSVReader.tsvReader1(filePath);
        int size=0;
        for (Map.Entry<String, HashMap<String, String>> entry : resultMap.entrySet()) {
            HashMap<String, String> value = entry.getValue();
            size += value.size();
            System.out.println(entry.getKey());
//            if(entry.getKey().equals("612")){
//            FtPredict.predictTestData(modelPath, filePath, topK, threshold, value);
//            }
        }
        System.out.printf("Top10_size:%d, size:%d, rate:%.2f",size,resultMap1.size(), size*1.00/resultMap1.size());  //Top10_size:131320, size:199804, rate:0.66
    }


    public static void main(String[] args){
        Integer topK = 5;
        Double threshold = 0.8;
        String filePath = Config.PREDICT_PATH;
        String modelPath = Config.ABS_PATH_FT + "N_level1";
//        String modelPath = "/Users/sunxq/Downloads/model.bin";

        FtPredict.predictTestData(modelPath, filePath, topK, threshold);

//        String modelPath = "/Users/sunxq/Downloads/model3.bin";
        FtPredict.predictTestData1(modelPath, filePath, topK, threshold);

//        top10pre();
    }
}

//@Precision (top 5): 0.5799744010059955
//@Recall (top 5): 0.7533543343833858

//@Precision (top 5): 0.5120331425318617
//@Recall (top 5): 0.6696583671105965