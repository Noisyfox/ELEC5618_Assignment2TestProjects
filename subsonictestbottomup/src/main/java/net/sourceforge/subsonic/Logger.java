/*
 This file is part of Subsonic.

 Subsonic is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Subsonic is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Subsonic.  If not, see <http://www.gnu.org/licenses/>.

 Copyright 2009 (C) Sindre Mehus
 */
package net.sourceforge.subsonic;

/**
 * Stub class of Logger
 */
public class Logger
{

	/**
	 * Creates a logger for the given class.
	 *
	 * @param clazz The class.
	 * @return A logger for the class.
	 */
	public static Logger getLogger( Class clazz )
	{
		return new Logger( clazz.getName() );
	}

	/**
	 * Creates a logger for the given namee.
	 *
	 * @param name The name.
	 * @return A logger for the name.
	 */
	public static Logger getLogger( String name )
	{
		return new Logger( name );
	}

	private Logger( String name )
	{
	}

	/**
	 * Logs a debug message.
	 *
	 * @param message The log message.
	 */
	public void debug( Object message )
	{
		debug( message, null );
	}

	/**
	 * Logs a debug message.
	 *
	 * @param message The message.
	 * @param error   The optional exception.
	 */
	public void debug( Object message, Throwable error )
	{
		if( isDebugEnabled() )
		{
			add( Level.DEBUG, message, error );
		}
	}

	private static boolean isDebugEnabled()
	{
		return true;
	}

	/**
	 * Logs an info message.
	 *
	 * @param message The message.
	 */
	public void info( Object message )
	{
		info( message, null );
	}

	/**
	 * Logs an info message.
	 *
	 * @param message The message.
	 * @param error   The optional exception.
	 */
	public void info( Object message, Throwable error )
	{
		add( Level.INFO, message, error );
	}

	/**
	 * Logs a warning message.
	 *
	 * @param message The message.
	 */
	public void warn( Object message )
	{
		warn( message, null );
	}

	/**
	 * Logs a warning message.
	 *
	 * @param message The message.
	 * @param error   The optional exception.
	 */
	public void warn( Object message, Throwable error )
	{
		add( Level.WARN, message, error );
	}

	/**
	 * Logs an error message.
	 *
	 * @param message The message.
	 */
	public void error( Object message )
	{
		error( message, null );
	}

	/**
	 * Logs an error message.
	 *
	 * @param message The message.
	 * @param error   The optional exception.
	 */
	public void error( Object message, Throwable error )
	{
		add( Level.ERROR, message, error );
	}

	private void add( Level level, Object message, Throwable error )
	{
	}

	/**
	 * Log level.
	 */
	public enum Level
	{
		DEBUG, INFO, WARN, ERROR
	}

}
