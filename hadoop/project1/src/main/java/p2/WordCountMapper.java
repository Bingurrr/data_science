package p2;

import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

//item code가 8인 경우PM10
//9인 경우 PM2.5
public class WordCountMapper extends Mapper<Object, Text, Text, Text>{
	Text word = new Text();
	Text word2 = new Text();
	IntWritable num = new IntWritable(1);
	String date = "";
	String station = "";
	String item_code = "";
	String avg = "";
	String status = "";
	int k;
	int area;
	@Override
	protected void map(Object key, Text value, Mapper<Object, Text, Text, Text>.Context context) 
			throws IOException, InterruptedException{
		StringTokenizer st = new StringTokenizer(value.toString(), ","); //split해준다
		if (st.hasMoreTokens()) {date = st.nextToken();}
		if (st.hasMoreTokens()) {station = st.nextToken();}
		if (st.hasMoreTokens()) {item_code = st.nextToken();}
		if (st.hasMoreTokens()) {avg = st.nextToken();}
		if (st.hasMoreTokens()) {status = st.nextToken();}
		
		if (item_code.equals("8") && status.equals("0")) {
			k = (int) Double.parseDouble(avg);
			if ( k <= 30) {
				word.set(station);
				word2.set(date);
				context.write(word, word2);
			}	
		}
		else if (item_code.equals("9") && status.equals("0")) {
			k = (int) Double.parseDouble(avg);
			if ( k <= 15) {
				word.set(station);
				word2.set(date);
				context.write(word, word2);
			}	
		}
	}
}
