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
            try {
                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    if (args[0].equalsIgnoreCase("check")) {

                        if (args.length < 2) {

                            try {
                                plugin.checkpns.onCheckRequest(player);
                            } catch (SQLException e) {
                                plugin.hm.onHelpRequest(player);
                            }

                        } else if (args.length == 2) {
                            Player target = Bukkit.getPlayer(args[1]);

                            try {
                                plugin.co.onCheckOther(player, target);
                            } catch (SQLException e) {
                                plugin.hm.onHelpRequest(player);
                            }

                        } else {

                            plugin.hm.onHelpRequest(player);

                        }

                    } else if (args[0].equalsIgnoreCase("set")) {
                        String value = args[1];
                        try {
                            plugin.changepn.onChangeRequest(player, value);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                    } else if (args[0].equalsIgnoreCase("clear")) {

                        try {
                            plugin.removepns.onRemoveRequest(player);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                    } else if (args[0].equalsIgnoreCase("list")) {

                        plugin.list.onRequestList(player);

                    } else if (args[0].equalsIgnoreCase("help")) {

                        plugin.hm.onHelpRequest(player);


                    } else if (args[0].equalsIgnoreCase("reload")) {

                        if (player.hasPermission("pronouns.reload")) {

                            plugin.rl.reload(plugin, player);

                        }


                    } else {

                        plugin.hm.onHelpRequest(player);

                    }

                }
            }catch (ArrayIndexOutOfBoundsException e){
                plugin.hm.onHelpRequest(((Player) sender).getPlayer());
            }
        }



        return true;
    }
}
