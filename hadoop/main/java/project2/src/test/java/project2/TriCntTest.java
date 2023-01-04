package project2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;

import project2.TriCnt;

public class TriCntTest {
	public static void main(String[] args) throws Exception {
		
		
		long startTime = System.currentTimeMillis();
		
		Configuration conf = new Configuration();
		conf.setInt("mapreduce.job.reduces", 3);
		
		String[] params = {"src/test/resources/testset.txt"};
		
		ToolRunner.run(conf, new TriCnt(), params);
		
        long finishTime = System.currentTimeMillis();
        long elapsedTime = finishTime - startTime;
        
  
        System.out.println("startTime(ms) : " + startTime);
        System.out.println("finishTime(ms) : " + finishTime);
        System.out.println("elapsedTime(ms) : " + elapsedTime);
	}
}

