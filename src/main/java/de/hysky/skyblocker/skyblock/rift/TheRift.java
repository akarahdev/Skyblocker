package de.hysky.skyblocker.skyblock.rift;

import de.hysky.skyblocker.config.SkyblockerConfigManager;
import de.hysky.skyblocker.utils.scheduler.Scheduler;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

public class TheRift {
    /**
     * @see de.hysky.skyblocker.utils.Utils#isInTheRift() Utils#isInTheRift().
     */
    public static final String LOCATION = "rift";

    public static void init() {
        WorldRenderEvents.AFTER_TRANSLUCENT.register(MirrorverseWaypoints::render);
        WorldRenderEvents.AFTER_TRANSLUCENT.register(EffigyWaypoints::render);
        WorldRenderEvents.AFTER_TRANSLUCENT.register(EnigmaSouls::render);
        ClientLifecycleEvents.CLIENT_STARTED.register(MirrorverseWaypoints::load);
        ClientLifecycleEvents.CLIENT_STARTED.register(EnigmaSouls::load);
        ClientLifecycleEvents.CLIENT_STOPPING.register(EnigmaSouls::save);
        ClientReceiveMessageEvents.GAME.register(EnigmaSouls::onMessage);
        ClientCommandRegistrationCallback.EVENT.register(EnigmaSouls::registerCommands);
        Scheduler.INSTANCE.scheduleCyclic(EffigyWaypoints::updateEffigies, SkyblockerConfigManager.get().slayer.vampireSlayer.effigyUpdateFrequency);
        Scheduler.INSTANCE.scheduleCyclic(TwinClawsIndicator::updateIce, SkyblockerConfigManager.get().slayer.vampireSlayer.holyIceUpdateFrequency);
        Scheduler.INSTANCE.scheduleCyclic(ManiaIndicator::updateMania, SkyblockerConfigManager.get().slayer.vampireSlayer.maniaUpdateFrequency);
        Scheduler.INSTANCE.scheduleCyclic(StakeIndicator::updateStake, SkyblockerConfigManager.get().slayer.vampireSlayer.steakStakeUpdateFrequency);
    }
}
