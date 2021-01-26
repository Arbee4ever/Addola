package net.arbee.addola.util;

import blue.endless.jankson.Comment;

public class AddolaConfig {
	@Comment("Whether the Addola Settings Button should be shown.")
	public boolean settingsButtonOn = true;
	@Comment("Whether Villagers follow Players holding Emerald Blocks/Ore.")
	public boolean villagersFollow = true;
	@Comment("Whether Sneaking through Berry Bushes does Damage.")
	public boolean sneakBerryBush = true;
}
