import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * Floyd-Warshall  |    lazy    |     eager      |
 * ---------------------------------------------
 * init(n)          O(n^2)         O(n^2)
 * insert(i,j)      O(1)           O(n^3)
 * delete(i,j)      O(1)           O(n^3)
 * trans?           O(n^3)         O(1)
 *  ---------------------------------------------
 */

public class FloydWarshall {

	public boolean[][] t;
	public boolean[][] R;
	public int numEdges = 0;
	
	public boolean isLazy = true;
	
	public static void main(String[] args) {
		
		//args = new String[1];
    	//args[0] = "D:\\DynAlg\\changefile3.sdx";
		
		StringBuilder sb = new StringBuilder();
		sb.append("mode lazy\n");
		
		if (args.length != 1)
        	System.out.println("Usage: Java FloydWarshall <input file>");
        else {
            try {
            	
            	File fin = new File(args[0]);
            	
            	FileInputStream fis = new FileInputStream( fin);
            	 
            	//Construct BufferedReader from InputStreamReader
            	BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF8"));
             
            	// BOM marker will only appear on the very beginning
            	br.mark(4);
            	if ('\ufeff' != br.read()) br.reset(); // not the BOM marker
            	
            	String line = null;
            	while ((line = br.readLine()) != null) {
            		sb.append(line + '\n');
            	}
             
            	br.close();
            	
            } catch (IOException e) {
                System.out.println("Could not read file " + args[0]);
                System.out.println(e);
            }
        }
		
		//StringBuilder cmds = new StringBuilder();
		//cmds.append("mode lazy\n");
		//cmds.append("init 10\n");
		//cmds.append("insert 2 5\n");
		//cmds.append("delete 5 9\n");
		//cmds.append("trans\n");
		
		
		FloydWarshall fw = new FloydWarshall(sb.toString());
		
	}
	
	public FloydWarshall(String cmds) {
				
		for (String cmd : cmds.split("\n")) {
			if (cmd.startsWith("init")) {
				int n = Integer.parseInt(cmd.substring(cmd.indexOf("(")+1,cmd.indexOf(")")));
				init(n);
			} else if (cmd.startsWith("insert")) {
				int i = Integer.parseInt(cmd.substring(cmd.indexOf("(")+1, cmd.indexOf(",")));
				int j = Integer.parseInt(cmd.substring(cmd.indexOf(",")+1, cmd.indexOf(")")));
				insert(i, j);
			} else if (cmd.startsWith("delete")) {
				int i = Integer.parseInt(cmd.substring(cmd.indexOf("(")+1, cmd.indexOf(",")));
				int j = Integer.parseInt(cmd.substring(cmd.indexOf(",")+1, cmd.indexOf(")")));
				delete(i, j);
			} else if (cmd.startsWith("transitive closure?")) {
				if (isLazy)
					transitive_closure();
				System.out.println(numEdges);
			} else if (cmd.startsWith("mode lazy")) {
				isLazy = true;
			} else if (cmd.startsWith("mode eager")) {
				isLazy = false;
			} else {
				throw new UnsupportedOperationException(cmd);
			}
		}
		
	}
	
	public boolean[][] trans(boolean[][] T) {
		
		int n = t.length;
		
		for (int k = 0; k < n; k++)
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					T[i][j] = T[i][j] || (T[i][k] && T[k][j]);
		
		return T;
	}
		
	public void transitive_closure() {
		
		numEdges = 0;
		
		int n = t.length;
		
		R = trans(t);
			
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (R[i][j] && (i != j))
					numEdges += 1;
		
	}
	
	public void init(int n) {
		t = new boolean[n][n]; // Initialized with all-zeroes.
	}
	
	public void delete(int i, int j) {
		t[i][j]  = false;
		
		if (!isLazy)
			transitive_closure();
		
	}
	
	public void insert(int i, int j) {
		t[i][j] = true;
		
		if (!isLazy)
			transitive_closure();
		
	}
	
}
