package dev.jay.pronouns.commands.managers;

import dev.jay.pronouns.Pronouns;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class pronounsList {

    private final Pronouns plugin;
    public pronounsList(Pronouns plugin){
        this.plugin = plugin;
    }


    public void onRequestList(Player player){

        List<String> requestList = plugin.getConfig().getStringList("Pronouns-List");
        String listTitle = plugin.getConfig().getString("List-Format.Title");
        List<String> listformat = plugin.getConfig().getStringList("List-Format.ForEach");

        player.sendMessage(Color(Hex(listTitle)));
            for(String pronounsList : requestList){
                for (String formatList : listformat){
                    formatList = formatList.replace("%pronouns_list_item%", pronounsList);
                    player.sendMessage(Color(Hex(formatList)));
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
