package dev.jay.pronouns.commands.sub;

import dev.jay.pronouns.Pronouns;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class helpMessage {

    private final Pronouns plugin;

    public helpMessage(Pronouns plugin){
        this.plugin = plugin;
    }

    public void onHelpRequest(Player player){

        List<String> helpMessage = plugin.getConfig().getStringList("HelpMessage");

        for (String hm : helpMessage){

            player.sendMessage(Color(Hex(hm)));

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
