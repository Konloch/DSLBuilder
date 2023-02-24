package com.konloch;

import java.io.File;
import java.io.IOException;

/**
 * @author Konloch
 * @since 2/17/2023
 */
public class ExampleDSLDriver
{
	public static void main(String[] args)
	{
		System.out.println("Testing DSL:");
		testA();
		System.out.println();
		System.out.println("Testing DSL-Strict-Mode");
		testB();
	}
	
	private static void testA()
	{
		ExampleDSL dsl = new ExampleDSL();
		
		try
		{
			//parse the DSL and load everything
			dsl.parse(new File("./src/test/java/com/konloch/ExampleDSLConfig"));
			
			//execute the subscripts
			dsl.run("exampleA");
			dsl.run("exampleB");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static void testB()
	{
		ExampleDSLStrictMode dsl = new ExampleDSLStrictMode();
		
		try
		{
			//parse the DSL and load everything
			dsl.parse(new File("./src/test/java/com/konloch/ExampleDSLConfig"));
			
			//execute the subscripts
			dsl.run("exampleA");
			dsl.run("exampleB");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
