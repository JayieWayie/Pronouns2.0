package dev.jay.pronouns.commands;

import dev.jay.pronouns.Pronouns;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class main implements CommandExecutor {

    private final Pronouns plugin;

    public main(Pronouns plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("pronouns")) {
            Player player = (Player) sender;
            try {

                if (args[0].equalsIgnoreCase("check")) {

                    try {
                        plugin.checkpns.onCheckRequest(player, Bukkit.getPlayer(args[1]));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                } else if (args[0].equalsIgnoreCase("set")) {

                    try {
                        plugin.changepn.onChangeRequest(player, args[1]);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                } else if (args[0].equalsIgnoreCase("clear")) {

                    try {
                        plugin.removepns.onRemoveRequest(player);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                } else {

                    plugin.hm.onHelpRequest(player);

                }

            } catch (ArrayIndexOutOfBoundsException | NullPointerException | IllegalArgumentException ex) {

                plugin.hm.onHelpRequest(player);

            }
        }



        return true;
    }
}
