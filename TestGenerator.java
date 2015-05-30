import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;


public class TestGenerator {

	public static void main(String[] args) {
	
		//if (args.length != 4) {
			
		//	TestGenerator gen = new TestGenerator();
			
		//	while (true)
		//		System.out.println(gen.next(100));
			
        	//System.out.println("Usage: Java TestGenerator <n numInserts numDeletes numTrans>");
		//}
        //else {
						
        	TestGenerator gen = new TestGenerator();
        	gen.init(Integer.parseInt(args[0]));
        	//System.out.println(gen.genTest(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3])).toString());
        	System.out.println("init(" + args[0] + ")");
        	for (int i = 0; i < Integer.parseInt(args[0]); i++) {
        		System.out.print(gen.next2(Integer.parseInt(args[0])));
        	}
        //}
		
	}

	//Set transClosues = new HashSet();
	
	public StringBuilder genTest(int n, int inserts, int deletes, int trans) {

		//n--;
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("init(" + n + ")\n");
		
		Set<IntPair> s = new TreeSet<IntPair>();
		
		if (deletes > inserts)
			throw new IllegalStateException("more deletes than inserts");
			
		Boolean callTrans = true;
		
		while (inserts + deletes + trans > 0) {
						
			int coin = rand.nextInt(inserts+deletes+trans);
			
			if (coin < inserts) { // insert
				
				if (s.size() == n*(n-1))
					continue;
				
				IntPair cand = new IntPair(rand.nextInt(n), rand.nextInt(n));
				while ( (s.contains(cand) || cand.i == cand.j) )
					cand = new IntPair(rand.nextInt(n), rand.nextInt(n));
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
	
	public static StringBuilder genTest2(int n) {

		int t = 2*n+1;
		
		String[] args = new String[1];
		args[0] = "D:\\DynAlg\\changefile30.sdx";
		
		StringBuilder sb = new StringBuilder();
		
		try {

			File fin = new File(args[0]);

			FileInputStream fis = new FileInputStream( fin);

			//Construct BufferedReader from InputStreamReader
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF8"));

			// BOM marker will only appear on the very beginning
			br.mark(4);
			if ('\ufeff' != br.read()) br.reset(); // not the BOM marker

			String line = null;
			while ((line = br.readLine()) != null && t > 0) {
				sb.append(line + '\n');
				t--;
			}

			br.close();

		} catch (IOException e) {
			System.out.println("Could not read file " + args[0]);
			System.out.println(e);
		}
		
		return sb;
		
	}
	
	Random rand = new Random(System.currentTimeMillis());

	Set<IntPair> graph = new TreeSet<IntPair>();
	
	Set<IntPair> cands = new TreeSet<IntPair>();
	
	public void init(int n) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i != j)
					cands.add(new IntPair(i,j));
			}
		}
	}
	
	boolean b = false;
	
	public String next(int n) {

		if (cands.size() == 0)
			b = true;
		
		if (graph.size() == 0)
			b = false;
		
		int inserts = 100;
		int deletes = 0;
		int trans = 0;
		
		if (b) {
			inserts = 0;
			deletes = 100;
		}
		
		StringBuilder sb = new StringBuilder();
		
		//sb.append("init(" + n + ")\n");
		
		
		//if (deletes > inserts)
		//	throw new IllegalStateException("more deletes than inserts");
				
		Boolean callTrans = true;
		
		while (inserts + deletes + trans > 0) {
						
			int coin = rand.nextInt(inserts+deletes+trans);
			
			if (coin < inserts) { // insert
				
				if (cands.size() == 0) {
					deletes = 100;
					inserts = 0;
					continue;
				}
				
				IntPair cand = getRandSetElement(cands); // new IntPair(rand.nextInt(n), rand.nextInt(n));
				//while ( (graph.contains(cand) || cand.i == cand.j) )
				//	cand = new IntPair(rand.nextInt(n), rand.nextInt(n));
				graph.add(cand);
				cands.remove(cand);
				sb.append("insert(" + cand.i + "," + cand.j + ")\n");
				//inserts--;
				callTrans = true;
				sb.append("transitive closure?\n");
				break;
				
			}
			else if (coin >= inserts + deletes) { // trans
				
				if (!callTrans && inserts + deletes > 0)
					continue;
				
				sb.append("transitive closure?\n");
				trans--;
				callTrans = false;
				break;
				
			} else { // delete
				
				if (graph.size() == 0) {
					inserts = 100;
					deletes = 0;
					continue;
				}

				IntPair cand = getRandSetElement(graph);
				cands.add(cand);
				sb.append("delete(" + cand.i + "," + cand.j + ")\n");
				graph.remove(cand);
				//deletes--;
				callTrans = true;
				sb.append("transitive closure?\n");
				break;
			}
						
		}
		
		return sb.toString();
		
	}
	
	public String next2(int n) {

		int inserts = 50;
		int deletes = 50;
		int trans = 0;
			
		StringBuilder sb = new StringBuilder();
		
		//sb.append("init(" + n + ")\n");
		
		
		//if (deletes > inserts)
		//	throw new IllegalStateException("more deletes than inserts");
				
		Boolean callTrans = true;
		
		while (inserts + deletes + trans > 0) {
						
			int coin = rand.nextInt(inserts+deletes+trans);
			
			if (coin < inserts) { // insert
				
				if (cands.size() == 0) {
					deletes = 100;
					inserts = 0;
					continue;
				}
				
				IntPair cand = getRandSetElement(cands); // new IntPair(rand.nextInt(n), rand.nextInt(n));
				//while ( (graph.contains(cand) || cand.i == cand.j) )
				//	cand = new IntPair(rand.nextInt(n), rand.nextInt(n));
				graph.add(cand);
				cands.remove(cand);
				sb.append("insert(" + cand.i + "," + cand.j + ")\n");
				//inserts--;
				callTrans = true;
				sb.append("transitive closure?\n");
				break;
				
			}
			else if (coin >= inserts + deletes) { // trans
				
				if (!callTrans && inserts + deletes > 0)
					continue;
				
				sb.append("transitive closure?\n");
				trans--;
				callTrans = false;
				break;
				
			} else { // delete
				
				if (graph.size() == 0) {
					inserts = 100;
					deletes = 0;
					continue;
				}

				IntPair cand = getRandSetElement(graph);
				cands.add(cand);
				sb.append("delete(" + cand.i + "," + cand.j + ")\n");
				graph.remove(cand);
				//deletes--;
				callTrans = true;
				sb.append("transitive closure?\n");
				break;
			}
						
		}
		
		return sb.toString();
		
	}
	
	public StringBuilder genTest(int n, int inserts, int deletes) {

		//n--;
		
		int trans = 0;
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("init(" + n + ")\n");
		
		Set<IntPair> s = new TreeSet<IntPair>();
		
		if (deletes > inserts)
			throw new IllegalStateException("more deletes than inserts");
				
		Boolean callTrans = true;
		
		while (inserts + deletes + trans > 0) {
						
			int coin = rand.nextInt(inserts+deletes+trans);
			
			if (coin < inserts) { // insert
				
				if (s.size() == n*(n-1))
					continue;
				
				IntPair cand = new IntPair(rand.nextInt(n), rand.nextInt(n));
				while ( (s.contains(cand) || cand.i == cand.j) )
					cand = new IntPair(rand.nextInt(n), rand.nextInt(n));
				s.add(cand);
				sb.append("insert(" + cand.i + "," + cand.j + ")\n");
				inserts--;
				callTrans = true;
				sb.append("transitive closure?\n");
				
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
				sb.append("transitive closure?\n");
				
			}
		}
		
		return sb;
		
	}
	
	StringBuilder sb = new StringBuilder();
	
	public IntPair getRandSetElement(Set<IntPair> s) {
		
		int item = rand.nextInt(s.size());
		
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
