package dev.arcticgaming.Main;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        CommandManager cmgr = new CommandManager();
        this.getCommand(cmgr.cmd1).setExecutor(cmgr);

        getLogger().info("Dice have been rolled!");
    }
    @Override
    public void onDisable() {
        getLogger().info("Dice have been un-rolled!");
    }
}
