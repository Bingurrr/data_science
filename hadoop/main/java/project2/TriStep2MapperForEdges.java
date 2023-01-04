package project2;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TriStep2MapperForEdges extends Mapper<IntPairWritable, IntPairWritable, IntPairWritable, IntWritable>{
	
	IntPairWritable ok = new IntPairWritable();
	IntWritable ov = new IntWritable(-1);
	@Override
	protected void map(IntPairWritable key, IntPairWritable value, Mapper<IntPairWritable, IntPairWritable, IntPairWritable, IntWritable>.Context context)
			throws IOException, InterruptedException {
		

		int u = key.u;
		int v = key.v;
		
		ok.set(u, v);
		context.write(ok, ov);
		
	}
}
