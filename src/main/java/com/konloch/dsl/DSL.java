package com.konloch.dsl;

import com.konloch.disklib.DiskReader;
import com.konloch.dsl.commands.DSLCommandType;
import com.konloch.dsl.commands.DSLDefinedCommand;
import com.konloch.dsl.commands.FunctionRunnable;
import com.konloch.dsl.commands.VariableRunnable;
import com.konloch.dsl.runtime.DSLRuntimeCommand;
import com.konloch.dsl.runtime.DSLRuntime;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents the data portion of the DSL.
 *
 * This class is used to configure and start the DSL Runtime.
 *
 * @author Konloch
 * @since Jan, 17th, 2017
 */
public class DSL
{
	private final String setValueDelimiter;
	private final String bracketDelimiterStart;
	private final String bracketDelimiterEnd;
	private final String subscriptDelimiterStart;
	private final String subscriptDelimiterEnd;
	private final char variableDelimiter;
	private final String commentDelimiter;
	private final HashMap<String, DSLDefinedCommand> commands = new HashMap<>();
	private final HashMap<String, List<DSLRuntimeCommand>> subscripts = new HashMap<>();
	private final DSLRuntime runtime = new DSLRuntime(this);
	
	/**
	 * Constructs a new DSL instance.
	 *
	 * @param setValueDelimiter any character to represent the variable value delimiter
	 * @param bracketDelimiterStart any character to represent the bracket delimiter start
	 * @param bracketDelimiterEnd any character to represent the bracket delimiter end
	 * @param subscriptDelimiterStart any character to represent the subscript delimiter start
	 * @param subscriptDelimiterEnd any character to represent the subscript delimiter end
	 * @param variableDelimiter any character to represent the variable delimiter
	 * @param commentDelimiter any character to represent the comment delimiter
	 */
	public DSL(char setValueDelimiter, char bracketDelimiterStart, char bracketDelimiterEnd,
	           char subscriptDelimiterStart, char subscriptDelimiterEnd, char variableDelimiter,
	           char commentDelimiter)
	{
		this.setValueDelimiter = String.valueOf(setValueDelimiter);
		this.bracketDelimiterStart = String.valueOf(bracketDelimiterStart);
		this.bracketDelimiterEnd = String.valueOf(bracketDelimiterEnd);
		this.subscriptDelimiterStart = String.valueOf(subscriptDelimiterStart);
		this.subscriptDelimiterEnd = String.valueOf(subscriptDelimiterEnd);
		this.variableDelimiter = variableDelimiter;
		this.commentDelimiter = String.valueOf(commentDelimiter);
	}
	
	/**
	 * Clear all the user defined commands & subscripts. It also removes all runtime data.
	 *
	 * @return this instance for method chaining
	 */
	public DSL clear()
	{
		//clear the runtime
		runtime.getCommands().clear();
		runtime.stopParse();
		
		//clear the user defined data
		commands.clear();
		
		//clear the subscripts
		subscripts.clear();
		return this;
	}
	
	/**
	 * Parse any File and execute / load the script.
	 *
	 * @param file any file
	 * @return this instance for method chaining
	 * @throws IOException if an I/O error occurs reading from the stream
	 */
	public DSL parse(File file) throws IOException
	{
		parse(DiskReader.read(file));
		return this;
	}
	
	/**
	 * Parse any String ArrayList and execute / load the script.
	 *
	 * @param fileContents any String ArrayList
	 * @return this instance for method chaining
	 */
	public DSL parse(ArrayList<String> fileContents)
	{
		for(String line : fileContents)
			runtime.parseLine(line);
		
		runtime.stopParse();
		return this;
	}
	
	/**
	 * Add a new variable handler.
	 *
	 * @param name any String as the variable name
	 * @param variableRunnable any VariableRunnable to be called when the variable set value gets called
	 * @return this instance for method chaining
	 */
	public DSL addVar(String name, VariableRunnable variableRunnable)
	{
		commands.put(name, new DSLDefinedCommand(name, variableRunnable));
		return this;
	}
	
	/**
	 * Remove a variable handler.
	 *
	 * @param name any String as the variable name
	 * @return this instance for method chaining
	 */
	public DSL removeVar(String name)
	{
		DSLDefinedCommand command = commands.get(name);
		if(command != null && command.getType() == DSLCommandType.VARIABLE)
			commands.remove(name);
		
		return this;
	}
	
	/**
	 * Add a new function handler.
	 *
	 * @param name any String as the function name
	 * @param functionRunnable any FunctionRunnable to be called when the function gets called
	 * @return this instance for method chaining
	 */
	public DSL addFunc(String name, FunctionRunnable functionRunnable)
	{
		commands.put(name, new DSLDefinedCommand(name, functionRunnable));
		return this;
	}
	
	/**
	 * Remove a function handler.
	 *
	 * @param name any String as the function name
	 * @return this instance for method chaining
	 */
	public DSL removeFunc(String name)
	{
		DSLDefinedCommand command = commands.get(name);
		if(command != null && command.getType() == DSLCommandType.FUNCTION)
			commands.remove(name);
		
		return this;
	}
	
	/**
	 * Define a subscript.
	 *
	 * @param name any String as the subscript name
	 * @return this instance for method chaining
	 */
	public DSL addSub(String name)
	{
		subscripts.put(name, new ArrayList<>());
		return this;
	}
	
	/**
	 * Remove a subscript.
	 *
	 * @param name any String as the subscript name
	 * @return this instance for method chaining
	 */
	public DSL removeSub(String name)
	{
		subscripts.remove(name);
		return this;
	}
	
	/**
	 * Runs a subscript associated with a String name. Throws a Runtime Exception if the Subscript doesn't exist.
	 *
	 * @param name any String as the subscript name
	 * @return this instance for method chaining
	 */
	public DSL run(String name)
	{
		List<DSLRuntimeCommand> functionContents = subscripts.get(name);
		
		if(functionContents == null)
			throw new RuntimeException("Subscript " + name + " not found");
		
		for (DSLRuntimeCommand command : functionContents)
			runtime.execute(command);
		
		return this;
	}
	
	/**
	 * Returns the set value delimiter
	 *
	 * @return the set value delimiter as a String
	 */
	public String getSetValueDelimiter()
	{
		return setValueDelimiter;
	}
	
	/**
	 * Returns the bracket delimiter start
	 *
	 * @return the bracket delimiter start as a String
	 */
	public String getBracketDelimiterStart()
	{
		return bracketDelimiterStart;
	}
	
	/**
	 * Returns the bracket delimiter end
	 *
	 * @return the bracket delimiter end as a String
	 */
	public String getBracketDelimiterEnd()
	{
		return bracketDelimiterEnd;
	}
	
	/**
	 * Returns the subscript delimiter start
	 *
	 * @return the subscript delimiter start as a String
	 */
	public String getSubscriptDelimiterStart()
	{
		return subscriptDelimiterStart;
	}
	
	/**
	 * Returns the subscript delimiter end
	 *
	 * @return the subscript delimiter end as a String
	 */
	public String getSubscriptDelimiterEnd()
	{
		return subscriptDelimiterEnd;
	}
	
	/**
	 * Returns the variable delimiter
	 *
	 * @return the variable delimiter as a String
	 */
	public char getVariableDelimiter()
	{
		return variableDelimiter;
	}
	
	/**
	 * Returns the comment delimiter
	 *
	 * @return the comment delimiter as a String
	 */
	public String getCommentDelimiter()
	{
		return commentDelimiter;
	}
	
	/**
	 * Returns the command map
	 *
	 * @return the command map as a HashMap
	 */
	public HashMap<String, DSLDefinedCommand> getCommands()
	{
		return commands;
	}
	
	/**
	 * The subscript map
	 * @return the subscript map as a HashMap
	 */
	public HashMap<String, List<DSLRuntimeCommand>> getSubscripts()
	{
		return subscripts;
	}
	
	/**
	 * The DLSRuntime associated with this DSL.
	 *
	 * @return the DSLRuntime for this DSL instance
	 */
	public DSLRuntime getRuntime()
	{
		return runtime;
	}
	
	/**
	 * Alert that this is a library
	 *
	 * @param args program launch arguments
	 */
	public static void main(String[] args)
	{
		throw new RuntimeException("Incorrect usage - for information on how to use this correctly visit https://konloch.com/DSLBuilder/");
	}
}