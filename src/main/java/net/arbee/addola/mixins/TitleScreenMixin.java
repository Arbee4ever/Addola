package net.arbee.addola.mixins;

import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.arbee.addola.ReferenceClient;
import net.arbee.addola.screens.configscreen;
import net.arbee.addola.util.configgui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

	protected TitleScreenMixin(Text title) {
		super(title);
	}

	@Inject (at = @At("HEAD"), method = "init()V")
	private void init(CallbackInfo info) {
		if(ReferenceClient.config.settingsButtonOn)
		{
			this.addButton(new TexturedButtonWidget(this.width - 21, 13, 20, 20, 0, 0, 20, new Identifier("addola:textures/gui/addolasettings.png"), 32, 64, (buttonWidget) -> {
		         client.openScreen(new configscreen(new configgui(this.client.currentScreen)));
		    }));
		}
	}
}