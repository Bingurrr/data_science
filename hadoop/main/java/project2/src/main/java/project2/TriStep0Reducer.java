package project2;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TriStep0Reducer extends Reducer<IntPairWritable, IntWritable, IntWritable, IntWritable>{
	
	IntWritable ok = new IntWritable();
	IntWritable ov = new IntWritable();
	String str_key;
	String[] arr;
	@Override
	protected void reduce(IntPairWritable key, Iterable<IntWritable> values,
			Reducer<IntPairWritable, IntWritable, IntWritable, IntWritable>.Context context) throws IOException, InterruptedException {
		
		ok.set(key.u);
		ov.set(key.v);
		context.write(ok, ov);
	}
}

