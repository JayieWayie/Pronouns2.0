package dev.jay.pronouns.commands.sub;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class reload {

    public void reload(Plugin plugin, Player player){

        plugin.reloadConfig();
        player.sendMessage(Color(Hex("&#9266ff&l&oP&#8c6dff&l&or&#8774ff&l&oo&#a27eff&l&on&#cf89ff&l&oo&#efa1ff&l&ou&#f7d0ff&l&on&#ffffff&l&os &7> &aPlugin reloaded.")));

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
