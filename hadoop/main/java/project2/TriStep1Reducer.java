package project2;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TriStep1Reducer extends Reducer<IntWritable, IntWritable, IntPairWritable, IntWritable>{
	IntPairWritable ok = new IntPairWritable();
	
	@Override
	protected void reduce(IntWritable key, Iterable<IntWritable> values,
			Reducer<IntWritable, IntWritable, IntPairWritable, IntWritable>.Context context) throws IOException, InterruptedException {
		
		ArrayList<Integer> neighbors = new ArrayList<Integer>();
		for(IntWritable v : values) {
			neighbors.add(v.get());
		}
		
		for(int u : neighbors) {
			for(int v : neighbors) {
				if (u < v) {
					ok.set(u, v);
					context.write(ok, key);
				}
			}
		}
		
	}
}
