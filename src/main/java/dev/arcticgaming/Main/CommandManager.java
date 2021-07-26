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

                //calls external classes
                ModifierCheck checkModifier = new ModifierCheck();
                RollSum rollTotal = new RollSum();

                //controls for roll modifiers
                boolean modifierPresent = false;

                //check if modifier is present
                if (args.length < 3) {
                    checkModifier.setModifier(0);
                    modifierPresent = false;
                    //debug message: player.sendMessage("modifier not found by command, setting to 0");

                    //if modifier is NOT present, set modifier to 0
                }
                if (args.length == 3) {
                    modifierPresent = true;
                    boolean checkMod = new IntegerValidation().isInteger(args[2]);
                    if (checkMod) {
                        //if modifier is a valid integer, parse it as the roll modifier value
                        checkModifier.setModifier(Integer.parseInt(args[2]));
                        // debug message: player.sendMessage("modifier found! its " + checkModifier.getModifier());
                    } else {
                        // if modifier is not a valid integer, set it to 0
                        player.sendMessage(ChatColor.GOLD + "Dice » Warning, Invalid modifier - setting to 0");
                        checkModifier.setModifier(0);
                    }
                }
                //check if arguments are missing, don't make modifier a requirement as its handled seperately
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Dice » Error - Missing Arguments - /roll <# dice> d<# faces> <optional:modifier>");
                } else {
                    // if arguments are present, validate integers
                    boolean numDice = new IntegerValidation().isInteger(args[0]);
                    boolean numFace = new IntegerValidation().isInteger(args[1].substring(1));
                    //if modifier was found, validate integer
                    if (numDice && numFace) {

                        //parse integers
                        int intDice = Integer.parseInt(args[0]);
                        int intFace = Integer.parseInt(args[1].substring(1));

                        //start array to track individual dice rolls
                        ArrayList<Integer> rolls = new ArrayList<Integer>();

                        //loop to get value for each dice roll
                        while (intDice > 0) {

                            //creates random seed
                            Random generator = new Random();
                            //random num generation uses doubles, faces set max range, +1 to cover 0 base
                            //note: weird conversion with doubles and ints, acts funny in any other configuration
                            double valDice = generator.nextInt(intFace) + 1;
                            //cast generated number as an integer
                            int intValDice = (int) valDice;
                            //add random value to the array list
                            rolls.add(intValDice);

                            //passes values to RollSum class to be calculated
                            rollTotal.setRollSum(rollTotal.getRollSum(), intValDice);

                            //subtracts one from amount of dice to be calculated
                            intDice = intDice - 1;

                                /*Debug messages to check values for each roll
                                player.sendMessage(ChatColor.YELLOW + "Dice Rolls: " + rolls);
                                player.sendMessage(ChatColor.GOLD + "Dice Sum: " + rollTotal.getRollSum());*/
                        }
                        //returns the list of individual rolls to the player
                        player.sendMessage(ChatColor.AQUA + "Dice » Values: " + ChatColor.WHITE + rolls);
                        //returns the sum of dice WITHOUT modifiers
                        if (args.length == 3) {
                            player.sendMessage(ChatColor.AQUA + "Dice » Modifier: " + ChatColor.WHITE + checkModifier.getModifier() +ChatColor.AQUA + "\nDice » Modified Sum: " + ChatColor.WHITE + (rollTotal.getRollSum() + checkModifier.getModifier()));
                        } else {
                            //returns the sum of dice WITHOUT modifiers
                            player.sendMessage(ChatColor.AQUA + "Dice » Sum: " + ChatColor.WHITE + rollTotal.getRollSum());
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Dice » Error! Invalid format, use /roll <# dice> d<# faces> <optional:modifier>");
                    }
                }
            }
        }
        return true;
    }
}