public class Config {
    // 模型存放路径
    public static  final String ABS_PATH_GENDER = "/Users/Shuan/Documents/Work/Search/search-new-java/genderwords/"; //性别预测-train
        public static final String ABS_PATH_FT = "/home/admin/user/sunxq/my_projects/fast_text/data/"; //类目预测-train
//    public static final String ABS_PATH_FT = "/Users/sunxq/work/self_projects/fast_text/data/"; //类目预测-train
    public static final String ABS_PATH_SECOND_FT = "/Users/Shuan/Documents/Work/Search/search-new-java/fasttext/data/level2_data_1121/"; //类目预测-level2 train

    // 类目预测评估
    public static final String PREDICT_PATH = "/home/admin/user/sunxq/my_projects/fast_text/data/test_combine_N_level1.tsv";
//    public static final String PREDICT_PATH = "/Users/sunxq/work/self_projects/fast_text/data/test_combine_N_level1.tsv";

        public static final String PREDICT_RESULT_PATH = "/home/admin/user/sunxq/my_projects/fast_text/data/preRes_combine_N_level1.tsv";
//    public static final String PREDICT_RESULT_PATH = "/Users/sunxq/work/self_projects/fast_text/data/preRes_combine_N_level1.tsv";

    // 类目预测阈值
    public static double threshold = 0.8;
    public static double thresholdSecond = 0.8;

    // 性别预测阈值
    public static double threshold_man = 0.995;
    public static double threshold_woman = 0.999;
    public static double threshold_all = 0.999;

    // 返回类目个数
    public static int topK = 3;
    public static int topKSecond = 5;

    public static final String MAN = "man";
    public static final String WOMAN = "woman";

    // 类目预测等级
    public static final String LEVEL = "1";
}
