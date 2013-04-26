package sg.edu.nus.dsr.gclass;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.commons.io.LineIterator;

/**
 * @author Orders the blogs by reverse length
 *
 */
public class OrderBlogs {

	
	public static void main(String[] args) throws IOException {

		new OrderBlogs().orderSort();
	}

	private void orderSort() throws IllegalArgumentException, IOException {

		ArrayList<String> allStrings=new ArrayList<String>(1000000);
		LineIterator lineIterator=new LineIterator(new BufferedReader(new FileReader(new File("/Users/Gabriel/apps/NGrams/allblogs.csv"))));
		while (lineIterator.hasNext()) {
			allStrings.add(lineIterator.next());
			
		}
		
		System.out.println("All reading done");
		Collections.sort(allStrings,new ReverseSizeComparator());
		
		PrintWriter out=new PrintWriter(new BufferedWriter(new FileWriter("/Users/Gabriel/apps/NGrams/allblogsSorted.csv")));
		int writeCount=0;
		for (String eachString : allStrings) {
			writeCount++;
			out.println(eachString);
			
			if (writeCount%10000==0){
				System.out.println("Write count now is : "+writeCount);
				out.flush();
			}
			
		}
		
		out.flush();
		out.close();
		System.out.println("Done");
	}
	
	


}

class ReverseSizeComparator implements Comparator<String>{

	@Override
	public int compare(String string1,String string2) {
		
		return string2.length()-string1.length();
		
	}
	
	
	
}