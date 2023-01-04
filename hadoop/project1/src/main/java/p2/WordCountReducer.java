package p2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Collections;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.util.HashSet;

public class WordCountReducer extends Reducer<Text, Text, Text, Text>{
	Text ret = new Text();
	Text total = new Text();
	Map<Text, Integer> pick_good = new HashMap<Text, Integer>();
	IntWritable n = new IntWritable(1);
	
	@Override
	protected void reduce(Text key, Iterable<Text> values,
			Reducer<Text, Text, Text, Text>.Context context)
			throws IOException, InterruptedException{
		int sum = 0;

		Set<Text> list = new HashSet<Text>();

		for(Text value : values) {
			if (list.contains(value)) {
				sum ++;
				list.remove(value);
			}
			else {
				list.add(value);
			}
		}

		ret.set(Integer.toString(sum));
		context.write(key, ret);
	}
}
