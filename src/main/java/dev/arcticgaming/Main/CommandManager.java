package dev.arcticgaming.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

public class CommandManager implements CommandExecutor {
    Main plugin = Main.getPlugin(Main.class);

    public String cmd1 = "roll";

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        /* Dice Rolling
           command structure: /roll <number of dice> d<number of faces>
           command example:   /roll 3 d6
         */
        if (cmd.getName().equalsIgnoreCase((cmd1))) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 0) {
                    player.sendMessage(ChatColor.RED + "Dice » Invalid command! " + ChatColor.WHITE + "Use /roll <number of dice> d<number of faces>");
                } else{
                    //establish unchecked vars
                    boolean numDice = new IntegerValidation().isInteger(args[0]);
                    boolean numFace = new IntegerValidation().isInteger(args[1].substring(1));
                    //if checked vars pass
                    if (numDice && numFace) {
                        int intDice = Integer.parseInt(args[0]);
                        int intFace = Integer.parseInt(args[1].substring(1));
                        RollSum rollTotal = new RollSum();

                        //returns player inputs to verify
                        //player.sendMessage(ChatColor.WHITE + "Values are: number of dice: " + numDice + " faces : " + numFace);

                        //method to create rolls list?
                        ArrayList<Integer> rolls = new ArrayList<Integer>();

                        //creates a random value for each roll
                        while (intDice > 0) {
                            Random generator = new Random();
                            double valDice = generator.nextInt(intFace) + 1;
                            int intValDice = (int) valDice;
                            rolls.add(intValDice);

                            rollTotal.setRollSum(rollTotal.getRollSum(), intValDice);
                            intDice = intDice - 1;
                            /*returns values after EACH roll - debugging messages
                             * player.sendMessage(ChatColor.YELLOW + "Dice Rolls: " + rolls);
                             * player.sendMessage(ChatColor.GOLD + "Dice Sum: " + rollTotal.getRollSum()); */
                        }
                        player.sendMessage(ChatColor.YELLOW + "Dice Rolls: " + rolls);
                        player.sendMessage(ChatColor.GOLD + "Dice Sum: " + rollTotal.getRollSum());
                    } else {
                        player.sendMessage(ChatColor.RED + "Dice » Invalid command! " + ChatColor.WHITE + "Use /roll <number of dice> d<number of faces>");
                    }
                }
            }
        }
        return true;
    }
}


