package p1;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCountReducer extends Reducer<Text, IntWritable, Text, Text>{
	Text ret = new Text();
	Text total = new Text();

	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,
			Reducer<Text, IntWritable, Text, Text>.Context context)
			throws IOException, InterruptedException{
		int sum = 0;
		int min = 99999999;
		int max = 0;
		int cnt = 0;

		for(IntWritable value : values) {
			sum += value.get();
			cnt ++;
			if (value.get() > max) max = value.get();
			if (value.get() < min) min = value.get();
		}
		int avg = Math.round(sum/cnt);
		ret.set("average: "+ Integer.toString(avg) + " max: " + Integer.toString(max) + " min: " + Integer.toString(min));
		context.write(key, ret);
	}
}
