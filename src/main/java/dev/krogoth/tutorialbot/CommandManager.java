package dev.krogoth.tutorialbot;

import dev.krogoth.tutorialbot.interfaces.ICommands;
import io.github.classgraph.ClassGraph;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class CommandManager extends ListenerAdapter {
    private static final Map<String, ICommands> cmdMap = new HashMap<>();
    private final CommandListUpdateAction globalCommandsData;
    private Bot main;

    public CommandManager(Bot main, JDA jda) {
        this.main = main;

        this.globalCommandsData = jda.updateCommands();

        ClassGraph classGraph = new ClassGraph();
        List<ICommands> cmds = new ArrayList<>();

        classGraph.enableClassInfo()
                .scan()
                .getClassesImplementing(ICommands.class)
                .forEach(classInfo -> {
                    try {
                        ICommands slash = (ICommands) classInfo.loadClass().getDeclaredConstructor().newInstance();
                        cmds.add(slash);
                    } catch (RuntimeException | InvocationTargetException | InstantiationException |
                             IllegalAccessException | NoSuchMethodException e) {
                        throw new RuntimeException("Unable to add command with the reason: " + e);
                    }
                });

        registerSlashCommands(cmds);

    }

    private void registerSlashCommand(ICommands slash) {
        cmdMap.put(slash.getName(), slash);
        globalCommandsData.addCommands(slash.getCommandData());
    }

    public void registerSlashCommands(List<ICommands> slashList) {
        slashList.forEach(this::registerSlashCommand);
        queueCommands();
    }

    private void queueCommands() {
        globalCommandsData.queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        ICommands slash = cmdMap.get(event.getName());
        slash.execute(event);
    }

    public static Map<String, ICommands> getMap() {
        return cmdMap;
    }

}
