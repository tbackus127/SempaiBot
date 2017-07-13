package Sempai_Bot.SempaiBotMain;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/*
 * Hello world!
 *
 */
public class SempaiBot extends ListenerAdapter
{
	public int glueCount = 0;
    public static void main( String[] args ) throws LoginException, IllegalArgumentException, InterruptedException, RateLimitedException
    {
        JDA sBot = new JDABuilder(AccountType.BOT).setToken("").buildBlocking();
        sBot.addEventListener(new SempaiBot());
    }             
    
    // 
    public void onMessageReceived(MessageReceivedEvent e) {
    	
    	Message objMsg = e.getMessage();
    	MessageChannel objChannel = e.getChannel();
    	User objUser = e.getAuthor();
    	
    	if(objMsg.getContent().toLowerCase().contains("sempai")) {
    		objChannel.sendMessage("Every time \"Senpai\" is spelled \"sempai\" a baby horse gets melted into glue. \n \n"
    				+ "So far " + objUser.getAsMention() + " has caused " + dummyVar + "baby horses to be melted. \n \n" 
    				+ "To see the total number of horses melted in this chat type \"!GlueCount\".");
    	}
    	
    }
}                 
                  