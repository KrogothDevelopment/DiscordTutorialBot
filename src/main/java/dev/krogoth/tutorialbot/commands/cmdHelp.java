package dev.krogoth.tutorialbot.commands;

import dev.krogoth.tutorialbot.CommandManager;
import dev.krogoth.tutorialbot.interfaces.ICommands;
import io.github.classgraph.ClassGraph;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class cmdHelp implements ICommands {


    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Ruft die Hilfe auf.";
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        ClassGraph classGraph = new ClassGraph();
        List<ICommands> slashes = new ArrayList<>();
        StringBuilder cmds = new StringBuilder();

        classGraph.enableClassInfo()
                .scan()
                .getClassesImplementing(ICommands.class)
                .forEach(classInfo -> {
                    try {
                        ICommands slash = (ICommands) classInfo.loadClass().getDeclaredConstructor().newInstance();
                        slashes.add(slash);
                    } catch (RuntimeException | InvocationTargetException | InstantiationException |
                             IllegalAccessException | NoSuchMethodException e) {
                        throw new RuntimeException("Unable to add command with the reason: " + e);
                    }
                });


        cmds.append("***BEFEHLSÜBERSICHT***\n\n");
        for (ICommands command : slashes) {
            cmds.append("**Befehl '/" + command.getName() + "'**\n");
            cmds.append(command.getHelp() + "\n\n");
        }
            event.deferReply().setEphemeral(true).queue();
            event.getHook().sendMessage(cmds.toString()).queue();



    }

    @Override
    public boolean isGuildOnly() {
        return true;
    }

    @Override
    public String getHelp() {
        return "Dieser Befehl ruft diese Hilfe auf.";
    }
}
