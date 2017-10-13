package com.wiki;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;


public class ErodAppTest {

	/**
	 * method to test Erod Application
	 */
	@Test
	public void test() {		
		ErodAppProcessor erodApp = new ErodAppProcessor();		
		Path inputFilePath = Paths.get(ErodAppProcessor.SYSTEM_USER_LOCATION+ "\\resources\\ErodInputData.csv");
		Path outputFilePath = Paths.get(ErodAppProcessor.SYSTEM_USER_LOCATION+ "\\resources\\ErodOutputData.csv");
		
		try {
			List<String> outputList = erodApp.processInput(inputFilePath);
			erodApp.processOutput(outputFilePath, outputList);
			
		} catch (IOException e) {
			fail("Junit testing failed");
		}
	}

}
