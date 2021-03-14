package com.drafakiller.brokenblock;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public interface Message {
	
	Component MAIN = Component.text()
		.append(Component.text("BrokenBlock: ").decorate(TextDecoration.BOLD))
		.append(Component.text("ON").color(NamedTextColor.GREEN).decorate(TextDecoration.BOLD)
			.hoverEvent(Component.text("Click here to turn ")
				.append(Component.text("ON", NamedTextColor.GREEN, TextDecoration.BOLD))
				.append(Component.text(" BrokenBlock.")))
			.clickEvent(ClickEvent.runCommand("/brokenblock toggle on")))
		.append(Component.text(" "))
		.append(Component.text("OFF").color(NamedTextColor.RED).decorate(TextDecoration.BOLD)
			.hoverEvent(Component.text("Click here to turn ")
				.append(Component.text("OFF", NamedTextColor.RED, TextDecoration.BOLD))
				.append(Component.text(" BrokenBlock.")))
			.clickEvent(ClickEvent.runCommand("/brokenblock toggle off")))
		.build();
		
	Component TOGGLED_ON = Component.text()
		.append(Component.text("You have toggled BrokenBlock "))
		.append(Component.text("ON", NamedTextColor.GREEN, TextDecoration.BOLD))
		.append(Component.text("."))
		.build();
		
	Component TOGGLED_OFF = Component.text()
		.append(Component.text("You have toggled BrokenBlock "))
		.append(Component.text("OFF", NamedTextColor.RED, TextDecoration.BOLD))
		.append(Component.text("."))
		.build();
		
	Component ALREADY_TOGGLED_ON = Component.text()
		.append(Component.text("BrokenBlock is already toggled "))
		.append(Component.text("ON", NamedTextColor.GREEN, TextDecoration.BOLD))
		.append(Component.text("."))
		.build();
		
	Component ALREADY_TOGGLED_OFF = Component.text()
		.append(Component.text("BrokenBlock is already toggled "))
		.append(Component.text("OFF", NamedTextColor.RED, TextDecoration.BOLD))
		.append(Component.text("."))
		.build();
	
}
