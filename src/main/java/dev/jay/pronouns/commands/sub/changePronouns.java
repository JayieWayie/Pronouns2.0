package dev.jay.pronouns.commands.sub;

import dev.jay.pronouns.Pronouns;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class changePronouns {

    private final Pronouns plugin;
    public changePronouns(Pronouns plugin){
        this.plugin = plugin;
    }


    public void onChangeRequest(Player player, String value) throws SQLException {
        List<String> pronounsList = plugin.getConfig().getStringList("Pronouns-List");
        String prefix = plugin.getConfig().getString("Plugin.Prefix");
        String message = plugin.getConfig().getString("Messages.Pronouns-Changed");
        String error1 = plugin.getConfig().getString("Errors.NoPermission");
        String error2 = plugin.getConfig().getString("Errors.NoPronouns");
        boolean valid = false;

        if (player.hasPermission("pronouns.set.self")){

            for (String pl : pronounsList){
                if (pl.equalsIgnoreCase(value)){
                    valid = true;
                }
            }

            if (valid){

                PreparedStatement st1 = plugin.con.GetDb().prepareStatement("UPDATE pronouns SET pronounsSet=? WHERE playerUUID=?");
                st1.setString(1, value);
                st1.setString(2, String.valueOf(player.getUniqueId()));
                st1.executeUpdate();
                message = message.replace("%pronouns%", value);
                player.sendMessage(Color(Hex(prefix + " " + message)));

            } else {

                player.sendMessage(Color(Hex(prefix + " " + error2)));

            }


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
