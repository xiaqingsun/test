import com.github.jfasttext.JFastText;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * fasttext训练过程
 */
public class FtTrain {

    private static void train(String[] args) {
        JFastText jft = new JFastText();
        jft.runCmd(new String[]{
                "supervised",
                "-input", Config.ABS_PATH_FT + "train_combine_" + args[0] + ".txt",
                "-output", Config.ABS_PATH_FT + args[0] + "_category_new.model",
                "-dim",args[1],  // 300
                "-epoch",args[2],  //50
                "-bucket",args[3],  //30000
                "-lr",args[4], // 0.1
                "-wordNgrams",args[5], // 1
                "-loss",args[6],  //"ns"
                "-minCount",args[7], // 5
                "-minn",args[8], // 3
                "-maxn",args[9], // 7

        });

        // Load model from file
        jft.loadModel(Config.ABS_PATH_FT + args[0] + "_category_new.model.bin");
        // Do label prediction
        String text = "shoes";
        JFastText.ProbLabel probLabel = jft.predictProba(text);
        System.out.printf("\nThe label of '%s' for '%s' is '%s' with probability %f\n",
                text, args[0], probLabel.label.replace("__label__", ""), Math.exp(probLabel.logProb));

    }

    private static void predict(String[] args) {
        JFastText jft = new JFastText();
        jft.loadModel(Config.ABS_PATH_FT + args[0] + "_category_new.model.bin");
        // Do label prediction
        String query ="shoes,dress,shoe,nike,jacket,woman clothes,smart watch,jeans,bag,clearance,dresses,man clothes,t shirt,boots,sex toys,jackets,bags,christmas decorations,shoe for man,winter jacket,trainers,kitchen accessories,watch,rolex watch,sneakers,gucci,men shoes,hand bag,long dress,hoodies,jumpsuit,jean,christmas,sunglasses,toys,sandal,summer dress,lady shoe,tracksuit,tracksuits,man,women shoes,woman,rings,tops,shorts,wigs,phone case,shirt for man,bluetooth speaker,bra,leggings,perfume,wedding dresses,boot,wig,3d hoodies for men,shoe for woman,plus size dress,shirts,mens shoes,kids,adidas,headphones,shoes accessories,pants,schuhe,jumpsuits,sofa cover,whole selected brand,women's jackets,sandals,man polo shirt,skirts,earrings,jacket for woman,baby,phone,plus size,t- shirts,lingerie,sex dolls,maxi dress,watches,sexy lingerie,nightwear & role play,hoodie,led lights,ring,underwear,kids clothes,car accessories,baby clothes,iphone,polo shirt,men,women dress,free,woman trainer,kids shoes,mens trainers";
        List<String> querys = Arrays.asList(query.split(","));
        List<String> res = new ArrayList<>();
        res.add("query,label,prob");
        for (String text : querys) {
            JFastText.ProbLabel probLabel = jft.predictProba(text);
            StringBuilder str = new StringBuilder();
            if(probLabel != null){
                System.out.println(text);
                str.append(text).append(',').append(probLabel.label.replace("__label__", "")).append(',').append(Math.exp(probLabel.logProb));
                res.add(str.toString());
            }
//            System.out.printf("\nThe label of '%s' for '%s' is '%s' with probability %f\n",
//                    text, args[0], probLabel.label.replace("__label__", ""), Math.exp(probLabel.logProb));
        }

        try {
            String path="/Users/sunxq/work/self_projects/fast_text/data/top100query.txt";
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

    public static void main(String[] args) {

        String[] train_args =  {"N_level1","300", "300", "50000", "1", "2", "ns", "5", "3", "7"};
        FtTrain.train(train_args);

        predict(train_args);

//        JFastText jft = new JFastText();
//        jft.loadModel(Config.ABS_PATH_FT + train_args[0] + "_category_new.model.bin");
//        List<JFastText.ProbLabel> res = jft.predictProba("shoes", 5);
//        for (JFastText.ProbLabel re : res) {
//            System.out.println(re.label.replace("__label__", "") + " " + Math.exp(re.logProb));
//        }


    }
}


//0.12  0.111