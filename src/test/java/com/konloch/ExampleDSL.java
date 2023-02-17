package com.konloch;

import com.konloch.dsl.DSL;

/**
 * An example DSL built using DSLBuilder
 *
 * @author Konloch
 * @since Jan, 17th, 2017
 */
public class ExampleDSL extends DSL
{
	public ExampleDSL()
	{
		super("=", "(", ")",
				"{", "}", "#");

		//add the sub scripts
		addSub("exampleA");
		addSub("exampleB");
		
		//variables - variable=value
		addVar("variable", value ->
		{
			System.out.println("Variable Set: " + value);
		});

		//functions - function(parameter)
		addFunc("functionA", (params) ->
		{
			System.out.println("Function A: " + params[0]);
		});
		addFunc("functionB", (params) ->
		{
			for(String p : params)
				System.out.println("Function B: " + p);
		});
	}
}
