package GUI;

import java.awt.Component;
import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFileChooser;

import org.eclipse.swt.SWT;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

/*
 * Klasa za učitavanje .csv i .arff podataka, odnosno početnih podataka
 * koje će model koristiti za cross validaciju.
 * */

public class Input {
	
	private  Instances data;
	private  Instances new_data;
	private  HashMap<String, Integer> classNameAndIndex; 
	
	public  void readFile(String filename, String filepath) throws Exception {

		String fullFilePath = filepath + "/" + filename;
		BufferedReader reader = null;
  	  	try {
  	  		
  	  		if(fullFilePath.endsWith(".csv")) {
  	  			CSVLoader loader = new CSVLoader();
	  			loader.setSource(new File(fullFilePath));
	  			data = loader.getDataSet();//get instances object
  	  		}
  	  		
  	  		else
  	  		{
  	  			reader = new BufferedReader(new FileReader(filepath + "/" + filename));
  	  			
  	  			try {
  	  				data = new Instances(reader);
  	  			} catch (IOException e) {
  	  				e.printStackTrace();
  	  			}
  	  			try {
  	  				reader.close();	
  	  			} catch (IOException e) {
  	  				e.printStackTrace();
  	  			}
  	  		}
 
  	  	} catch (FileNotFoundException e) {
  	  		e.printStackTrace();
  	  	}
		
  	  int brojZadnjegStupca = data.numAttributes() - 1;
  	  
  	  /* Pretvaranje broja bugova u vrijednosti 0/1 */
  	  for ( int i = 0; i < data.numInstances(); i++) {
  		  
  		  Instance currentInstance = data.instance(i);
  		  int brojBugova = Integer.parseInt( currentInstance.toString(brojZadnjegStupca) );
  		  if ( brojBugova != 0 ) {
  			  currentInstance.setValue(brojZadnjegStupca, 1.0);
  		  }
  	  }
  	  	
  	  	// setting class attribute
  	  	data.setClassIndex(data.numAttributes() - 1);
		//System.out.println(data);
  	  	
  	  	new_data = FileHandler.numericToNominal(data);
      }

	public  Instances getData() {
		return new_data;
	}	
}

