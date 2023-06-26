package dev.jay.pronouns.commands.sub;

import dev.jay.pronouns.Pronouns;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class checkPronouns {

    private final Pronouns plugin;

    public checkPronouns(Pronouns plugin){
        this.plugin = plugin;
    }

    public void onCheckRequest(Player player, Player target) throws SQLException {

        String prefix = plugin.getConfig().getString("Plugin.Prefix");
        String checkMessage = plugin.getConfig().getString("Pronouns-Check-Other");
        String checkMessage2 = plugin.getConfig().getString("Pronouns-Check-Self");
        String error1 = plugin.getConfig().getString("Errors.NoPermission");

        if (target != null) {

            if (player.hasPermission("pronouns.check.other")) {


                PreparedStatement ps1 = plugin.con.GetDb().prepareStatement("SELECT * FROM pronouns WHERE playerUUID=?");
                ps1.setString(1, target.getUniqueId().toString());
                ResultSet rs1 = ps1.executeQuery();

                if (rs1.next()) {

                    checkMessage = checkMessage.replace("%target%", target.getName()).replace("%pronouns%", rs1.getString("pronounsSet"));

                    player.sendMessage(Color(Hex(prefix + " " + checkMessage)));

                }


            } else {

                player.sendMessage(Color(Hex(prefix + " " + error1)));
            }
        }else{

            if (player.hasPermission("pronouns.check.self")) {


                PreparedStatement ps1 = plugin.con.GetDb().prepareStatement("SELECT * FROM pronouns WHERE playerUUID=?");
                ps1.setString(1, player.getUniqueId().toString());
                ResultSet rs1 = ps1.executeQuery();

                if (rs1.next()) {

                    checkMessage2 = checkMessage2.replace("%pronouns%", rs1.getString("pronounsSet"));

                    player.sendMessage(Color(Hex(prefix + " " + checkMessage2)));

                }

            } else {

                player.sendMessage(Color(Hex(prefix + " " + error1)));
            }

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
