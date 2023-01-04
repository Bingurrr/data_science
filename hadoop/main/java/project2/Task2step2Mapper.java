package project2;


import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Task2step2Mapper extends Mapper<IntPairWritable, IntPairWritable,IntPairWritable, IntPairWritable> {
	
	IntPairWritable ok = new IntPairWritable();
	IntPairWritable ov = new IntPairWritable();
	
	@Override
	protected void map(IntPairWritable key, IntPairWritable value, Mapper<IntPairWritable, IntPairWritable, IntPairWritable, IntPairWritable>.Context context)
			throws IOException, InterruptedException {
		
		// st = 0 67	0 67 347 x
		int k1 = key.u;
		int k2 = key.v;
		int v1 = value.u;
		int v2 = value.v;
		
		ok.set(k1, k2);
		ov.set(v1, v2);
		
		context.write(ok, ov);

	}
}
