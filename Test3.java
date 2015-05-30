import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


public class Test3 {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		// TODO Auto-generated method stub

		for (int n = 10; n <= 1000; n = n + 10) {
				
			TestGenerator gen = new TestGenerator();
			gen.init(n);
		
			PrintWriter writer = new PrintWriter("var_n_" + n + ".txt", "UTF-8");
		
			writer.write("init(" + n + ")\n");
		
			for (int i = 0; i < 10000; i++)
				writer.print(gen.next(n));
		
			writer.close();
			
		}
				
	}

}
