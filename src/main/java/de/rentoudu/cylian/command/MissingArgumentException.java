package de.rentoudu.cylian.command;

import org.bukkit.command.CommandException;

@SuppressWarnings("serial")
public class MissingArgumentException extends CommandException {

	public MissingArgumentException(String argumentName) {
		super("Missing argument: " + argumentName);
	}
	
}
