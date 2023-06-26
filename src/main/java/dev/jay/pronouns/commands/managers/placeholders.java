package dev.jay.pronouns.commands.managers;

import dev.jay.pronouns.Pronouns;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;

public class placeholders extends PlaceholderExpansion {

    private final Pronouns plugin;

    public placeholders(Pronouns plugin){
        this.plugin = plugin;
    }

    @Override
    public String getAuthor() {
        return "Jay";
    }

    @Override
    public String getIdentifier() {
        return "pronouns";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (params.equalsIgnoreCase("current")) {
            String pronouns = null;
            try {
                PreparedStatement ps1 = plugin.con.GetDb().prepareStatement("SELECT * FROM pronouns WHERE playerUUID=?");
                ps1.setString(1, player.getUniqueId().toString());
                ResultSet rs1 = ps1.executeQuery();

                if (rs1.next()) {

                    pronouns = rs1.getString("pronounsSet");

                }

            } catch (SQLException e) {
                    throw new RuntimeException(e);
            }

            return pronouns;

        }


        return null;
    }

}
