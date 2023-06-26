package dev.jay.pronouns;

import dev.jay.pronouns.commands.main;
import dev.jay.pronouns.commands.managers.db;
import dev.jay.pronouns.commands.managers.dbq;
import dev.jay.pronouns.commands.managers.placeholders;
import dev.jay.pronouns.commands.managers.pronounsList;
import dev.jay.pronouns.commands.sub.*;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Pronouns extends JavaPlugin implements Listener {

    public db con;
    public dbq query;
    public String Datatype;
    public changePronouns changepn;
    public checkPronouns checkpns;
    public removePronouns removepns;
    public helpMessage hm;
    public pronounsList list;
    public checkother co;
    public reload rl;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        Datatype = getConfig().getString("Plugin.Database.Type");

        con = new db(this);
        query = new dbq(this);
        list = new pronounsList(this);
        changepn = new changePronouns(this);
        checkpns = new checkPronouns(this);
        removepns = new removePronouns(this);
        hm = new helpMessage(this);
        co = new checkother(this);
        rl = new reload();

        getLogger().info("[Pronouns] Launched correctly.");
        getCommand("pronouns").setExecutor(new main(this));

        new placeholders(this).register();

        try {
            con.InitDb();
            if (con.GetDb() != null){
                query.createTable();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        getServer().getPluginManager().registerEvents(this, this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        con.CloseDb();
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) throws SQLException {

        query.createPlayer(e.getPlayer());


    }
}
