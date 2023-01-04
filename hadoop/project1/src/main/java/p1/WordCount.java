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
	// Configured -> Hadoop�� ���� ���� �������ش�.
	// Tool : command ���ο��� ������ �� �ɼǰ��� �ޱ� ���ϰ� ���ش�.
	public int run(String[] args) throws Exception {
		
		Job myjob = Job.getInstance(getConf()); // my job -> ���� ���� �������� �ϴ� ���̴�. configured�� getConf()���� ����Ʈ������ ������´�. 
		myjob.setJarByClass(WordCount.class);   // ���� �����ϰ� �ִ� ������ �������� �Ѱ��ش�.
		
		myjob.setMapperClass(WordCountMapper.class);   // ���� ������ ����Ŭ������ �������ش�
		myjob.setReducerClass(WordCountReducer.class); // ���� ������ ���༭Ŭ������ �������ش�.
		
		myjob.setMapOutputKeyClass(Text.class);           // output Ÿ���� �������ش�
		myjob.setMapOutputValueClass(IntWritable.class);  // output Ÿ���� �������ش�. 
		
		myjob.setOutputFormatClass(TextOutputFormat.class);  //  OutPutFomat�� Text�̸� �Ƚ��൵ �Ǳ��ϴ�
		myjob.setInputFormatClass(TextInputFormat.class);    // TextInputFormat�� �������ش�. SequenceFileInputFormat -> binary ���� ���� �� �ִ�.
		
		FileInputFormat.addInputPath(myjob, new Path(args[0]));                    // �Է������� ��� �ҷ��� ���ΰ�
		FileOutputFormat.setOutputPath(myjob, new Path(args[0]).suffix("1.out"));  // ��������� ��� ��� ������ �� ���ΰ�
		
		myjob.waitForCompletion(true);    // Hadoop�� �����ϴ� �ڵ� true�� �ϸ� �α׵��� ���� ������.
		
		return 0;    
		}
	// command line���� �����Ű�� ���� ������ �ڵ�
	public static void main(String[] args) throws Exception{
		ToolRunner.run(new WordCount(), args);
	}

}
