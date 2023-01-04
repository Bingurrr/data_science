package project2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Task2step1Reducer extends Reducer<IntWritable, IntPairWritable, IntPairWritable, IntPairWritable>{
	IntPairWritable ok = new IntPairWritable();
	IntPairWritable ov = new IntPairWritable();
	String str_v;
	@Override
	protected void reduce(IntWritable key, Iterable<IntPairWritable> values,
			Reducer<IntWritable, IntPairWritable, IntPairWritable, IntPairWritable>.Context context) throws IOException, InterruptedException {
		
		ArrayList<Integer[]> list = new ArrayList<Integer[]>(); 
		int k = key.get();
		int leng = 0;
		for(IntPairWritable v : values) {
			int a = v.u;
			int b = v.v;
			list.add(new Integer[] {a,b});
			leng++;
		}
		
		for(Integer[] val : list) {
			if (k == val[0]) {
				ok.set(val[0], val[1]);
				ov.set(leng, -1);
				context.write(ok, ov);
			}
			else  {
				ok.set(val[0], val[1]);
				ov.set(-1, leng);
				context.write(ok, ov);
			}
		}
	}
		
	
}
