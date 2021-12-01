package net.arbee.addola.mixins;

import net.arbee.addola.ReferenceClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.village.VillagerType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity {

	public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;Lnet/minecraft/village/VillagerType;)V", at = @At(value = "TAIL"))
	private void inject(EntityType<? extends VillagerEntity> entityType, World world, VillagerType type, CallbackInfo ci) {
		if(ReferenceClient.config.villagersFollow == true)
		{
			this.goalSelector.add(2, new TemptGoal(this, .4D, Ingredient.ofItems(Items.EMERALD_BLOCK), false));
			this.goalSelector.add(2, new TemptGoal(this, .2D, Ingredient.ofItems(Items.EMERALD), true));
		}
	}
}