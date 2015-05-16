public class IntPair implements Comparable<IntPair> {
		
		public int i, j;
		public IntPair(int i, int j) {
			this.i = i;
			this.j = j;
		}
		@Override
		public int compareTo(IntPair o) {
			
			if (this.equals(o))
				return 0;
			
			if (o.i == i)
				return this.j < o.j ? -1
						: (this.j == o.j ? 0 : 1);
			
			return (i < o.i ? -1 : 1);
		}
		
		
		
	}