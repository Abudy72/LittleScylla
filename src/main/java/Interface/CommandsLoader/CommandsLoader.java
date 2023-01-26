package Interface.CommandsLoader;

import Interface.CommandsModule.AcceptCommand;
import Interface.CommandsModule.CustomCommandListener;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigObject;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CommandsLoader {
    private final static LinkedList<CommandData> commandsList = new LinkedList<>();

    public static LinkedList<CommandData> loadCommands(){
        Config config = ConfigFactory.load();
        for(Config configItem: config.getConfigList("slashCommands")){
            processCommands(configItem);
        }
        return commandsList;
    }
    private static void processCommands(Config configItem){
        List<OptionData> optionDataList = new LinkedList<>();
        String commandId = configItem.getString("commandName");
        String description = configItem.getString("description");
        //addCommand(commandId); //This is load command to CommandManager
        if(!configItem.getObjectList("options").isEmpty()){
            for(ConfigObject option: configItem.getObjectList("options")){
                OptionType optionType;
                if(option.toConfig().getString("optionType").equals("user")){
                    optionType = OptionType.USER;
                }else if(option.toConfig().getString("optionType").equals("channel")){
                    optionType = OptionType.CHANNEL;
                }else if(option.toConfig().getString("optionType").equals("role")) {
                    optionType = OptionType.ROLE;
                }else if(option.toConfig().getString("optionType").equals("int")){
                  optionType = OptionType.INTEGER;
                } else{
                    optionType = OptionType.STRING;
                }
                optionDataList.add(
                        new OptionData(optionType, option.toConfig().getString("name"),option.toConfig().getString("description"), option.toConfig().getBoolean("isRequired"))
                );
            }
        }
        CommandData singleCommand = new CommandDataImpl(commandId,description).addOptions(optionDataList);
        commandsList.add(singleCommand);
    }
}
