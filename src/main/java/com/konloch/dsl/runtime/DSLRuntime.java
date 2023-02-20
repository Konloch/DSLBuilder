package com.konloch.dsl.runtime;

import com.konloch.dsl.DSL;
import com.konloch.dsl.commands.DSLCommandType;
import com.konloch.dsl.commands.DSLDefinedCommand;
import com.konloch.util.FastStringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * The DSLRuntime parses and executes Strings based on the supplied DSL.
 *
 * @author Konloch
 * @since Jan, 17th, 2017
 */
public class DSLRuntime
{
	private final DSL dsl;
	private final LinkedHashMap<String, DSLRuntimeCommand> commands = new LinkedHashMap<>();
	private String insideSubscript;
	
	/**
	 * Construct a new instance of the DSLRuntime
	 *
	 * @param dsl any DSL
	 */
	public DSLRuntime(DSL dsl)
	{
		this.dsl = dsl;
	}
	
	/**
	 * Signal to the runtime that the parsing has stopped externally.
	 */
	public void stopParse()
	{
		insideSubscript = null;
	}
	
	/**
	 * Attempts to parse any String and runs it if it's part of the init, or else it will store the DLSRuntimeCommand under the read subscript
	 *
	 * @param line any String
	 */
	public void parseLine(String line)
	{
		if (line == null)
			return;
		
		line = line.trim();
		
		if (line.isEmpty())
			return;
		
		if (line.startsWith(dsl.getCommentDelimiter()))
			return;
		
		//if not currently processing a subscript, parse normally
		if(insideSubscript == null)
		{
			if (line.contains(dsl.getBracketDelimiterStart()))
			{
				//turn the line into a runtime command
				DSLRuntimeCommand command = buildRuntimeCommand(line);
				
				//store the parsed DSL command to be processed if needed
				commands.put(command.getName(), command);
				
				//execute the runtime command
				execute(command);
			}
			else if (line.endsWith(dsl.getSubscriptDelimiterStart()))
			{
				String functionName = line.substring(0, line.length()-1).trim();
				
				if (dsl.getSubscripts().containsKey(functionName))
				{
					insideSubscript = functionName;
				}
			}
			else
			{
				//turn the line into a runtime command
				DSLRuntimeCommand command = buildRuntimeCommand(line);
				
				//store the parsed DSL command to be processed if needed
				commands.put(command.getName(), command);
				
				//execute the runtime command
				execute(command);
			}
		}
		else
		{
			if (line.contains(dsl.getSubscriptDelimiterEnd()))
			{
				insideSubscript = null;
			}
			else
			{
				//get the subscript list or create one if it doesn't exist
				ArrayList<DSLRuntimeCommand> sub;
				if(dsl.getSubscripts().containsKey(insideSubscript))
					sub = (ArrayList<DSLRuntimeCommand>) dsl.getSubscripts().get(insideSubscript);
				else
				{
					sub = new ArrayList<>();
					dsl.getSubscripts().put(insideSubscript, sub);
				}
				
				//turn the line into a runtime command
				DSLRuntimeCommand command = buildRuntimeCommand(line);
				
				//add the lines to the script
				sub.add(command);
			}
		}
	}
	
	/**
	 * Executes a specific DLSRuntimeCommand
	 *
	 * @param runtimeCommand any DLSRuntimeCommand
	 */
	public void execute(DSLRuntimeCommand runtimeCommand)
	{
		DSLDefinedCommand command = dsl.getCommands().get(runtimeCommand.getName());
		
		if(command == null)
		{
			//TODO if verbose or alternative if strict throw new RuntimeException
			//System.out.println("No variable or function named `" + runtimeCommand.getName() + "` has been defined.");
			return;
		}
		
		switch(command.getType())
		{
			case VARIABLE:
				command.getVariableRunnable().run(runtimeCommand.getVariableValue(this));
				break;
				
			case FUNCTION:
				command.getFunctionRunnable().run(runtimeCommand.getParameters());
				break;
		}
	}
	
	/**
	 * Attempts to build a DSLRuntimeCommand from any String.
	 *
	 * @param line any String
	 * @return the DSLRuntimeCommand if it can be created, if not it will return null
	 */
	public DSLRuntimeCommand buildRuntimeCommand(String line)
	{
		//look for the function bracket delimiters
		if (line.contains(dsl.getBracketDelimiterStart()) && line.contains(dsl.getBracketDelimiterEnd()))
		{
			String[] parameters = FastStringUtils.split(line, dsl.getBracketDelimiterStart(), 2);
			String name = parameters[0].trim();
			String values = null;
			
			if (parameters[1].length() >= 2)
			{
				values = FastStringUtils.split(parameters[1], dsl.getBracketDelimiterEnd())[0].trim();
			}
			
			if (values != null)
			{
				if (values.contains(","))
				{
					parameters = FastStringUtils.split(values, ",");
					
					//this allows the user to have spaces between the function separator.
					//one downside to doing it this way, is users lose the ability to have trailing whitespace.
					//this limitation is part of the specification along with the hardcoded `,` delimiter.
					//these should be looked at being changed at a later date if needed.
					for(int i = 0; i < parameters.length; i++)
						parameters[i] = parameters[i].trim();
				}
				else
				{
					parameters = new String[]{values};
				}
				
				return new DSLRuntimeCommand(DSLCommandType.VARIABLE, name, parameters);
			}
			else
			{
				return new DSLRuntimeCommand(DSLCommandType.VARIABLE, name, null);
			}
		}
		
		//does an unrestricted quick then first, then a deeper verification before it creates the variable
		if (line.contains(dsl.getSetValueDelimiter()))
		{
			String[] split = FastStringUtils.split(line, dsl.getSetValueDelimiter(), 2);
			String name = split[0];
			String value = split[1];
			
			//verify the data is valid, then make sure the runtime command has a handler
			//if it does, assume this is a variable
			//TODO if strict true, it should re-enable the runtime command handler check
			if (!(name == null || name.isEmpty() || value == null || value.isEmpty()))
				//&& dsl.getCommands().containsKey(name))
				return new DSLRuntimeCommand(DSLCommandType.VARIABLE, name, new String[]{value});
		}
		
		return null;
	}
	
	/**
	 * Returns all the DLSRuntimeCommands created during runtime.
	 *
	 * @return the commands in the form of a HashMap
	 */
	public HashMap<String, DSLRuntimeCommand> getCommands()
	{
		return commands;
	}
	
	/**
	 * Returns the DSL linked with this DSLRuntime instance.
	 *
	 * @return the DSL instance
	 */
	public DSL getDSL()
	{
		return dsl;
	}
}
