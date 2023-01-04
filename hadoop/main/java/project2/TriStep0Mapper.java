package project2;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class TriStep0Mapper extends Mapper<Object, Text, IntPairWritable, IntWritable> {
	
	IntPairWritable ok = new IntPairWritable();
	IntWritable ov = new IntWritable();
	
	@Override
	protected void map(Object key, Text value, Mapper<Object, Text, IntPairWritable, IntWritable>.Context context)
			throws IOException, InterruptedException {
		
		StringTokenizer st = new StringTokenizer(value.toString());
		
		int u = Integer.parseInt(st.nextToken());
		int v = Integer.parseInt(st.nextToken());

		if (u < v) {
			ok.set(u, v);
			ov.set(-1);
			context.write(ok, ov);
		}
		else if (u > v) {
			ok.set(v, u);
			ov.set(-1);
			context.write(ok, ov);
		}
	}
}
