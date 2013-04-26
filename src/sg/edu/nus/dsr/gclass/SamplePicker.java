package sg.edu.nus.dsr.gclass;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.commons.io.LineIterator;

/**
 * @author Picks samples from the huge dataset of 650k entries
 *
 */
public class SamplePicker {

	
	public static void main(String[] args) throws IllegalArgumentException, IOException {

		SamplePicker picker=new SamplePicker();
		picker.pickSamples();
	}

	private void pickSamples() throws IllegalArgumentException, IOException {

		//LineIterator lineIterator=new LineIterator(new BufferedReader(new FileReader(new File("/Users/Gabriel/apps/NGrams/allblogsSorted.csv"))));
		LineIterator lineIterator=new LineIterator(new BufferedReader(new FileReader(new File("/Users/Gabriel/apps/NGrams/sampleBlogsLarge25k.csv"))));

		ArrayList<String> maleSample=new ArrayList<String>(10000);
		ArrayList<String> femaleSample=new ArrayList<String>(10000);
		
		int lineCount=0;
		boolean maleCapped=false;
		boolean femaleCapped=false;
		
		//skip some 10k lines
		for (int i = 0; i < 10000; i++) {
			lineIterator.next();
			
		}
		

		while (lineIterator.hasNext()) {
			lineCount++;

			
			String eachData = (String) lineIterator.next();
			if (lineCount%1000==0){
				System.out.println("hitting line "+lineCount);
			}
			
			if (maleCapped&& femaleCapped){
				break;
			}
			if (!maleCapped && eachData.contains("^MALE")){
				if (maleSample.size()>1000){
					System.out.println("Male reached cap");
					maleCapped=true;
				}
				maleSample.add(eachData);
			}
			else if (!femaleCapped && eachData.contains("^FEMALE")){
				if (femaleSample.size()>1000){
					System.out.println("Female reached cap");
					femaleCapped=true;
				}
				femaleSample.add(eachData);
			}
			
		}
		
		
		maleSample.addAll(femaleSample);
		
		PrintWriter out=new PrintWriter(new BufferedWriter(new FileWriter("/Users/Gabriel/apps/NGrams/testSample2k.csv")));
		int writeCount=0;
		for (String eachString : maleSample) {
			writeCount++;
			out.println(eachString);
			
			if (writeCount%10000==0){
				System.out.println("Write count now is : "+writeCount);
				out.flush();
			}
			
		}
		
		out.flush();
		out.close();
		
	}
	

}
