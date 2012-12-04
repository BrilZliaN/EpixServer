package org.Epixcrafted.EpixServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.Epixcrafted.EpixServer.chat.commands.AllCommands;
import org.Epixcrafted.EpixServer.engine.EpixPipelineFactory;
import org.Epixcrafted.EpixServer.mc.entity.EntityPlayer;
import org.Epixcrafted.EpixServer.mc.threads.Time;
import org.Epixcrafted.EpixServer.mc.threads.TimeSenderThread;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;

@SuppressWarnings("unused")
public class EpixServer extends ServerBootstrap {
	
	public static final String MINECRAFT_VERSION = "1.4.2";
	public static final int PROTOCOL_VERSION = 47;
	private static final Logger log = Logger.getLogger("EpixServer");
	
	public String ip = "0.0.0.0";
	public int port = 25565;
	public boolean showConsole = true;
	
	private ChannelFactory cfactory;
	private ExecutorService bossExec;
	private ExecutorService ioExec;
	
	public static EpixServer instance;
	
	public static int allEntitiesUsedIds = 0;
	
	public static ArrayList<EntityPlayer> players = new ArrayList<EntityPlayer>();
	public static AllCommands clist;
	private static Time timer;
	private TimeSenderThread timesender;
	
	public EpixServer(String ip, int port, boolean showConsole) {
		this.ip = ip;
		this.port = port > 65535 || port < 1024 ? 25565 : port;
		this.showConsole = showConsole;
		
		this.setFactory(cfactory = new NioServerSocketChannelFactory(bossExec = new OrderedMemoryAwareThreadPoolExecutor(1, 400000000, 2000000000, 60, TimeUnit.SECONDS), ioExec = new OrderedMemoryAwareThreadPoolExecutor(4, 400000000, 2000000000, 60, TimeUnit.SECONDS)));
		this.setOption("backlog", 500);
		this.setOption("connectTimeoutMillis", 10000);
		this.setPipelineFactory(new EpixPipelineFactory());
	}
	
	public void init() {
		info("EpixServer is starting... (implementing MC " + MINECRAFT_VERSION + " version)");
		try {
			this.bind(new InetSocketAddress(ip, port));
		} catch (Exception e) {
			error("Error while binding to /" + ip + ":" + port);
			error("Maybe some application is using this port?");
			return;
		} finally {
			info("Started listening on " + ip + ":" + port);
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
	
	public static void main(String ip, int port, boolean showConsole) {
		new EpixServer(ip, port, showConsole).init();
	}

}
