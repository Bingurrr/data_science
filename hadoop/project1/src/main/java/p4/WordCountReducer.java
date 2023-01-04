package p4;

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
		//1 3 5 6 8 9
		String kf = "";
		String tmp = ""; //일단 분리시키자
		String estimate = "";
		float so2 = 0;
		int so2_cnt = 0;
		
		float no2 = 0;
		int no2_cnt = 0;
		
		float co = 0;
		int co_cnt = 0;
		
		float o3 = 0;
		int o3_cnt = 0;
		
		float PM10 = 0;
		int PM10_cnt = 0;

		float PM2 = 0;
		int PM2_cnt = 0;

		
		for(Text value : values) {
			tmp = value.toString();
			kf = tmp.substring(0, 1);
			estimate = tmp.substring(2);
			
			if (kf.equals("1")) {
				so2 += Float.parseFloat(estimate);
				so2_cnt++;
			}
			else if (kf.equals("3")) {
				no2 += Float.parseFloat(estimate);
				no2_cnt++;
			}
			else if (kf.equals("5")) {
				co += Float.parseFloat(estimate);
				co_cnt++;
			}
			else if (kf.equals("6")) {
				o3 += Float.parseFloat(estimate);
				o3_cnt++;
			}
			else if (kf.equals("8")) {
				PM10 += Float.parseFloat(estimate);
				PM10_cnt++;
			}
			else if (kf.equals("9")) {
				PM2 += Float.parseFloat(estimate);
				PM2_cnt++;
			}
		}
		float avg_so2 = so2/so2_cnt;
		float avg_no2 = no2/no2_cnt;
		float avg_co = co/co_cnt;
		float avg_o3 = o3/o3_cnt;
		float avg_PM10 = PM10/PM10_cnt;
		float avg_PM2 = PM2/PM2_cnt;
		
		String result = "SO2: " + String.valueOf(avg_so2) +  " NO2: " + String.valueOf(avg_no2) + " CO: "  +  
		String.valueOf(avg_co) + " O3: " + String.valueOf(avg_o3) + " PM10: " + String.valueOf(avg_PM10) + " PM2: " + String.valueOf(avg_PM2); 
		ret.set(result);
		context.write(key, ret);
	}
}
