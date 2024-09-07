package dev.krogoth.tutorialbot.interfaces;

import dev.krogoth.tutorialbot.Bot;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;

import java.util.ArrayList;
import java.util.List;

public interface ICommands {

    String getName();

    String getDescription();
    void execute(SlashCommandInteractionEvent event);
    boolean isGuildOnly();
    String getHelp();
    default List<OptionData> getOptions() {
        return new ArrayList<>();
    }

    default CommandData getCommandData() {
        return new CommandDataImpl(getName(), getDescription())
                .setGuildOnly(isGuildOnly())
                .addOptions(getOptions());
    }
}
