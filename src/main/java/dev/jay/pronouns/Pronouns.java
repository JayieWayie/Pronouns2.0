package dev.jay.pronouns;

import dev.jay.pronouns.commands.main;
import dev.jay.pronouns.commands.managers.db;
import dev.jay.pronouns.commands.managers.dbq;
import dev.jay.pronouns.commands.managers.placeholders;
import dev.jay.pronouns.commands.sub.changePronouns;
import dev.jay.pronouns.commands.sub.checkPronouns;
import dev.jay.pronouns.commands.sub.helpMessage;
import dev.jay.pronouns.commands.sub.removePronouns;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import javax.xml.crypto.Data;
import java.sql.SQLException;

public final class Pronouns extends JavaPlugin {

    public db con;
    public dbq query;
    public String Datatype;
    public changePronouns changepn;
    public checkPronouns checkpns;
    public removePronouns removepns;
    public helpMessage hm;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        Datatype = getConfig().getString("Plugin.Database.Type");

        con = new db(this);
        query = new dbq(this);
        changepn = new changePronouns(this);
        checkpns = new checkPronouns(this);
        removepns = new removePronouns(this);
        hm = new helpMessage(this);

        getLogger().info("[Pronouns] Launched correctly.");
        getCommand("pronouns").setExecutor(new main(this));

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new placeholders(this).register();
        }

        try {
            con.InitDb();
            if (con.GetDb() != null){
                query.createTable();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        con.CloseDb();
    }
}
