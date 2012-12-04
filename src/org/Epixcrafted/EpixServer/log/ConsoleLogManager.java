package org.Epixcrafted.EpixServer.log;

import java.util.logging.*;


public class ConsoleLogManager
{
    /** Reference to the logger. */
    public static Logger logger = Logger.getLogger("EpixServer");
	static public final String driver = "com.mysql.jdbc.Driver";
	
    public ConsoleLogManager()
    {
    }

    /**
     * Initialises the console logger.
     */
    public static void init()
    {
        ConsoleLogFormatter consolelogformatter = new ConsoleLogFormatter();
        logger.setUseParentHandlers(false);
        ConsoleHandler consolehandler = new ConsoleHandler();
        consolehandler.setFormatter(consolelogformatter);
        logger.addHandler(consolehandler);

        try
        {
            FileHandler filehandler = new FileHandler("server.log", true);
            filehandler.setFormatter(consolelogformatter);
            logger.addHandler(filehandler);
        }
        catch (Exception exception)
        {
            logger.log(Level.WARNING, "Failed to log to server.log", exception);
        }
        
        //MySQLHandler jdbcHandler = new MySQLHandler(driver); //TODO: Fix NPE.
        //logger.addHandler(jdbcHandler);
    }
}
