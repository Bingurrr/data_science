package wc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;

import p2.WordCount;

public class WordCountTest {
	
	public static void main(String[] not_used) throws Exception {
		
		Configuration conf = new Configuration();
		conf.setInt("mapreduce.job.reduces",  3);
		
		String[] args = {"src/test/resources/Measurement_info.csv"};
		//1
		//ToolRunner.run(conf, new p1.WordCount(), args);
		//2
		//ToolRunner.run(conf, new p2.WordCount(), args);
//		//3
		//ToolRunner.run(conf, new p3.WordCount(), args);
//		//4
		ToolRunner.run(conf, new p4.WordCount(), args);
//		
	}
	
}
