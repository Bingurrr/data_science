package project2;
//
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class TriCnt extends Configured implements Tool {

	public static void main(String[] args) throws Exception {
		ToolRunner.run(new TriCnt(), args);
	}
	
	public int run(String[] args) throws Exception {
		
		String inputpath = args[0];
		String tmppath = inputpath + ".tmp";
		String tmppath2 = inputpath + "2.tmp";
		String tmppath3 = inputpath + "3.tmp";
		String tmppath4 = inputpath + "4.tmp";
		String outpath = inputpath + ".out";
		//task1
		runStep0(inputpath, tmppath);
		
		//task2
		runStep1(tmppath, tmppath2);
		runStep1_2(tmppath2, tmppath3);
		
		//triangle
		runStep2_1(tmppath3, tmppath4);
		runStep2_2(tmppath3, tmppath4, outpath);
		
		return 0;
	}
	private void runStep0(String inputpath, String tmppath) throws Exception{
		
		Job job = Job.getInstance(getConf());
		job.setJarByClass(TriCnt.class);
		
		job.setMapperClass(TriStep0Mapper.class);
		job.setReducerClass(TriStep0Reducer.class);
		
		job.setMapOutputKeyClass(IntPairWritable.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		job.setPartitionerClass(TriCntPartitioner.class);
		
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(IntWritable.class);
		
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		
		FileInputFormat.addInputPath(job, new Path(inputpath));
		FileOutputFormat.setOutputPath(job, new Path(tmppath));
		
		job.waitForCompletion(true);
		
	}
	
	private void runStep1(String inputpath, String tmppath) throws Exception{
		
		Job job = Job.getInstance(getConf());
		job.setJarByClass(TriCnt.class);
		
		job.setMapperClass(Task2step1Mapper.class);
		job.setReducerClass(Task2step1Reducer.class);
		
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(IntPairWritable.class);
		
		
		
		job.setOutputKeyClass(IntPairWritable.class);
		job.setOutputValueClass(IntPairWritable.class);
		

		job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		
		FileInputFormat.addInputPath(job, new Path(inputpath));
		FileOutputFormat.setOutputPath(job, new Path(tmppath));
		
		job.waitForCompletion(true);
		
	}
	private void runStep1_2(String inputpath, String tmppath) throws Exception{
		
		Job job = Job.getInstance(getConf());
		job.setJarByClass(TriCnt.class);
		
		job.setMapperClass(Task2step2Mapper.class);
		job.setReducerClass(Task2step2Reducer.class);
		
		job.setMapOutputKeyClass(IntPairWritable.class);
		job.setMapOutputValueClass(IntPairWritable.class);
		
		job.setPartitionerClass(TriCntPartitioner2.class);
		
		job.setOutputKeyClass(IntPairWritable.class);
		job.setOutputValueClass(IntPairWritable.class);
		
		
		MultipleInputs.addInputPath(job, new Path(inputpath), SequenceFileInputFormat.class, Task2step2Mapper.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);

		FileInputFormat.addInputPath(job, new Path(inputpath));
		FileOutputFormat.setOutputPath(job, new Path(tmppath));
		
		job.waitForCompletion(true);
		
	}
	
	private void runStep2_1(String inputpath, String tmppath) throws Exception{
		
		Job job = Job.getInstance(getConf());
		job.setJarByClass(TriCnt.class);
		
		job.setMapperClass(TriStep1Mapper.class);
		job.setReducerClass(TriStep1Reducer.class);
		
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		job.setOutputKeyClass(IntPairWritable.class);
		job.setOutputValueClass(IntWritable.class);
		
		
		job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
//		
		FileInputFormat.addInputPath(job, new Path(inputpath));
		FileOutputFormat.setOutputPath(job, new Path(tmppath));
		
		job.waitForCompletion(true);
		
		
	}

	private void runStep2_2(String inputpath, String tmppath, String outpath) throws Exception {
		
		Job job = Job.getInstance(getConf());
		job.setJarByClass(TriCnt.class);
		
		job.setReducerClass(TriStep2Reducer.class);
		
		job.setMapOutputKeyClass(IntPairWritable.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		job.setPartitionerClass(TriCntPartitioner.class);
		
		job.setOutputFormatClass(TextOutputFormat.class);
		
		MultipleInputs.addInputPath(job, new Path(inputpath), SequenceFileInputFormat.class, TriStep2MapperForEdges.class);
		MultipleInputs.addInputPath(job, new Path(tmppath), SequenceFileInputFormat.class, TriStep2MapperForWedges.class);
		
		FileOutputFormat.setOutputPath(job, new Path(outpath));
		
		job.waitForCompletion(true);
		
		
	}
	

}

