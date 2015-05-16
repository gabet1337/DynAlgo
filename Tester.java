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
    	args[0] = "D:\\DynAlg\\changefile30.sdx";
		
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
		
		int repeats = 1;
		
		long fw_time = 0;
		
		long startTime = System.nanoTime();
		ByteArrayOutputStream fwBaos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(fwBaos));
		FloydWarshall fw = new FloydWarshall("mode lazy\n" + sb.toString());
		long endTime = System.nanoTime();
		
		fw_time += (endTime - startTime);
		
		long sm_time = 0;
		
		startTime = System.nanoTime();
		ByteArrayOutputStream smBaos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(smBaos));
		ShermanMorrison sm = new ShermanMorrison(sb.toString());
		endTime = System.nanoTime();
		
		sm_time += (endTime - startTime);
		
		System.setOut(old);
				
		if (fwBaos.toString().equals(smBaos.toString()))
			System.out.print("correct ");
		else
			System.out.print("incorrect ");
		
		System.out.println(fw_time / 1000000 / repeats + "\t" + sm_time / 1000000 / repeats);
		
	}

}
