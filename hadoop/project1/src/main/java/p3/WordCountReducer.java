package p3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCountReducer extends Reducer<Text, Text, Text, Text>{
	Text ret = new Text();
	Text total = new Text();
	Map<Text, Integer> pick_good = new HashMap<Text, Integer>();
	IntWritable n = new IntWritable(1);
	
	@Override
	protected void reduce(Text key, Iterable<Text> values,
			Reducer<Text, Text, Text, Text>.Context context)
			throws IOException, InterruptedException{
		//String ret_string = "";
		String s1 = "";
		String s2 = "";
		String s3 = "";
		String s4 = "";
		String s5 = "";
		String s6 = "";
		String v = "";
		String full_v = "";
		for(Text value : values) {
			v = value.toString().substring(0,1);
			full_v = value.toString();
			if (v.equals("1")) {
				s1 = full_v.substring(2);
			}
			else if (v.equals("3")) {
				s2 = full_v.substring(2);
			}
			else if (v.equals("5")) {
				s3 = full_v.substring(2);
			}
			else if (v.equals("6")) {
				s4 = full_v.substring(2);
			}
			else if (v.equals("8")) {
				s5 = full_v.substring(2);
			}
			else if (v.equals("9")) {
				s6 = full_v.substring(2);
			}
		}
		String ret_string = "SO2: " + s1 +  " NO2: " + s2 + " CO: "  +  s3 + " O3: " + s4 + " PM10: " + s5 + " PM2: " + s6; 
		ret.set(ret_string);
		context.write(key, ret);
	}
}
