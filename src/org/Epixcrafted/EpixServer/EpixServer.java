package org.Epixcrafted.EpixServer;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.Epixcrafted.EpixServer.chat.commands.AllCommands;
import org.Epixcrafted.EpixServer.engine.EpixPipelineFactory;
import org.Epixcrafted.EpixServer.log.ConsoleLogManager;
import org.Epixcrafted.EpixServer.mc.entity.EntityPlayer;
import org.Epixcrafted.EpixServer.mc.threads.Time;
import org.Epixcrafted.EpixServer.mc.threads.TimeSenderThread;
import org.Epixcrafted.EpixServer.mysql.MySQL;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;

@SuppressWarnings("unused")
public class EpixServer extends ServerBootstrap {
	
	public static final String MINECRAFT_VERSION = "1.4.5";
	public static final int PROTOCOL_VERSION = 49;
	private static final Logger log = Logger.getLogger("EpixServer");
	
	public static PropertyManager settings;
	public static MySQL mysql;
	
	public String ip;
	public int port;
	
	public static String serverName;
	
	private ChannelFactory cfactory;
	private ExecutorService bossExec;
	private ExecutorService ioExec;
	
	public static EpixServer instance;
	
	public static int allEntitiesUsedIds = 0;
	
	public static ArrayList<EntityPlayer> players = new ArrayList<EntityPlayer>();
	public static AllCommands clist;
	private static Time timer;
	private TimeSenderThread timesender;
	
	public EpixServer() {
		settings = new PropertyManager(new File("server.conf"));
		try {
			mysql = new MySQL();
			String mysql_host = settings.getStringProperty("mysql_host", "127.0.0.1");
			String mysql_user = settings.getStringProperty("mysql_user", "root");
			String mysql_pass = settings.getStringProperty("mysql_pass", "");
			String mysql_db = settings.getStringProperty("mysql_db", "mcserver");
			serverName = settings.getStringProperty("server_name", "mcserver");
			info("Connection to the MySQL server");
			mysql.connect(mysql_user, mysql_pass, mysql_host, mysql_db);
		} catch(Exception e) {
			error("Cannot connect to MySQL server!");
			System.exit(0);
		}
		ConsoleLogManager.init();
		this.ip = settings.getStringProperty("listen_ip", "0.0.0.0");
		this.port = settings.getIntProperty("listen_port", 25565);
		
		this.setFactory(cfactory = new NioServerSocketChannelFactory(bossExec = new OrderedMemoryAwareThreadPoolExecutor(1, 400000000, 2000000000, 60, TimeUnit.SECONDS), ioExec = new OrderedMemoryAwareThreadPoolExecutor(4, 400000000, 2000000000, 60, TimeUnit.SECONDS)));
		this.setOption("backlog", 500);
		this.setOption("connectTimeoutMillis", 10000);
		this.setPipelineFactory(new EpixPipelineFactory());
	}
	
	public void init() {
		info("EpixServer is starting... (implementing MC " + MINECRAFT_VERSION + " version)");
		try {
			this.bind(new InetSocketAddress(ip, port));
			info("Started listening on " + ip + ":" + port);
		} catch (Exception e) {
			error("Error while binding to " + ip + ":" + port);
			error("Maybe some application is using this port?");
			System.exit(0);
		}
		
		(timer = new Time()).start();
		(timesender = new TimeSenderThread()).start();
		
		clist = new AllCommands();
	}
	
	public static void info(String s) {
		log.info(s);
	}

	public static void warning(String s) {
		log.warning(s);
	}
	
	public static void error(String s) {
		log.severe(s);
	}
	
	public static int getTime() {
		return timer.getTime();
	}
	
	public static void main() {
		new EpixServer().init();
	}

}
