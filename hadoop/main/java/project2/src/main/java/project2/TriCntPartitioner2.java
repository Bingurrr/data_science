package project2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class TriCntPartitioner2 extends Partitioner<IntPairWritable, IntPairWritable>{
	public int getPartition(IntPairWritable key, IntPairWritable value, int numReduceTasks) {
		return (key.u * 31 + key.v) % numReduceTasks;
	}
}
