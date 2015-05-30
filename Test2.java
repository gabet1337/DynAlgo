import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		PrintStream old = System.out;
		
		TestGenerator gen = new TestGenerator();
		
		long p = 2147483647;
		int n = 10;
		
		FloydWarshall fw = new FloydWarshall("mode lazy\ninit(" + n + ")\n");
		ShermanMorrison sm = new ShermanMorrison("init(" + n + ")\n", p); //(2e31-1));
		
		for (int i = 0; i < 2; i++) {
		
		String nextCmd = gen.next(n);
			
		ByteArrayOutputStream fwBaos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(fwBaos));
		fw.command(nextCmd);
		
		ByteArrayOutputStream smBaos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(smBaos));					
		sm.command(nextCmd);
		
		System.setOut(old);

			//System.out.println(sb.toString() + "\n");

		System.out.println(fwBaos.toString() + "------------------\n" + smBaos.toString());

		}
		
			//if (fwBaos.toString().equals(smBaos.toString())) {
				//if (j == 0)
					//count++;
				//else
				//	count = count + (j * 10);
				//System.out.print("1\t");
			//} else {
			//	hasError = true;
			//	globalCount = globalCount + count;
			//}
		
	}

}
