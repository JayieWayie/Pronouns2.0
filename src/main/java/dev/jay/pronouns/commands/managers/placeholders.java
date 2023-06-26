package dev.jay.pronouns.commands.managers;

import dev.jay.pronouns.Pronouns;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;

public class placeholders extends PlaceholderExpansion {

    String pronouns = "";

    private final Pronouns plugin;

    public placeholders(Pronouns plugin){
        this.plugin = plugin;
    }


    @Override
    public String getIdentifier() {
        return "pronouns";
    }

    @Override
    public String getAuthor() {
        return "Jay";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params){

        if (params.equalsIgnoreCase("set")){

            try {
                PreparedStatement statement = plugin.con.GetDb().prepareStatement("SELECT pronounsSet FROM pronouns WHERE playerUUID=?");
                statement.setString(1, String.valueOf(player.getUniqueId()));
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {

                    pronouns = resultSet.getString("pronounsSet");

                } else {

                    pronouns = "N/A";

                }
            }catch (SQLException e){
                e.printStackTrace();
            }

            return pronouns;

        }


        return null;

    }


}
