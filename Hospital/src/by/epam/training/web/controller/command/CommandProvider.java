package by.epam.training.web.controller.command;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandProvider {

	private final Map<CommandName, Command> repository = new HashMap<>();

    private static Logger logger = LogManager.getLogger(CommandProvider.class);
    
	public CommandProvider() {
		repository.put(CommandName.SIGN_IN, new SignIn());
		repository.put(CommandName.SIGN_UP, new SignUp());
		repository.put(CommandName.BACK_TO_WELCOME_PAGE, new BackToWelcomePage());
		repository.put(CommandName.CONFIRM_REG, new ConfirmReg());
		repository.put(CommandName.LOG_OUT, new LogOut());
		repository.put(CommandName.WRONG_COMMAND, new WrongCommand());
	}

	public Command getCommand(String name) {
		CommandName commandName;
		Command command;
		try {
			commandName = CommandName.valueOf(name.toUpperCase());
			command = repository.get(commandName);
		} catch (IllegalArgumentException | NullPointerException e) {
			command = repository.get(CommandName.WRONG_COMMAND);
			logger.info(e.getMessage());
		}
		return command;
	}
}