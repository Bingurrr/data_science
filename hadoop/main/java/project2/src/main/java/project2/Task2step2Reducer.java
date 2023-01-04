package project2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Task2step2Reducer extends Reducer<IntPairWritable, IntPairWritable, IntPairWritable, IntPairWritable>{
	IntPairWritable ok = new IntPairWritable();
	IntPairWritable ov = new IntPairWritable();
	

	@Override
	protected void reduce(IntPairWritable key, Iterable<IntPairWritable> values,
			Reducer<IntPairWritable, IntPairWritable, IntPairWritable, IntPairWritable>.Context context) throws IOException, InterruptedException {
		//0 102 x 60 102 347 x
		
//		String kk = key.toString();
		
		int cnt1 = 0;
		int cnt2 = 0;
		for (IntPairWritable a : values) {
			if (a.u == -1) {
				cnt2 = a.v;
			}
			else {
				cnt1 = a.u;
			}
		}
		ov.set(cnt1, cnt2);
		context.write(key, ov);

	}
}


