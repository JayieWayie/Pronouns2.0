package dev.jay.pronouns.commands.sub;

import dev.jay.pronouns.Pronouns;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class removePronouns {

    private final Pronouns plugin;
    public removePronouns(Pronouns plugin){
        this.plugin = plugin;
    }

    public void onRemoveRequest(Player player) throws SQLException {

        String prefix = plugin.getConfig().getString("Plugin.Prefix");
        String clear = plugin.getConfig().getString("Messages.Pronouns-Cleared");
        String error1 = plugin.getConfig().getString("Errors.NoPermission");

        if (player.hasPermission("pronouns.remove.self")){

            PreparedStatement st1 = plugin.con.GetDb().prepareStatement("UPDATE pronouns SET pronounsSet=? WHERE playerUUID=?");
            st1.setString(1, plugin.getConfig().getString("Plugin.Default"));
            st1.setString(2, String.valueOf(player.getUniqueId()));

            player.sendMessage(Color(Hex(prefix + " " + clear)));

        }else{

            player.sendMessage(Color(Hex(prefix + " " + error1)));

        }

    }


    private String Color(String s){
        s = org.bukkit.ChatColor.translateAlternateColorCodes('&', s);
        return s;
    }

    private static final Pattern HEX_PATTERN = Pattern.compile("&(#\\w{6})");
    public static String Hex(String message) {
        Matcher matcher = HEX_PATTERN.matcher(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', message));
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of(matcher.group(1)).toString());
        }

        return matcher.appendTail(buffer).toString();
    }

}
