package sg.edu.nus.dsr.gclass;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.LineIterator;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;


/**
 * @author Arun Manivannan
 * 
 * Converts a CARAT separated file in the form of Terms,GENDER into an ARFF format understood by Weka
 *
 */
public class CSVToARFFConverter {

	static String CARAT="^";
	public static void main(String[] args) throws Exception {
		CSVToARFFConverter converter=new CSVToARFFConverter();
		converter.convertCSVToARFF();
	}

	private void convertCSVToARFF() throws Exception {

		//Create Attributes
		FastVector attributes = constructAttributes();
		
		//Create instances object
		Instances data=new Instances("Data",attributes,0);
		
		System.out.println("Number of attributes : "+data.numAttributes());
		//data.setClassIndex(data.numAttributes()-1);
		
		//LineIterator lineReader=new LineIterator(new FileReader("/Users/Gabriel/apps/NGrams/allblogs.csv"));
		LineIterator lineReader=new LineIterator(new FileReader("/Users/Gabriel/apps/NGrams/sampleBlogsLarge25k.csv"));

		
		//Construct data
		String eachLine=null;
		ArrayList<String> eachDataList=null;
		Instance eachDataInstance=null;
		
		int lineCount=0;
		while (lineReader.hasNext()){
			try {
				lineCount++;
				eachLine=lineReader.nextLine();
				//eachLine="SystemofaDown.com,..,0,3,0";
				//System.out.println("Each line : "+eachLine);
				eachDataList=Lists.newArrayList(Splitter.on(CARAT).omitEmptyStrings().trimResults().split(eachLine.toString()));
				
				
				if (lineCount%1000==0){
					System.out.println("Line count is currently : "+lineCount);
				}
				//System.out.println("Each data list : line ( "+lineCount+"):::"+eachDataList.get(0)+ "::::"+eachDataList.get(1));
				eachDataInstance=new Instance(2);
				eachDataInstance.setDataset(data);
				
				eachDataInstance.setValue(data.attribute(0), data.attribute(0).addStringValue(eachDataList.get(0)));
				try {
					eachDataInstance.setClassValue(eachDataList.get(eachDataList.size()-1));
				} catch (Exception e) {
					System.out.println("Each line : "+eachLine);
					e.printStackTrace();
					break;
					//continue;
				}
				//eachDataInstance.setValue(data.attribute(3), Double.parseDouble(eachDataList.get(3)));
				
				data.add(eachDataInstance);
			} catch (Exception e) {
				System.out.println("Error :  "+eachLine);
				e.printStackTrace();
				break;
			}
			
			//wordVectorFilter.input(eachDataInstance);
		}

		System.out.println("Instances count : "+data.numInstances());
		System.out.println("Instance 0 : "+data.instance(0));
		//System.out.println(data.instance(0).toString());
		
		System.out.println("Filtering....");
		//Instances filteredData=Filter.useFilter(data, wordVectorFilter);
		//System.out.println("Instance 0 : "+filteredData.instance(0));
		
		saveToArff(data);
		
	}

	private void saveToArff(Instances filteredData) throws IOException {

		System.out.println("Saving to ARFF file");
		ArffSaver arffSaver=new ArffSaver();
		//arffSaver.setFile(new File("/Users/Gabriel/Desktop/BlogClassArffFile.arff"));
		arffSaver.setFile(new File("/Users/Gabriel/apps/DSR-Gender/GenderSample25k.arff"));
		arffSaver.setInstances(filteredData);
		arffSaver.writeBatch();
	}

	private FastVector constructAttributes() {

		FastVector attributes=new FastVector();
		attributes.addElement(new Attribute("Terms", (FastVector)null));
		
		FastVector maleFemaleClass=new FastVector();
		maleFemaleClass.addElement("MALE");
		maleFemaleClass.addElement("FEMALE");
		Attribute classAttribute=new Attribute("GENDER",maleFemaleClass);
		attributes.addElement(classAttribute);
		return attributes;
	}
}
