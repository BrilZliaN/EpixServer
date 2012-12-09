package org.Epixcrafted.EpixServer;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.Epixcrafted.EpixServer.chat.commands.AllCommands;
import org.Epixcrafted.EpixServer.chat.commands.CommandList;
import org.Epixcrafted.EpixServer.engine.EpixPipelineFactory;
import org.Epixcrafted.EpixServer.engine.Server;
import org.Epixcrafted.EpixServer.engine.player.Session;
import org.Epixcrafted.EpixServer.engine.player.SessionList;
import org.Epixcrafted.EpixServer.log.ConsoleLogManager;
import org.Epixcrafted.EpixServer.mc.entity.EntityPlayer;
import org.Epixcrafted.EpixServer.mc.threads.TickCounter;
import org.Epixcrafted.EpixServer.mc.threads.Time;
import org.Epixcrafted.EpixServer.mysql.MySQL;
import org.Epixcrafted.EpixServer.mysql.MySQLHandler;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;

@SuppressWarnings("unused")
public class EpixServer implements Server {
	
	private String ip;
	private int port;
	private String mysqlIP;
	private String mysqlUser;
	private String mysqlPass;
	private String mysqlDB;
	
	private MySQL mysql;
	
	private final ServerBootstrap bootstrap = new ServerBootstrap();
	private final ChannelGroup channelList = new DefaultChannelGroup();
	private final SessionList sessionList = new SessionList();
	private final AllCommands commandList = new AllCommands();
	private final Logger log = Logger.getLogger("EpixServer");
	
	public static int onlinePlayers = 0; //TODO make this private & move
	public static int maxPlayers = 32767; //TODO make this private & move to an another place
	
	public static int lastEntityId = 0; //TODO: move this to an another place!
	
	public EpixServer() {
		ConsoleLogManager.init();
        ExecutorService bossexecutor = new OrderedMemoryAwareThreadPoolExecutor(1, 400000000, 2000000000, 60, TimeUnit.SECONDS);
        ExecutorService ioexecutor = new OrderedMemoryAwareThreadPoolExecutor(4, 400000000, 2000000000, 60, TimeUnit.SECONDS);
        bootstrap.setFactory(new NioServerSocketChannelFactory(bossexecutor, ioexecutor));
        bootstrap.setPipelineFactory(new EpixPipelineFactory(this));
        bootstrap.setOption("backlog", 500);
        bootstrap.setOption("connectTimeoutMillis", 10000);
		readConfiguration();
	}
	
	@Override
	public void start() {
		log.info("EpixServer is starting... (implementing MC " + getMinecraftVersion() + " version)");
		try {
			bootstrap.bind(new InetSocketAddress(ip, port));
			log.info("Started listening on " + ip + ":" + port);
		} catch (Exception e) {
			log.severe("Error while binding to " + ip + ":" + port);
			log.severe("Maybe some application is using this port?");
			System.exit(0);
		}
		
		setupMysqlConnection();
		ConsoleLogManager.addHandler(new MySQLHandler(ConsoleLogManager.driver));
		setupMisc();
	}

	@Override
	public void shutdown() {
        bootstrap.getFactory().releaseExternalResources();
        System.exit(0);
	}

	@Override
	public String getMinecraftVersion() {
		return "1.4.5";
	}
	
	public AllCommands getCommandList() {
		return commandList;
	}
	
	public void addChannel(Channel channel, Session session) {
        channelList.add(channel);
        sessionList.add(session);
	}
	
	public void removeChannel(Channel channel, Session session) {
        channelList.remove(channel);
        sessionList.remove(session);
	}
	
	public SessionList getSessionListClass() {
		return sessionList;
	}
	
	public ArrayList<Session> getSessionList() {
		return sessionList.getSessionList();
	}
	
	public Logger getLogger() {
		return log;
	}
	
	public MySQL getMySQL() {
		return mysql;
	}
	
	private void readConfiguration() {
		PropertyManager settings = new PropertyManager(new File("server.conf"));
		this.ip = settings.getStringProperty("listen_ip", "0.0.0.0");
		this.port = settings.getIntProperty("listen_port", 25565);
		this.mysqlIP = settings.getStringProperty("mysql_host", "127.0.0.1");
		this.mysqlUser = settings.getStringProperty("mysql_user", "root");
		this.mysqlPass = settings.getStringProperty("mysql_pass", "");
		this.mysqlDB = settings.getStringProperty("mysql_db", "mc");
	}
	
	private void setupMysqlConnection() {
		try {
			mysql = new MySQL();
			mysql.connect(mysqlUser, mysqlPass, mysqlIP, mysqlDB);
		} catch (Exception e) {
			log.severe("Cannot connect to MySQL server!");
			System.exit(0);
		}
	}
	
	private void setupMisc() {
		new TickCounter().start();
	}
}
