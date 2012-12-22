package org.Epixcrafted.EpixServer.engine;

import java.util.logging.*;



public class ConsoleLogManager
{
    /** Reference to the logger. */
    public static Logger logger = Logger.getLogger("EpixServer");
	public static final String driver = "com.mysql.jdbc.Driver";
	
    public ConsoleLogManager()
    {
    }

    /**
     * Initializes the console logger.
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
    }
    
    public static void addHandler(Handler handler) {
        logger.addHandler(handler);
    }
}
