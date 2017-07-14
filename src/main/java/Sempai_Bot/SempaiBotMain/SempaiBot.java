package Sempai_Bot.SempaiBotMain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


public class SempaiBot extends ListenerAdapter
{
	public static HashMap<String,Integer> glueCount = new HashMap<String,Integer>();
    
	public static void main( String[] args ) throws LoginException, IllegalArgumentException, InterruptedException, RateLimitedException
    {
        JDA sBot = new JDABuilder(AccountType.BOT).setToken("").buildBlocking();
        sBot.addEventListener(new SempaiBot());
        if(glueCount.size() == 0) {
        	constructGlueCount();
        }
        
    }             
    
	
    private static void constructGlueCount() {
		
		
	}


	private static void constructUserFile() throws FileNotFoundException {
		File f = new File("UserList.txt");
		PrintStream p = new PrintStream(f);
    	
		
	}

	// 
    public void onMessageReceived(MessageReceivedEvent e) {
    	
    	Message objMsg = e.getMessage();
    	MessageChannel objChannel = e.getChannel();
    	User objUser = e.getAuthor();
    	
    	if(objMsg.getContent().toLowerCase().contains("sempai")) {
    		String regex = objMsg.getContent().toLowerCase();
    		Pattern p = Pattern.compile("sempai");
    		Matcher m = p.matcher(regex);
    		int count = 0;
    		while(m.find()) {
    			count++;
    		}
    		String h = "baby ";
    		String h1 = "baby ";
    		if(count == 1)
    			h += "horse";
    		else
    			h += "horses";
    		if(glueCount.get(objUser.getName()) == 1)
    			h1 += "horse";
    		else
    			h1 += "horses";
    		objChannel.sendMessage("Every time \"Senpai\" is spelled \"sempai\" a baby horse gets melted into glue. \n \n"
    				+ objUser.getAsMention() + " has just caused " + count + " " + h + " to be melted. In total " + objUser.getName() + 
    				" has melted " + glueCount.get(objUser.getName()) + " " + h1 + "\n \n" + "To see the total number of horses melted in "
    						+ "this chat type \"!GlueCount\".");
    	}
    	
    }
}                 
                  