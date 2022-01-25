import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {
    private static final int OFFSET = 1;
    
    public static Command parse(String command) throws DukeException {
        String[] commandTokens = command.split(" ", 2);
        
        try {
            switch (commandTokens[0]) {
            case "bye":
                return new ByeCommand();
            case "list":
                return new ListTaskCommand();
            case "mark":
                int indexOfTaskToMark = Integer.parseInt(commandTokens[1]) - OFFSET;
                return new MarkTaskCommand(indexOfTaskToMark);
            case "unmark":
                int indexOfTaskToUnmark = Integer.parseInt(commandTokens[1]) - OFFSET;
                return new UnmarkTaskCommand(indexOfTaskToUnmark);
            case "delete":
                int indexOfTaskToDelete = Integer.parseInt(commandTokens[1]) - OFFSET;
                return new DeleteTaskCommand(indexOfTaskToDelete);
            case "todo":
                return new AddToDoTaskCommand(commandTokens[1]);
            case "deadline":
                String[] deadlineTokens = commandTokens[1].split(" /by ");
                LocalDate date = LocalDate.parse(deadlineTokens[1]);
                return new AddDeadlineTaskCommand(deadlineTokens[0], date);
            case "event":
                String[] eventTokens = commandTokens[1].split(" /at ");
                return new AddEventTaskCommand(eventTokens[0], eventTokens[1]);
            default:
                throw new DukeException("I'm sorry, but I don't know what that means");
            }
        } catch (DateTimeParseException e) {
            throw new DukeException("The date provided is invalid(or in wrong format)");
        } catch (NumberFormatException e) {
            throw new DukeException("The index of task to " + commandTokens[0] + " is not a valid integer");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException("Seems like the command is incomplete");
        }
    }
}