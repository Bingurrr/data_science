package project2;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class IntPairWritable implements WritableComparable<IntPairWritable> {

	int u, v;
	
	public void set(int u, int v) {
		this.u = u;
		this.v = v;
	}
	
	public void readFields(DataInput in) throws IOException {
		u = in.readInt();
		v = in.readInt();
	}

	public void write(DataOutput out) throws IOException {
		out.writeInt(u);
		out.writeInt(v);
	}

	public int compareTo(IntPairWritable o) {
		if( u != o.u)
			return Integer.compare(u, o.u);
		else
			return Integer.compare(v, o.v);
	}

	@Override
	public String toString() {
		return u + "\t" + v;
	}
}
