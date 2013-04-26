package sg.edu.nus.dsr.gclass;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/**
 * @author Arun Manivannan
 * 
 * Simple Java program to load the model and classify dataset in ARFF format 
 * (To convert to ARFF format, use the other program - CSVToARFFConverter)
 *
 * The reason why ARFF was chosen as the format is that I could play with uni,bi and trigrams using weka.  Accuracy
 * increased considerably with this n-gram switch rather than the default unigram model. 
 * 
 * 
 */
public class ClassifierTest {

	public static void main(String[] args) throws Exception {
		
		new ClassifierTest().evaluate();
	}

	private void evaluate() throws Exception {
		Classifier classifier = (Classifier) weka.core.SerializationHelper.read("location/of/SvmModel.model");

		
		Instances evaluationInstances = getEvaluationInstances();
		Evaluation evaluator = new Evaluation(evaluationInstances);
		evaluator.evaluateModel(classifier, evaluationInstances);
	}
	
	private Instances getEvaluationInstances() throws Exception{
		
		//Input data is mandated to be arff because of the pre-processin and normalization step
		DataSource evaluationSource = new DataSource("location/of/evaluationdata.arff"); 
		Instances evaluationSet = evaluationSource.getDataSet();
		evaluationSet.setClassIndex(evaluationSet.numAttributes() - 1);
		
		return evaluationSet;
		
	}

}
