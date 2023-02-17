package com.konloch.dsl.commands;

/**
 * @author Konloch
 * @since Jan, 17th, 2017
 */
public class DSLDefinedCommand
{
	private final DSLCommandType commandType;
	private final String name;
	private VariableRunnable variableRunnable;
	private FunctionRunnable functionRunnable;
	
	public DSLDefinedCommand(String name, VariableRunnable variableRunnable)
	{
		commandType = DSLCommandType.VARIABLE;
		this.name = name;
		this.variableRunnable = variableRunnable;
	}
	
	public DSLDefinedCommand(String name, FunctionRunnable functionRunnable)
	{
		commandType = DSLCommandType.FUNCTION;
		this.name = name;
		this.functionRunnable = functionRunnable;
	}
	
	public DSLCommandType getCommandType()
	{
		return commandType;
	}
	
	public String getName()
	{
		return name;
	}
	
	public VariableRunnable getVariableRunnable()
	{
		return variableRunnable;
	}
	
	public FunctionRunnable getFunctionRunnable()
	{
		return functionRunnable;
	}
}
