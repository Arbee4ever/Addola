package net.arbee.addola.mixins;

import net.arbee.addola.entity.ai.goal.EmeraldGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.Goal;
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
		final Goal EmeraldBlockGoal = new EmeraldGoal(this, .6D, Ingredient.ofItems(Items.EMERALD_BLOCK), false);
		final Goal EmeraldOreItemGoal = new EmeraldGoal(this, .4D, Ingredient.ofItems(Items.EMERALD, Items.EMERALD_ORE), false);

		goalSelector.add(2, EmeraldBlockGoal);
		goalSelector.add(2, EmeraldOreItemGoal);
	}
}