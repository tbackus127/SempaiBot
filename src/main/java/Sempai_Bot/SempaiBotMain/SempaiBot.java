package Sempai_Bot.SempaiBotMain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
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


public class SempaiBot extends ListenerAdapter {
  
  private static final String SAVEDATA_FILENAME = "UserList.ser";
  
  private static HashMap<String, Integer> glueCount = new HashMap<String, Integer>();
  private static File fileSaveData = new File(SAVEDATA_FILENAME);

  public static void main(String[] args) throws LoginException, IllegalArgumentException, InterruptedException,
      RateLimitedException, ClassNotFoundException, IOException {
    
    JDA sBot = new JDABuilder(AccountType.BOT).setToken("MzM1MTE0MjY0NjE4NDY3MzI4.DEn_Rw.hfyjF9_lMiQIRvJgxPndT8XyJdE").buildBlocking();
    sBot.addEventListener(new SempaiBot());
    if (fileSaveData.exists()) {
      constructGlueCount();
    }
  }

  private static final void constructGlueCount() {
    
    try {
      FileInputStream fileIn = new FileInputStream(f);
      ObjectInputStream in = new ObjectInputStream(fileIn);
    
      glueCount = (HashMap<String, Integer>) in.readObject();
          
      in.close();
      fileIn.close();
    } catch(IOException i){
      i.printStackTrace();
      return;
    } catch(ClassNotFoundException c) {
      System.out.println("Class not Found.");
      c.printStackTrace();
    }
        
    System.out.println("glueCount Data Reloaded.");
        
    // Print HashMap Contents, to be removed in final.
    Set<Map.Entry<String, Integer>> s = glueCount.entrySet();
    Iterator<Map.Entry<String, Integer>> i = s.iterator();
    while(i.hasNext()) {
      Map.Entry<String, Integer> m = (Map.Entry<String, Integer>) i.next();
      System.out.println("Key: " + m.getKey());
      System.out.println("Value: " + m.getValue());
    }
  }

  private static final void saveGlueCount() {
    try {
      FileOutputStream fileOut = new FileOutputStream(fileSaveData);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(glueCount);
      out.close();
      fileOut.close();
      System.out.printf("Serialized data is saved in \"" + SAVEDATA_FILENAME + "\".");
    } catch(IOException i) {
      i.printStackTrace();
    }
  }

  @Override
  public void onMessageReceived(MessageReceivedEvent e) {

    Message objMsg = e.getMessage();
    MessageChannel objChannel = e.getChannel();
    User objUser = e.getAuthor();
    
    if(objMsg.getContent().toLowerCase().contains("sempai") && !objUser.getName().equals("Senpai-Bot")) {

      String regex = objMsg.getContent().toLowerCase();
      Pattern p = Pattern.compile("sempai");
      Matcher m = p.matcher(regex);
      int count = 0;

      while (m.find()) {
        count++;
      }

      if(glueCount.containsKey(objUser.getName()))
        glueCount.put(objUser.getName(), glueCount.get(objUser.getName()) + count);
      else
        glueCount.put(objUser.getName(), count);
      
      if(glueCount.containsKey("Global"))
        glueCount.put("Global", glueCount.get("Global") + count);
      else
        glueCount.put("Global", count);
      
      
      String h = "baby ";
      String h1 = "baby ";

      if (count == 1)
        h += "horse";
      else
        h += "horses";

      if (glueCount.get(objUser.getName()) == 1)
        h1 += "horse";
      else
        h1 += "horses";
      

      objChannel.sendMessage("Every time \"Senpai\" is spelled \"sempai\", a baby horse gets melted into glue. \n \n"
              + objUser.getAsMention() + " has just caused " + count + " " + h
              + " to be melted. In total, " + objUser.getName() + " has melted "
              + glueCount.get(objUser.getName()) + " " + h1 + "\n \n"
              + "To see the total number of horses melted in " + "this chat, type \"!GlueCount\".").queue();
      
      saveGlueCount();
    }
    
    if(objMsg.getContent().equals("!GlueCount") && !objUser.getName().equals("Senpai-Bot")) {
      
      String h = "";
      if (glueCount.get("Global") == 1)
        h += "horse";
      else
        h += "horses";
      
      objChannel.sendMessage("So far osu! University has contributed to the melting of " + glueCount.get("Global")
              + " " + h + ".").queue();
    }

  }
}
