package net.arbee.addola.mixins;

import net.arbee.addola.Gamerules;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public abstract class VillagerMixin extends MerchantEntity {

	public VillagerMixin(EntityType<? extends MerchantEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(at = @At("HEAD"), method = "mobTick")
	protected void mobTick(CallbackInfo ci) {
		if(getEntityWorld().getGameRules().getBoolean(Gamerules.VILLAGERS_FOLLOW)) {
			goalSelector.add(2, new TemptGoal(this, .8D, false, Ingredient.ofItems(Items.EMERALD_BLOCK, Items.EMERALD_ORE)));
			goalSelector.add(2, new TemptGoal(this, .4D, true, Ingredient.ofItems(Items.EMERALD)));
		} else {
			goalSelector.remove(new TemptGoal(this, .8D, false, Ingredient.ofItems(Items.EMERALD_BLOCK)));
			goalSelector.remove(new TemptGoal(this, .4D, true, Ingredient.ofItems(Items.EMERALD, Items.EMERALD_ORE)));
		}
	}
}