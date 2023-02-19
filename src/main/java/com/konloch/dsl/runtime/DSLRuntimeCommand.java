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
	
	/**
	 * Constructs a new DSLRuntimeCommand
	 *
	 * @param DSLCommandType the DSLCommandType type
	 * @param name any String as the name
	 * @param parameters any String Array as the parameters
	 */
	public DSLRuntimeCommand(DSLCommandType DSLCommandType, String name, String[] parameters)
	{
		this.DSLCommandType = DSLCommandType;
		this.name = name;
		this.parameters = parameters;
	}
	
	/**
	 * Return the DSLCommandType type
	 *
	 * @return the defined DLSCommandType type
	 */
	public DSLCommandType getType()
	{
		return DSLCommandType;
	}
	
	/**
	 * Return the defined name
	 *
	 * @return the defined String name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Return the defined parameters
	 *
	 * @return the defined String Array parameters
	 */
	public String[] getParameters()
	{
		return parameters;
	}
	
	/**
	 * Preforms a recursive search if the variable value contains a variable delimiter / variable name
	 *
	 * @param runtime the DSLRuntime instance
	 * @return the fully extracted String variable
	 */
	public String getVariableValue(DSLRuntime runtime)
	{
		return StringVars.getVariableValue(runtime.getDSL().getVariableDelimiter(),
				()-> getParameters()[0], (key)-> runtime.getCommands().get(key).getVariableValue(runtime));
	}
}
