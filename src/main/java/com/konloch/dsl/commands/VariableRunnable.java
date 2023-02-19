package com.konloch.dsl.commands;

/**
 * @author Konloch
 * @since Jan, 17th, 2017
 */
public interface VariableRunnable
{
	/**
	 * A functional interface for variables
	 *
	 * @param value the value supplied for the variable
	 */
	void run(String value);
}
