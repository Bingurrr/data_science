package p3;

import java.io.IOException;
import java.util.StringTokenizer;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


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
		
		StringTokenizer st = new StringTokenizer(value.toString(), ","); //split���ش�
		
		if (st.hasMoreTokens()) {date = st.nextToken();}
		if (st.hasMoreTokens()) {station = st.nextToken();}
		if (st.hasMoreTokens()) {item_code = st.nextToken();}
		if (st.hasMoreTokens()) {avg = st.nextToken();}
		if (st.hasMoreTokens()) {status = st.nextToken();}
		if (status.equals("0")) {
			String por_key = "<" + date + ", " + station + ">";
			word.set(por_key);
			String val = item_code + " "+ avg;
			word2.set(val);
			context.write(word, word2);
		}
	}
}
