package com.konloch.dsl.runtime;

import com.konloch.dsl.commands.DSLCommandType;

/**
 * A DSLCommand represents a variable or a function.
 *
 * @author Konloch
 * @since Jan, 17th, 2017
 */
public class DSLRuntimeCommand
{
	private final DSLCommandType DSLCommandType;
	private final String name;
	private final String[] parameters;
	
	public DSLRuntimeCommand(DSLCommandType DSLCommandType, String name, String[] parameters)
	{
		this.DSLCommandType = DSLCommandType;
		this.name = name;
		this.parameters = parameters;
	}
	
	public DSLCommandType getType()
	{
		return DSLCommandType;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String[] getParameters()
	{
		return parameters;
	}
	
}
