package org.Epixcrafted.EpixServer.mysql;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.Epixcrafted.EpixServer.Main;

public class MySQLHandler extends Handler {
        String driverName;
        Connection connection;
        protected final static String insertSQL = "insert into server_log (level,logger,message,sequence,"
                        + "sourceClass,sourceMethod,threadID,timeEntered,server_name)"
                        + "values(?,?,?,?,?,?,?,?,?)";
        protected final static String clearSQL = "delete from server_log;";
        protected PreparedStatement ptmtInsert;
        protected PreparedStatement ptmtClear;

        public MySQLHandler(String driverName) {
                try {
                        this.driverName = driverName;
						Class.forName(driverName);
                        connection = Main.getServer().getMySQL().connection;
                        ptmtInsert = connection.prepareStatement(insertSQL);
                        ptmtClear = connection.prepareStatement(clearSQL);
                } catch (ClassNotFoundException e) {
                        System.err.println("Error on open: " + e);
                } catch (SQLException e) {
                        System.err.println("Error on open: " + e);
                }
        }

        @Override
        public void close() throws SecurityException {

        }

        @Override
        public void flush() {
        	
        }

        static public String trunc(String str, int length) {
                if (str.length() < length)
                        return str;
                return (str.substring(0, length));
        }

        @Override
        public void publish(LogRecord record) {
                try {
                        // adding entry to log
                        ptmtInsert.setInt(1, record.getLevel().intValue());
                        ptmtInsert.setString(2, trunc(record.getLoggerName(), 63));
                        String mess = record.getMessage();
                        mess.replace("§", "");
                        ptmtInsert.setString(3, trunc(mess, 255));
                        ptmtInsert.setLong(4, record.getSequenceNumber());
                        ptmtInsert.setString(5, trunc(record.getSourceClassName(), 63));
                        ptmtInsert.setString(6, trunc(record.getSourceMethodName(), 31));
                        ptmtInsert.setInt(7, record.getThreadID());
                        ptmtInsert.setTimestamp(8,
                                        new Timestamp(System.currentTimeMillis()));
						ptmtInsert.setString(9, trunc("EpixServer", 255)); //TODO: Server MOTD usage
                        ptmtInsert.executeUpdate();
                } catch (Exception e) {
                        System.err.println(e.toString());
                }
        }

        public void clear() {
                try {
                        ptmtClear.executeUpdate();
                } catch (SQLException e) {
                        System.err.println("Error on clear: " + e);
                }
        }
}