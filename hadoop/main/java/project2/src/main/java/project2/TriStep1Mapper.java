package project2;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TriStep1Mapper extends Mapper<IntPairWritable, IntPairWritable, IntWritable, IntWritable> {
	
	IntWritable ok = new IntWritable();
	IntWritable ov = new IntWritable();
	
	@Override
	protected void map(IntPairWritable key, IntPairWritable value, Mapper<IntPairWritable, IntPairWritable, IntWritable, IntWritable>.Context context)
			throws IOException, InterruptedException {
		
		//StringTokenizer st = new StringTokenizer(value.toString());
		

		if (value.u < value.v) {
			ok.set(key.u);
			ov.set(key.v);
			context.write(ok, ov);
		}
		else if(value.u > value.v) {
			ok.set(key.v);
			ov.set(key.u);
			context.write(ok, ov);
		}
		else {
			if (key.u < key.v) {
				ok.set(key.u);
				ov.set(key.v);
				context.write(ok, ov);
			}
			else {
				ok.set(key.v);
				ov.set(key.u);
				context.write(ok, ov);
			}
		}
	}
}
