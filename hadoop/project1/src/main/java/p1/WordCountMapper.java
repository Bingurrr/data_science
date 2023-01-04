package p1;


import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

//item code가 8인 경우PM10
//9인 경우 PM2.5
public class WordCountMapper extends Mapper<Object, Text, Text, IntWritable> {
	
	Text word = new Text();
	IntWritable num = new IntWritable(); 
	String date = "";
	String station = "";
	String item_code = "";
	String avg = "";
	String status = "";
	int k;
	@Override
	protected void map(Object key, Text value, Mapper<Object, Text, Text, IntWritable>.Context context) 
			throws IOException, InterruptedException{
		
		StringTokenizer st = new StringTokenizer(value.toString(), ","); //split해준다
		
		if (st.hasMoreTokens()) {date = st.nextToken();}
		if (st.hasMoreTokens()) {station = st.nextToken();}
		if (st.hasMoreTokens()) {item_code = st.nextToken();}
		if (st.hasMoreTokens()) {avg = st.nextToken();}
		if (st.hasMoreTokens()) {status = st.nextToken();}

		if (item_code.equals("8") && status.equals("0")) {
			k = (int) Double.parseDouble(avg);
			num.set(k);
			word.set(station);
			context.write(word, num);		
			}
	}
}