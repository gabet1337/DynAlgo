import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;


public class TestGenerator {

	public static void main(String[] args) {
	
		if (args.length != 4)
        	System.out.println("Usage: Java TestGenerator <n numInserts numDeletes numTrans>");
        else {
        	System.out.println(genTest(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3])).toString());
        }
		
	}

	//Set transClosues = new HashSet();
	
	public static StringBuilder genTest(int n, int inserts, int deletes, int trans) {

		//n--;
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("init(" + n + ")\n");
		
		Set<IntPair> s = new TreeSet<IntPair>();
		
		if (deletes > inserts)
			throw new IllegalStateException("more deletes than inserts");
		
		Random r = new Random();
		
		Boolean callTrans = true;
		
		while (inserts + deletes + trans > 0) {
						
			int coin = r.nextInt(inserts+deletes+trans);
			
			if (coin < inserts) { // insert
				
				if (s.size() == n*(n-1))
					continue;
				
				IntPair cand = new IntPair(r.nextInt(n), r.nextInt(n));
				while ( (s.contains(cand) || cand.i == cand.j) )
					cand = new IntPair(r.nextInt(n), r.nextInt(n));
				s.add(cand);
				sb.append("insert(" + cand.i + "," + cand.j + ")\n");
				inserts--;
				callTrans = true;
				
			}
			else if (coin >= inserts + deletes) { // trans
				
				if (!callTrans && inserts + deletes > 0)
					continue;
				
				sb.append("transitive closure?\n");
				trans--;
				callTrans = false;
				
			} else { // delete
				
				if (s.size() == 0)
					continue;

				IntPair cand = getRandSetElement(s);
				sb.append("delete(" + cand.i + "," + cand.j + ")\n");
				s.remove(cand);
				deletes--;
				callTrans = true;
				
			}
		}
		
		return sb;
		
	}
	
	static Random r = new Random();
	StringBuilder sb = new StringBuilder();
	
	public static IntPair getRandSetElement(Set<IntPair> s) {
		
		
		int item = r.nextInt(s.size());
		
		int i = 0;
		
		for(IntPair o : s)
		{
		    if (i == item)
		        return o;
		    i = i + 1;
		}
		
		return null;
		
	}
		
	
}
