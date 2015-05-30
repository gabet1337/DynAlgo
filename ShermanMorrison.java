import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;


public class ShermanMorrison {

	static long[][] A_inv;
	static long[][] B;
	
	static int n = 0;
	static long p; // = 17; // (int) (2e31-1);
	
	public static void main(String[] args) {
		
		int _p = (int) (2e31-1);
		
		args = new String[1];
    	args[0] = "D:\\DynAlg\\changefile100.sdx";
    	
    	if (args.length == 2)
    		_p = Integer.parseInt(args[1]);
    	
		StringBuilder sb = new StringBuilder();
		
		if (args.length == 0)
        	System.out.println("Usage: Java FloydWarshall <input file> <prime p, default: 2e31-1>");
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
		
		//sb = TestGenerator.genTest2(1);
		
		ShermanMorrison sm = new ShermanMorrison(sb.toString(), _p);
		
	}
	
	public static int transitive_closure() {
		
		int numEdges = 0;
						
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (A_inv[i][j] > 0 && (i != j))
					numEdges += 1;
		
		return numEdges;
		
	}
	
	public long[][] getResult() {
		return A_inv;
	}
	
	public ShermanMorrison(String cmds, long _p) {
		
		this.p = _p;
		
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
				System.out.println(transitive_closure());
			} else {
				throw new UnsupportedOperationException(cmd);
			}
		}
		
		//print(A_inv);
		
	}
	
	public void command(String cmds) {
		
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
				System.out.println(transitive_closure());
			} else {
				throw new UnsupportedOperationException(cmd);
			}
		}

	}
	
	public void init(int n) {
	
		this.n = n;

		res22 = new long[n][1];
		res33 = new long[1][n];
		C44 = new long[n][n];
		
		B = random(n, n);
		
		//print(B);
		
		//print(B);
		
		A_inv = identity(n);
		
		//for (int i = 0; i < n; i++) {
		//	A_inv[i][i] = B;
		//}
		
	}
	
	//A^-1 = A^-1 - ((A^-1 * u)(vT * A^-1) / (1 + vT * A^-1 * u)) = A^-1 - ((a)(b) / (1 + b * u)  
	public void insert(int i, int j) {
		
		long[][] a = multiply(A_inv, unit(i));
		//long[][] a = multiply2(A_inv, i, 1);
		long[][] b = multiply(transpose(unit(j, B[i][j])), A_inv);
		//long[][] b = multiply3(A_inv, j, B[i][j]);
		
		A_inv = subtract(A_inv, multiply(a, b), mod(1 + multiply(b, unit(j))[0][0],p));
		//A_inv = subtract(A_inv, multiply44(a, b), mod(1 + multiply2(b, j, 1)[0][0],p));
		//subtract2(A_inv, multiply44(a, b), mod(1 + multiply22(b, j, 1)[0][0],p));
		
	}
	
	static Random rand = new Random(System.currentTimeMillis());
	
	public static long getRand() {
		
		return (long) mod(rand.nextLong(), p-1)+1;
	}
	
	public void delete(int i, int j) {
		
		long[][] a = multiply(A_inv, neg_unit(i));
		//long[][] a = multiply22(A_inv, i, -1);
		long[][] b = multiply(transpose(neg_unit(j, B[i][j])), A_inv);
		//long[][] b = multiply33(A_inv, j, -1 * B[i][j]);
		
		A_inv = add(A_inv, multiply(a, b), mod(1 - multiply(b, unit(j))[0][0],p));
		//A_inv = add(A_inv, multiply(a, b), mod(1 - multiply2(b, j, 1)[0][0],p));
		//add(A_inv, multiply44(a, b), mod(1 - multiply22(b, j, 1)[0][0],p));
		
		//Random r = new Random();
		B[i][j] = getRand(); // r.nextLong();
		
	}
	
	public static void print(long m[][]) {
	    for (long[] row : m) 
	        System.out.println(Arrays.toString(row));       
	}
	
	// return a random m-by-n matrix with values between 0 and 1
    public static long[][] random(int m, int n) {
    	//Random r = new Random();
        long[][] C = new long[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = getRand(); // mod(r.nextLong(), p);
        return C;
    }

    // return n-by-n identity matrix I
    public static long[][] identity(int n) {
        long[][] I = new long[n][n];
        for (int i = 0; i < n; i++)
            I[i][i] = B[i][i]; // 1; // I[i][i];
        return I;
    }

    public long[][] unit(int i) {
        long[][] unit = new long[n][1];
        unit[i][0] = 1;
        return unit;
    }
    
    public long[][] unit(int i, long value) {
        long[][] unit = new long[n][1];
        unit[i][0] = value;
        return unit;
    }
    
    public long[][] neg_unit(int i) {
        long[][] unit = new long[n][1];
        unit[i][0] = -1;
        return unit;
    }
    
    public long[][] neg_unit(int i, long value) {
        long[][] unit = new long[n][1];
        unit[i][0] = -1 * value;
        return unit;
    }

    // return C = A^T
    public static long[][] transpose(long[][] A) {
        int m = A.length;
        int n = A[0].length;
        long[][] C = new long[n][m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                C[j][i] = A[i][j];
        return C;
    }
    
    // return C = A + (alpha * B)
    public static long[][] add(long[][] A, long[][] B, long alpha) {
    	alpha = mod_inverse(alpha, p); // (int) Math.ceil((double)1 / alpha);
        int m = A.length;
        int n = A[0].length;
        long[][] C = new long[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = mod(A[i][j] + (mod((alpha * B[i][j]),p)), p);
        return C;
    }
    
    // return C = A + (alpha * B)
    public static void add2(long[][] A, long[][] B, long alpha) {
    	alpha = mod_inverse(alpha, p); // (int) Math.ceil((double)1 / alpha);
        int m = A.length;
        int n = A[0].length;
        //long[][] C = new long[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                A[i][j] = mod(A[i][j] + (mod((alpha * B[i][j]),p)), p);
        //return C;
    }
        
    // return C = A - alpha * B
    public static void subtract2(long[][] A, long[][] B, long alpha) {
    	alpha = mod_inverse(alpha, p); // (int) Math.ceil((double)1 / alpha);
        int m = A.length;
        int n = A[0].length;
        //long[][] C = new long[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                A[i][j] = mod(A[i][j] - (mod((alpha * B[i][j]),p)), p);
        //return C;
    }
    
    // return C = A - alpha * B
    public static long[][] subtract(long[][] A, long[][] B, long alpha) {
    	alpha = mod_inverse(alpha, p); // (int) Math.ceil((double)1 / alpha);
        int m = A.length;
        int n = A[0].length;
        long[][] C = new long[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = mod(A[i][j] - (mod((alpha * B[i][j]),p)), p);
        return C;
    }

    
    // return C = A * B
    public static long[][] multiply(long[][] A, long[][] B) {
        int mA = A.length;
        int nA = A[0].length;
        int mB = B.length;
        int nB = B[0].length;
        if (nA != mB) throw new RuntimeException("Illegal matrix dimensions.");
        long[][] C = new long[mA][nB];
        for (int i = 0; i < mA; i++)
            for (int j = 0; j < nB; j++)
                for (int k = 0; k < nA; k++) {
                    C[i][j] = mod(C[i][j] + (mod((A[i][k] * B[k][j]),p)),p);
                }
        return C;
    }
    
    
    static long[][] C44;;
    
    // return C = A * B
    public static long[][] multiply44(long[][] A, long[][] B) {
        int mA = A.length;
        int nA = A[0].length;
        int mB = B.length;
        int nB = B[0].length;
        if (nA != mB) throw new RuntimeException("Illegal matrix dimensions.");
        //long[][] C = new long[mA][nB];
        for (int i = 0; i < mA; i++)
            for (int j = 0; j < nB; j++)
                for (int k = 0; k < nA; k++) {
                    C44[i][j] = mod(C44[i][j] + (mod((A[i][k] * B[k][j]),p)),p);
                }
        return C44;
    }

    static long[][] res33;
    
    public static long[][] multiply33(long[][] A, int idx, long value) {
        
    	//int nA = A.length;
    	
    	//long[][] res = new long[1][n];
    	
    	for (int i = 0; i < n; i++) {
    		res33[0][i] += (value * A[idx][i]); 
    	}
    	
    	return res33;
    	
    	/*
    	int mA = A.length;
        int nA = A[0].length;
        int mB = B.length;
        int nB = B[0].length;
        if (nA != mB) throw new RuntimeException("Illegal matrix dimensions.");
        long[][] C = new long[mA][nB];
        for (int i = 0; i < mA; i++)
            for (int j = 0; j < nB; j++)
                for (int k = 0; k < nA; k++) {
                    C[i][j] = mod(C[i][j] + (mod((A[i][k] * B[k][j]),p)),p);
                }
        return C;
        */
    }
    
    public static long[][] multiply3(long[][] A, int idx, long value) {
        
    	//int nA = A.length;
    	
    	long[][] res = new long[1][n];
    	
    	for (int i = 0; i < n; i++) {
    		res[0][i] += (value * A[idx][i]); 
    	}
    	
    	return res;
    	
    	/*
    	int mA = A.length;
        int nA = A[0].length;
        int mB = B.length;
        int nB = B[0].length;
        if (nA != mB) throw new RuntimeException("Illegal matrix dimensions.");
        long[][] C = new long[mA][nB];
        for (int i = 0; i < mA; i++)
            for (int j = 0; j < nB; j++)
                for (int k = 0; k < nA; k++) {
                    C[i][j] = mod(C[i][j] + (mod((A[i][k] * B[k][j]),p)),p);
                }
        return C;
        */
    }
        
    public static long[][] multiply2(long[][] A, int idx, long value) {
        
    	int nA = A.length;
    	
    	long[][] res = new long[n][1];
    	
    	for (int i = 0; i < nA; i++) {
    		res[i][0] += (value * A[i][idx]); 
    	}
    	
    	return res;
    	
    	/*
    	int mA = A.length;
        int nA = A[0].length;
        int mB = B.length;
        int nB = B[0].length;
        if (nA != mB) throw new RuntimeException("Illegal matrix dimensions.");
        long[][] C = new long[mA][nB];
        for (int i = 0; i < mA; i++)
            for (int j = 0; j < nB; j++)
                for (int k = 0; k < nA; k++) {
                    C[i][j] = mod(C[i][j] + (mod((A[i][k] * B[k][j]),p)),p);
                }
        return C;
        */
    }
    
    static long[][] res22;
    
    public long[][] getRes() {
    	return A_inv;
    }
    
    public static long[][] multiply22(long[][] A, int idx, long value) {
        
    	int nA = A.length;   	
    	
    	for (int i = 0; i < nA; i++) {
    		res22[i][0] += (value * A[i][idx]); 
    	}
    	
    	return res22;
    	
    	/*
    	int mA = A.length;
        int nA = A[0].length;
        int mB = B.length;
        int nB = B[0].length;
        if (nA != mB) throw new RuntimeException("Illegal matrix dimensions.");
        long[][] C = new long[mA][nB];
        for (int i = 0; i < mA; i++)
            for (int j = 0; j < nB; j++)
                for (int k = 0; k < nA; k++) {
                    C[i][j] = mod(C[i][j] + (mod((A[i][k] * B[k][j]),p)),p);
                }
        return C;
        */
    }
    
   //  return array [d, a, b] such that d = gcd(p, q), ap + bq = d
   static long[] gcd(long p, long q) {
      if (q == 0)
         return new long[] { p, 1, 0 };

      long[] vals = gcd(q, p % q);
      long d = vals[0];
      long a = vals[2];
      long b = vals[1] - (p / q) * vals[2];
      return new long[] { d, a, b };
   }
   
	// computes b such that ab = 1 (mod n), returns -1 on failure
	static long mod_inverse(long a, long n) {
		long d = gcd(a, n)[1];
		if (d > 1) return -1;
		return mod(d,n);
	}
	
	// return a % b (positive value)
	static long mod(long a, long b) {
	  return ((a%b)+b)%b;
	}
	
}