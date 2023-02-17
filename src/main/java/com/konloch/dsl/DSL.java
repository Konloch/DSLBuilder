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
 * @author Konloch
 * @since Jan, 17th, 2017
 */
public class DSL
{
	private final String setValueDelimiter;
	private final String bracketDelimiterStart;
	private final String bracketDelimiterEnd;
	private final String subScriptDelimiterStart;
	private final String subScriptDelimiterEnd;
	private final String commentDelimiter;
	private final HashMap<String, DSLDefinedCommand> commands = new HashMap<>();
	private final HashMap<String, List<DSLRuntimeCommand>> subscripts = new HashMap<>();
	private final DSLRuntime runtime = new DSLRuntime(this);
	
	public DSL(String setValueDelimiter, String bracketDelimiterStart, String bracketDelimiterEnd,
	           String subScriptDelimiterStart, String subScriptDelimiterEnd, String commentDelimiter)
	{
		this.setValueDelimiter = setValueDelimiter;
		this.bracketDelimiterStart = bracketDelimiterStart;
		this.bracketDelimiterEnd = bracketDelimiterEnd;
		this.subScriptDelimiterStart = subScriptDelimiterStart;
		this.subScriptDelimiterEnd = subScriptDelimiterEnd;
		this.commentDelimiter = commentDelimiter;
	}
	
	public DSL clear()
	{
		commands.clear();
		subscripts.clear();
		return this;
	}
	
	public DSL parse(File file) throws IOException
	{
		parse(DiskReader.read(file));
		return this;
	}
	
	public DSL parse(ArrayList<String> fileContents) throws IOException
	{
		for(String line : fileContents)
			runtime.parseLine(line);
		
		runtime.stopParse();
		return this;
	}
	
	public DSL addVar(String name, VariableRunnable variableRunnable)
	{
		commands.put(name, new DSLDefinedCommand(name, variableRunnable));
		return this;
	}
	
	public DSL removeVar(String name)
	{
		DSLDefinedCommand command = commands.get(name);
		if(command != null && command.getCommandType() == DSLCommandType.VARIABLE)
			commands.remove(name);
		
		return this;
	}
	
	public DSL addFunc(String name, FunctionRunnable functionRunnable)
	{
		commands.put(name, new DSLDefinedCommand(name, functionRunnable));
		return this;
	}
	
	public DSL removeFunc(String name)
	{
		DSLDefinedCommand command = commands.get(name);
		if(command != null && command.getCommandType() == DSLCommandType.FUNCTION)
			commands.remove(name);
		
		return this;
	}
	
	public DSL addSub(String name)
	{
		subscripts.put(name, new ArrayList<>());
		return this;
	}
	
	public DSL removeSub(String name)
	{
		subscripts.remove(name);
		return this;
	}
	
	public DSL executeSubscript(String name)
	{
		List<DSLRuntimeCommand> functionContents = subscripts.get(name);
		
		if(functionContents == null)
			throw new RuntimeException("Subscript " + name + " not found");
		
		for (DSLRuntimeCommand command : functionContents)
			runtime.execute(command);
		
		return this;
	}
	
	public String getSetValueDelimiter()
	{
		return setValueDelimiter;
	}
	
	public String getBracketDelimiterStart()
	{
		return bracketDelimiterStart;
	}
	
	public String getBracketDelimiterEnd()
	{
		return bracketDelimiterEnd;
	}
	
	public String getSubScriptDelimiterStart()
	{
		return subScriptDelimiterStart;
	}
	
	public String getSubScriptDelimiterEnd()
	{
		return subScriptDelimiterEnd;
	}
	
	public String getCommentDelimiter()
	{
		return commentDelimiter;
	}
	
	public HashMap<String, DSLDefinedCommand> getCommands()
	{
		return commands;
	}
	
	public HashMap<String, List<DSLRuntimeCommand>> getSubscripts()
	{
		return subscripts;
	}
}
