package project2;


import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;

public class Task2step1Mapper extends Mapper<IntWritable, IntWritable, IntWritable, IntPairWritable> {
	
	IntWritable ok = new IntWritable();
	IntPairWritable ov = new IntPairWritable();
	
	@Override
	protected void map(IntWritable key, IntWritable value, Mapper<IntWritable, IntWritable, IntWritable, IntPairWritable>.Context context)
			throws IOException, InterruptedException {
		
		//StringTokenizer st = new StringTokenizer(value.toString());
		
		int u = key.get();
		int v = value.get();
		ok.set(u);
		ov.set(u,v);
		context.write(ok, ov);
		ok.set(v);
		ov.set(u,v);
		context.write(ok, ov);
	}
}

