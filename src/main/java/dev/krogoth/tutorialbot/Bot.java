package dev.krogoth.tutorialbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Bot {

    private JDA jda;

    public static void main(String[] args) {
        new Bot();
    }

    public Bot() {

        this.jda = JDABuilder.createDefault("YOUR_DISCORD_TOKEN_HERE")
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.playing("TutorialBot"))
                .enableIntents(
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_PRESENCES,
                        GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.GUILD_VOICE_STATES)
                .build();

        try {
            this.jda.awaitReady().addEventListener(new CommandManager(this,this.jda));
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }


}