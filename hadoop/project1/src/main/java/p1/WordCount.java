package p1;

import org.apache.hadoop.conf.Configured;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;


import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class WordCount extends Configured implements Tool {
	// Configured -> Hadoop에 설정 값을 전달해준다.
	// Tool : command 라인에서 실행할 때 옵션값을 받기 편하게 해준다.
	public int run(String[] args) throws Exception {
		
		Job myjob = Job.getInstance(getConf()); // my job -> 내가 지금 돌릴려고 하는 일이다. configured의 getConf()에서 디폴트값들을 가지고온다. 
		myjob.setJarByClass(WordCount.class);   // 내가 실행하고 있는 파일이 무엇인지 넘겨준다.
		
		myjob.setMapperClass(WordCountMapper.class);   // 내가 구현한 매퍼클래스를 지정해준다
		myjob.setReducerClass(WordCountReducer.class); // 내가 구현한 리듀서클래스를 지정해준다.
		
		myjob.setMapOutputKeyClass(Text.class);           // output 타입을 지정해준다
		myjob.setMapOutputValueClass(IntWritable.class);  // output 타입을 지정해준다. 
		
		myjob.setOutputFormatClass(TextOutputFormat.class);  //  OutPutFomat이 Text이면 안써줘도 되긴하다
		myjob.setInputFormatClass(TextInputFormat.class);    // TextInputFormat을 지정해준다. SequenceFileInputFormat -> binary 파일 읽을 수 있다.
		
		FileInputFormat.addInputPath(myjob, new Path(args[0]));                    // 입력파일을 어디서 불러올 것인가
		FileOutputFormat.setOutputPath(myjob, new Path(args[0]).suffix("1.out"));  // 출력파일을 어디에 어떻게 저장을 할 것인가
		
		myjob.waitForCompletion(true);    // Hadoop를 실행하는 코드 true로 하면 로그들이 많이 찍힌다.
		
		return 0;    
		}
	// command line에서 실행시키기 위해 구현한 코드
	public static void main(String[] args) throws Exception{
		ToolRunner.run(new WordCount(), args);
	}

}
