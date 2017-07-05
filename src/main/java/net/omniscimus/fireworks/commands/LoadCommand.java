package net.omniscimus.fireworks.commands;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import net.omniscimus.fireworks.ShowHandler;

/**
 * This command loads a saved show from savedshows.yml into the server and
 * starts it.
 *
 * @author Omniscimus
 */
public class LoadCommand extends FireworksCommand {

    private final ShowHandler showHandler;

    /**
     * Creates a new LoadCommand.
     * 
     * @param showHandler the ShowHandler which should be used to start shows
     */
    public LoadCommand(ShowHandler showHandler) {
        this.showHandler = showHandler;
    }
    
    @Override
    public Options getOptions() {
        return super.getOptions()
                .addOption("s", "show", true, "the saved show to load");
    }

    @Override
    public void run(CommandSender sender, CommandLine commandLine) {
        if (commandLine.hasOption("s")) {
            loadShow(sender, commandLine.getOptionValue("s"));
        } else {
            showSavedShows(sender);
        }
    }
    
    /**
     * Loads a show with the specified name.
     * 
     * @param sender the command sender to send feedback to
     * @param show the show to load
     */
    private void loadShow(CommandSender sender, String show) {
        ArrayList<Location> showWeShouldLoad
                = showHandler.getSavedShows().get(show);
        if (showWeShouldLoad == null) {
            sender.sendMessage(ChatColor.GOLD + "Couldn't find that show.");
        } else {
            showWeShouldLoad.forEach((loc) -> {
                try {
                    showHandler.startShow(loc);
                } catch (UnsupportedEncodingException ex) {
                    sender.sendMessage(ex.getMessage());
                }
            });
            sender.sendMessage(
                    ChatColor.GOLD + "Show loaded successfully.");
        }
    }
    
    /**
     * Sends the command sender a list of saved shows.
     * 
     * @param sender the command sender
     */
    private void showSavedShows(CommandSender sender) {
        StringBuilder responseBuilder = new StringBuilder(ChatColor.GOLD + "Saved shows: \n");
        Map<String, ArrayList<Location>> savedShows = showHandler.getSavedShows();
        if (savedShows.isEmpty()) {
            responseBuilder.append(ChatColor.RED).append("None.");
        } else {
            String[] savedShowsNames = savedShows.keySet().toArray(new String[0]);
            for (int i = 0; i < savedShowsNames.length; i++) {
                responseBuilder.append(ChatColor.RED).append(savedShowsNames[i]);
                if (i != savedShowsNames.length - 1) {
                    responseBuilder.append(ChatColor.GOLD).append(", ");
                }
            }
        }
        sender.sendMessage(responseBuilder.toString());
    }

}
