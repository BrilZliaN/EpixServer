package org.Epixcrafted.EpixServer.chat;

public enum Colour {

	BLACK("0"),
	DARK_BLUE("1"),
	DARK_GREEN("2"),
	DARK_CYAN("3"),
	DARK_RED("4"),
	PURPLE("5"),
	GOLD("6"),
	GRAY("7"),
	DARK_GRAY("8"),
	BLUE("9"),
	LIGHT_GREEN("a"),
	CYAN("b"),
	RED("c"),
	PINK("d"),
	YELLOW("e"),
	WHITE("f"),
	
	RANDOM("k"),
	BOLD("l"),
	STRIKETHROUGH("m"),
	UNDERLINED("n"),
	ITALIC("o"),
	RESET("r")
	;
	
	private String colour;
	
	Colour(String colour) {
		this.colour = colour;
	}
	
	@Override
	public String toString() {
		return "§" + colour;
	}
	
}
