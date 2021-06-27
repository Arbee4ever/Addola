package net.arbee.addola.mixins;

import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.arbee.addola.ReferenceClient;
import net.arbee.addola.screens.configscreen;
import net.arbee.addola.util.configgui;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuMixin extends Screen {

	protected GameMenuMixin(Text title) {
		super(title);
	}

	@Inject (at = @At("HEAD"), method = "initWidgets()V")	
	private void initWidgets(CallbackInfo info) {
		if(ReferenceClient.config.settingsButtonOn == true)
		{
			this.addButton(new TexturedButtonWidget(this.width - 21, 13, 20, 20, 0, 0, 20, new Identifier("addola:textures/gui/addolasettings.png"), 32, 64, (buttonWidgetx) -> {
		         this.client.openScreen(new configscreen(new configgui(null)));
		    }));
		}
	}
}