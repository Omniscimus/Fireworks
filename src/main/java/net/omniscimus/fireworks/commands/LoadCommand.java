package net.omniscimus.fireworks.commands;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import net.omniscimus.fireworks.ShowHandler;
import net.omniscimus.fireworks.commands.exceptions.WrongArgumentsNumberException;

/**
 * This command loads a saved show from savedshows.yml into the server and
 * starts it.
 *
 * @author Omniscimus
 */
public class LoadCommand extends FireworksCommand {

    private final ShowHandler showHandler;

    public LoadCommand(ShowHandler showHandler) {
        this.showHandler = showHandler;
    }

    @Override
    public void run(CommandSender sender, String[] args)
            throws WrongArgumentsNumberException, UnsupportedEncodingException {

        if (args.length == 0) {
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
        } else if (args.length == 1) {
            ArrayList<Location> showWeShouldLoad
                    = showHandler.getSavedShows().get(args[0]);
            if (showWeShouldLoad == null) {
                sender.sendMessage(ChatColor.GOLD + "Couldn't find that show.");
            } else {
                for (Location loc : showWeShouldLoad) {
                    showHandler.startShow(loc);
                }
                sender.sendMessage(
                        ChatColor.GOLD + "Show loaded successfully.");
            }
        } else {
            throw new WrongArgumentsNumberException();
        }

    }

}
