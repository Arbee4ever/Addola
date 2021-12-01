package net.arbee.addola.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

import java.util.Collection;

public class PlayerstatCommand {
    private static final SimpleCommandExceptionType HUNGER_SET_FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.hunger.set.failed"));
    private static final SimpleCommandExceptionType HUNGER_ADD_FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.hunger.add.failed"));
    private static final SimpleCommandExceptionType HUNGER_REMOVE_FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.hunger.remove.failed"));
    private static final SimpleCommandExceptionType HEALTH_SET_FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.health.set.failed"));
    private static final SimpleCommandExceptionType HEALTH_ADD_FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.health.add.failed"));
    private static final SimpleCommandExceptionType HEALTH_REMOVE_FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.health.remove.failed"));
    private static final SimpleCommandExceptionType SATURATION_SET_FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.saturation.set.failed"));
    private static final SimpleCommandExceptionType SATURATION_ADD_FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.saturation.add.failed"));
    private static final SimpleCommandExceptionType SATURATION_REMOVE_FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.saturation.remove.failed"));

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("playerstat")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.literal("hunger")
                        .then(CommandManager.literal("set")
                                .then(CommandManager.argument("targets", EntityArgumentType.entities())
                                        .then(CommandManager.argument("amount", IntegerArgumentType.integer())
                                                .executes((commandContext) -> {
                                                    return executeSetHunger(commandContext.getSource(),
                                                            EntityArgumentType.getPlayers(commandContext, "targets"),
                                                            IntegerArgumentType.getInteger(commandContext, "amount"));
                                                })))))
                .then(CommandManager.literal("hunger")
                        .then(CommandManager.literal("add")
                            .then(CommandManager.argument("targets", EntityArgumentType.entities())
                                    .then(CommandManager.argument("amount",IntegerArgumentType.integer())
                                            .executes((commandContext) -> {
                                                return executeAddHunger(commandContext.getSource(),
                                                        EntityArgumentType.getPlayers(commandContext, "targets"),
                                                        IntegerArgumentType.getInteger(commandContext, "amount"));
                                            })))))
                .then(CommandManager.literal("hunger")
                        .then(CommandManager.literal("remove")
                                .then(CommandManager.argument("targets", EntityArgumentType.entities())
                                        .then(CommandManager.argument("amount",IntegerArgumentType.integer())
                                                .executes((commandContext) -> {
                                                    return executeRemoveHunger(commandContext.getSource(),
                                                            EntityArgumentType.getPlayers(commandContext, "targets"),
                                                            IntegerArgumentType.getInteger(commandContext, "amount"));
                                                })))))
                .then(CommandManager.literal("health")
                        .then(CommandManager.literal("set")
                                .then(CommandManager.argument("targets", EntityArgumentType.entities())
                                        .then(CommandManager.argument("amount", IntegerArgumentType.integer())
                                                .executes((commandContext) -> {
                                                    return executeSetHealth(commandContext.getSource(),
                                                            EntityArgumentType.getPlayers(commandContext, "targets"),
                                                            IntegerArgumentType.getInteger(commandContext, "amount"));
                                                })))))
                .then(CommandManager.literal("health")
                        .then(CommandManager.literal("add")
                                .then(CommandManager.argument("targets", EntityArgumentType.entities())
                                        .then(CommandManager.argument("amount",IntegerArgumentType.integer())
                                                .executes((commandContext) -> {
                                                    return executeAddHealth(commandContext.getSource(),
                                                            EntityArgumentType.getPlayers(commandContext, "targets"),
                                                            IntegerArgumentType.getInteger(commandContext, "amount"));
                                                })))))
                .then(CommandManager.literal("health")
                        .then(CommandManager.literal("remove")
                                .then(CommandManager.argument("targets", EntityArgumentType.entities())
                                        .then(CommandManager.argument("amount",IntegerArgumentType.integer())
                                                .executes((commandContext) -> {
                                                    return executeRemoveHealth(commandContext.getSource(),
                                                            EntityArgumentType.getPlayers(commandContext, "targets"),
                                                            IntegerArgumentType.getInteger(commandContext, "amount"));
                                                })))))
                .then(CommandManager.literal("saturation")
                        .then(CommandManager.literal("set")
                                .then(CommandManager.argument("targets", EntityArgumentType.entities())
                                        .then(CommandManager.argument("amount", IntegerArgumentType.integer())
                                                .executes((commandContext) -> {
                                                    return executeSetSaturation(commandContext.getSource(),
                                                            EntityArgumentType.getPlayers(commandContext, "targets"),
                                                            IntegerArgumentType.getInteger(commandContext, "amount"));
                                                })))))
                .then(CommandManager.literal("saturation")
                        .then(CommandManager.literal("add")
                                .then(CommandManager.argument("targets", EntityArgumentType.entities())
                                        .then(CommandManager.argument("amount",IntegerArgumentType.integer())
                                                .executes((commandContext) -> {
                                                    return executeAddSaturation(commandContext.getSource(),
                                                            EntityArgumentType.getPlayers(commandContext, "targets"),
                                                            IntegerArgumentType.getInteger(commandContext, "amount"));
                                                })))))
                .then(CommandManager.literal("saturation")
                        .then(CommandManager.literal("remove")
                                .then(CommandManager.argument("targets", EntityArgumentType.entities())
                                        .then(CommandManager.argument("amount",IntegerArgumentType.integer())
                                                .executes((commandContext) -> {
                                                    return executeRemoveSaturation(commandContext.getSource(),
                                                            EntityArgumentType.getPlayers(commandContext, "targets"),
                                                            IntegerArgumentType.getInteger(commandContext, "amount"));
                                                })))))
        );
    }

    private static int executeSetHunger(ServerCommandSource source, Collection<ServerPlayerEntity> targets, int amount) throws CommandSyntaxException {
        for (ServerPlayerEntity player : targets) {
            player.getHungerManager().setFoodLevel(amount);
        }

        if (targets.size() == 0) {
            throw HUNGER_SET_FAILED_EXCEPTION.create();
        } else {
            if (targets.size() == 1) {
                source.sendFeedback(new TranslatableText("commands.hunger.set.success.single", targets.iterator().next().getDisplayName(), amount), true);
            } else {
                source.sendFeedback(new TranslatableText("commands.hunger.set.success.multiple", targets.size(), amount), true);
            }
        }

        return amount;
    }

    private static int executeAddHunger(ServerCommandSource source, Collection<ServerPlayerEntity> targets, int amount) throws CommandSyntaxException {
        for (ServerPlayerEntity player : targets) {
            player.getHungerManager().setFoodLevel(player.getHungerManager().getFoodLevel() + amount);
        }

        if (targets.size() == 0) {
            throw HUNGER_ADD_FAILED_EXCEPTION.create();
        } else {
            if (targets.size() == 1) {
                source.sendFeedback(new TranslatableText("commands.hunger.add.success.single", amount, targets.iterator().next().getDisplayName()), true);
            } else {
                source.sendFeedback(new TranslatableText("commands.hunger.add.success.multiple", amount, targets.size()), true);
            }
        }

        return amount;
    }

    private static int executeRemoveHunger(ServerCommandSource source, Collection<ServerPlayerEntity> targets, int amount) throws CommandSyntaxException {
        for (ServerPlayerEntity player : targets) {
            player.getHungerManager().setFoodLevel(player.getHungerManager().getFoodLevel() - amount);
        }

        if (targets.size() == 0) {
            throw HUNGER_REMOVE_FAILED_EXCEPTION.create();
        } else {
            if (targets.size() == 1) {
                source.sendFeedback(new TranslatableText("commands.hunger.remove.success.single", amount, targets.iterator().next().getDisplayName()), true);
            } else {
                source.sendFeedback(new TranslatableText("commands.hunger.remove.success.multiple", amount, targets.size()), true);
            }
        }

        return amount;
    }

    private static int executeSetHealth(ServerCommandSource source, Collection<ServerPlayerEntity> targets, int amount) throws CommandSyntaxException {
        for (ServerPlayerEntity player : targets) {
            player.setHealth(amount);
        }

        if (targets.size() == 0) {
            throw HEALTH_SET_FAILED_EXCEPTION.create();
        } else {
            if (targets.size() == 1) {
                source.sendFeedback(new TranslatableText("commands.health.set.success.single", targets.iterator().next().getDisplayName(), amount), true);
            } else {
                source.sendFeedback(new TranslatableText("commands.health.set.success.multiple", targets.size(), amount), true);
            }
        }

        return amount;
    }

    private static int executeAddHealth(ServerCommandSource source, Collection<ServerPlayerEntity> targets, int amount) throws CommandSyntaxException {
        for (ServerPlayerEntity player : targets) {
            player.setHealth(player.getHealth() + amount);
        }

        if (targets.size() == 0) {
            throw HEALTH_ADD_FAILED_EXCEPTION.create();
        } else {
            if (targets.size() == 1) {
                source.sendFeedback(new TranslatableText("commands.health.add.success.single", amount, targets.iterator().next().getDisplayName()), true);
            } else {
                source.sendFeedback(new TranslatableText("commands.health.add.success.multiple", amount, targets.size()), true);
            }
        }

        return amount;
    }

    private static int executeRemoveHealth(ServerCommandSource source, Collection<ServerPlayerEntity> targets, int amount) throws CommandSyntaxException {
        for (ServerPlayerEntity player : targets) {
            player.setHealth(player.getHealth() - amount);
        }

        if (targets.size() == 0) {
            throw HEALTH_REMOVE_FAILED_EXCEPTION.create();
        } else {
            if (targets.size() == 1) {
                source.sendFeedback(new TranslatableText("commands.health.remove.success.single", amount, targets.iterator().next().getDisplayName()), true);
            } else {
                source.sendFeedback(new TranslatableText("commands.health.remove.success.multiple", amount, targets.size()), true);
            }
        }

        return amount;
    }

    private static int executeSetSaturation(ServerCommandSource source, Collection<ServerPlayerEntity> targets, int amount) throws CommandSyntaxException {
        for (ServerPlayerEntity player : targets) {
            player.getHungerManager().setSaturationLevel(amount);
        }

        if (targets.size() == 0) {
            throw SATURATION_SET_FAILED_EXCEPTION.create();
        } else {
            if (targets.size() == 1) {
                source.sendFeedback(new TranslatableText("commands.saturation.set.success.single", targets.iterator().next().getDisplayName(), amount), true);
            } else {
                source.sendFeedback(new TranslatableText("commands.saturation.set.success.multiple", targets.size(), amount), true);
            }
        }

        return amount;
    }

    private static int executeAddSaturation(ServerCommandSource source, Collection<ServerPlayerEntity> targets, int amount) throws CommandSyntaxException {
        for (ServerPlayerEntity player : targets) {
            player.getHungerManager().setSaturationLevel(player.getHungerManager().getSaturationLevel() + amount);
        }

        if (targets.size() == 0) {
            throw SATURATION_ADD_FAILED_EXCEPTION.create();
        } else {
            if (targets.size() == 1) {
                source.sendFeedback(new TranslatableText("commands.saturation.add.success.single", amount, targets.iterator().next().getDisplayName()), true);
            } else {
                source.sendFeedback(new TranslatableText("commands.saturation.add.success.multiple", amount, targets.size()), true);
            }
        }

        return amount;
    }

    private static int executeRemoveSaturation(ServerCommandSource source, Collection<ServerPlayerEntity> targets, int amount) throws CommandSyntaxException {
        for (ServerPlayerEntity player : targets) {
            player.getHungerManager().setSaturationLevel(player.getHungerManager().getSaturationLevel() - amount);
        }

        if (targets.size() == 0) {
            throw SATURATION_REMOVE_FAILED_EXCEPTION.create();
        } else {
            if (targets.size() == 1) {
                source.sendFeedback(new TranslatableText("commands.saturation.remove.success.single", amount, targets.iterator().next().getDisplayName()), true);
            } else {
                source.sendFeedback(new TranslatableText("commands.saturation.remove.success.multiple", amount, targets.size()), true);
            }
        }

        return amount;
    }
}
