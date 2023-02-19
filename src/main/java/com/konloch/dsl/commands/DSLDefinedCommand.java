package com.konloch.dsl.commands;

/**
 * @author Konloch
 * @since Jan, 17th, 2017
 */
public class DSLDefinedCommand
{
	private final DSLCommandType type;
	private final String name;
	private VariableRunnable variableRunnable;
	private FunctionRunnable functionRunnable;
	
	/**
	 * Creates a new DSLDefinedCommand and defines it as a variable.
	 *
	 * @param name any String as the variable name
	 * @param variableRunnable any VariableRunnable
	 */
	public DSLDefinedCommand(String name, VariableRunnable variableRunnable)
	{
		type = DSLCommandType.VARIABLE;
		this.name = name;
		this.variableRunnable = variableRunnable;
	}
	
	/**
	 * Constructs a new DSLDefinedCommand and defines it as a function.
	 *
	 * @param name any String as the variable name
	 * @param functionRunnable any FunctionalRunnable
	 */
	public DSLDefinedCommand(String name, FunctionRunnable functionRunnable)
	{
		type = DSLCommandType.FUNCTION;
		this.name = name;
		this.functionRunnable = functionRunnable;
	}
	
	/**
	 * Returns the type
	 *
	 * @return the defined DSLCommandType type
	 */
	public DSLCommandType getType()
	{
		return type;
	}
	
	/**
	 * Returns the name
	 *
	 * @return the defined String name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Returns the VariableRunnable if it's defined, or it will return null.
	 *
	 * @return returns the VariableRunnable if it's defined, or it will return null
	 */
	public VariableRunnable getVariableRunnable()
	{
		return variableRunnable;
	}
	
	/**
	 * Returns the FunctionRunnable if it's defined, or it will return null
	 *
	 * @return returns the FunctionRunnable if it's defined, or it will return null
	 */
	public FunctionRunnable getFunctionRunnable()
	{
		return functionRunnable;
	}
}
