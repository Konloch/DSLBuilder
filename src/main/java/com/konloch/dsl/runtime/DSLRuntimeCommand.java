package com.konloch.dsl.runtime;

import com.konloch.dsl.commands.DSLCommandType;
import com.konloch.stringvars.StringVars;

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
	
	public String getVariableValue(DSLRuntime runtime)
	{
		return StringVars.getVariableValue('%', ()->getParameters()[0],
				(key)->runtime.getCommands().get(key).getVariableValue(runtime));
	}
}
