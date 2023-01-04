package project2;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;

public class TriStep2MapperForWedges extends Mapper<IntPairWritable, IntWritable, IntPairWritable, IntWritable>{
	
	@Override
	protected void map(IntPairWritable key, IntWritable value, Mapper<IntPairWritable, IntWritable, IntPairWritable, IntWritable>.Context context)
			throws IOException, InterruptedException {
		context.write(key, value);
	}
}
