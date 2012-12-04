package org.Epixcrafted.EpixServer;

public class Main {
	
	public static void main(String[] args) {
		String[] parsed = parseArgs(args);
		String ip = parsed[0] == "false" ||  parsed[0] == null ? "0.0.0.0" : parsed[0];
		String port = parsed[1] == "false" ||  parsed[1] == null ? "25566" : parsed[1];
		String showConsole = parsed[2] == null ? "true" : parsed[2];
		EpixServer.main(ip, Integer.parseInt(port), Boolean.getBoolean(showConsole));
	}
	
	private static String[] parseArgs(String[] args) {
		String[] parsed = new String[3];
		for(String arg : args) {
			if (arg.contains("--ip")) {
				parsed[0] = arg.split("=")[1];
			} else {
				parsed[0] = "false";
			}
			if (arg.contains("--port")) {
				parsed[1] = arg.split("=")[1];
			} else {
				parsed[1] = "false";
			}
			if (arg.contains("--showconsole")) {
				parsed[2] = arg.split("=")[1];
			} else {
				parsed[2] = "true";
			}
		}
		return parsed;
	}

}
