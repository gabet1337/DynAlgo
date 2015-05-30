import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.rmi.server.LogStream;


public class Tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		args = new String[1];
		args[0] = "D:\\DynAlg\\changefile100.sdx";

		StringBuilder sb = new StringBuilder();

		if (args.length != 1)
			System.out.println("Usage: Java Tester <input file>");
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
			
		PrintStream old = System.out;

		int n = 30;
		
		int repeats = 30;

		//long[] primes = { 2,  23, 223, 2153, 21481, 214759, 2147489, 21474851, 214748383, 697932239, 939524147, 1181116037, 1422707953, 1664299867, 1785095867, 1905891817 ,2147483647 };

		long[] primes = { 1422707953, 1785095867 };
		
		//System.out.println((int) (2e31));
		
		for (int j = 0; j < primes.length; j++) {

			int globalCount = 0;
			
			for (int i = 0; i < repeats; i++) {

				//System.out.print("*");
				
				boolean hasError = false;

				long ident = 0;
				
				int count = 1;
								
				TestGenerator gen = new TestGenerator();
				gen.init(n);
				
				long p = primes[j];
				
				FloydWarshall fw = new FloydWarshall("mode lazy\ninit(" + n + ")\n");
				ShermanMorrison sm = new ShermanMorrison("init(" + n + ")\n", p); //(2e31-1));
				
				while (!hasError) {
					
					ident++;
					
					//if (ident % 1000 == 0)
					//	System.out.print(".");
					
					String nextCmd = gen.next(n);
					
					ByteArrayOutputStream fwBaos = new ByteArrayOutputStream();
					System.setOut(new PrintStream(fwBaos));
					fw.command(nextCmd);
					boolean[][] fw_res = fw.getRes(); 
					
					ByteArrayOutputStream smBaos = new ByteArrayOutputStream();
					System.setOut(new PrintStream(smBaos));					
					sm.command(nextCmd);
					long[][] sm_res = sm.getRes();
					
					System.setOut(old);

					for (int a = 0; a < n; a++) {
						for (int b = 0; b < n; b++) {
							if (a != b) {
								if (fw_res[a][b] == true && sm_res[a][b] == 0)
									hasError = true;
								if (fw_res[a][b] == false && sm_res[a][b] != 0)
									hasError = true;
							}
						}
						
					}
					
					if (hasError)
						globalCount = globalCount + count;
					else
						count++;
					
					//System.out.println(sb.toString() + "\n");

					//System.out.println(fwBaos.toString() + "------------------\n" + smBaos.toString());

					//if (fwBaos.toString().equals(smBaos.toString())) {
						//if (j == 0)
					//		count++;
						//else
						//	count = count + (j * 10);
						//System.out.print("1\t");
					//} else {
						//System.out.println(fwBaos.toString() + "------------------\n" + smBaos.toString());
					//	hasError = true;
					//	globalCount = globalCount + count;
					//}
					//System.out.print("0\t");

					//System.out.print(p + "\t");

					//System.out.println(fw_time / 1000000 / repeats + "\t" + sm_time / 1000000 / repeats);

				}

			}
			
			System.out.println("\nAvg. #operations for prime p = " + primes[j] + " was " + globalCount / repeats);
		}

		//System.out.println("Success: " + count);

	}

}
