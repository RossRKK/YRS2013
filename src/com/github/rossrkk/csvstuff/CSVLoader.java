package com.github.rossrkk.csvstuff;

import java.io.FileReader;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;

public class CSVLoader {
	public static int[] c2008 = new int[100];
	public static int[] c2009 = new int[100];
	
	public static void main(String args[]) throws IOException {
	@SuppressWarnings("resource")
	
	//source http://opendata.s3.amazonaws.com/job-seekers-allowance-2008-2009.csv
	//http://data.gov.uk/dataset/warwickshire-job-seekers-allowance-claimants/resource/10034292-e060-4b05-98c4-3c613e4877e6
	CSVReader reader = new CSVReader(new FileReader("file.csv"));
    String nextLine[];
	int i = 0;
	    while ((nextLine = reader.readNext()) != null) {
		    	if (i != 0 && i < 100) {
		    	c2008[i] = Integer.parseInt(nextLine[6]);
		    	c2009[i] = Integer.parseInt(nextLine[7]);
		    	}
		    i ++;
	    }
	    
		/*for (int j = 0; j < c2008.length; j++) {
			System.out.print(c2008[j] + "	");
		}
		
		System.out.print("\n");
		
		for (int j = 0; j < c2009.length; j++) {
			System.out.print(c2009[j] + "	");
		}
		*/
		System.out.println(c2008[(int) Math.floor(100 * Math.random())]);
		System.out.println(c2009[(int) Math.floor(100 * Math.random())]);
    }
}
