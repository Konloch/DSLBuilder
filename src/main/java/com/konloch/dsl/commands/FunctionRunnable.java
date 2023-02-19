package com.konloch.dsl.commands;

/**
 * @author Konloch
 * @since Jan, 17th, 2017
 */
public interface FunctionRunnable
{
	/**
	 * A functional interface for functions
	 *
	 * @param parameters the parameters supplied for the function
	 */
	void run(String[] parameters);
}
