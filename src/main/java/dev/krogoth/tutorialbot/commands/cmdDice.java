package dev.krogoth.tutorialbot.commands;

import dev.krogoth.tutorialbot.interfaces.ICommands;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Random;

public class cmdDice implements ICommands {
    @Override
    public String getName() {
        return "dice";
    }

    @Override
    public String getDescription() {
        return "Würfelt eine zufällige Zahl von 1 bis 6.";
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String[] dice = {"1", "2", "3", "4", "5", "6"};
        Random rnd = new Random();

        int sel = rnd.nextInt(dice.length);
        event.deferReply().setEphemeral(false).queue();
        event.getHook().sendMessage("**"+ event.getMember().getAsMention()+"** würfelt eine **"+dice[sel]+"**").queue();

    }

    @Override
    public boolean isGuildOnly() {
        return true;
    }

    @Override
    public String getHelp() {
        return "Ein Würfelsystem. Es wird eine zufällige Zahl von 1 bis 6 ausgegeben.";
    }
}
