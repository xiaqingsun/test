import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import redis.clients.jedis.Jedis;
import com.alibaba.fastjson.JSON;

public class RedisTest {

    public static void main(String[] args) {
        System.out.println("begin");

        Jedis jedis = new Jedis("query-process.5xl6ax.ng.0001.usw2.cache.amazonaws.com", 6379);
        String s1=jedis.get("recoveryErrorDTOList");
        String s2=jedis.get("substituteWordList");
        String s3=jedis.get("recoveryErrorUpdateTime");

        try {
            String path=System.getProperty("user.dir")+"/sunxq/recoveryErrorDTOList.txt";
            System.out.println(path);
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            bw.write(JSON.toJSONString(s1));
            bw.flush();
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            String path=System.getProperty("user.dir")+"/sunxq/substituteWordList.txt";
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            bw.write(JSON.toJSONString(s2));
            bw.flush();
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            String path=System.getProperty("user.dir")+"/sunxq/recoveryErrorUpdateTime.txt";
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            bw.write(JSON.toJSONString(s3));
            bw.flush();
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("end");

    }
}
