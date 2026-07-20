package mv.mossuh.mocore.UTILITIES.ARGS.CommandArgs;

import mv.mossuh.mocore.UTILITIES.ARGS.ArgType;
import mv.mossuh.mocore.UTILITIES.ARGS.Args;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandArgs implements Args {
    private List<String> args = new ArrayList<>();
    public CommandArgs(String[] strings, int ignoreArgs) {
        int ignoredStrings = 0;
        if (strings != null) {
            for (String string : strings) {
                if (ignoredStrings < ignoreArgs) {
                    ignoredStrings += 1;
                    continue;
                }
                args.add(string);
            }
        }
    }

    public CommandArgs(List<String> args) {
        if (args != null) {
            this.args.addAll(args);
        }
    }

    public List<String> getArgs() { return args; }

    public String getArgsAsString() {
        if (args != null && !args.isEmpty()) {
            return String.join(", ", args);
        } else {
            return "";
        }
    }

    public String getArg(int argNumber) {
        if (argNumber < 0) { argNumber = 0; }

        String arg = args.get(argNumber);
        if (arg != null) { return arg; }
        return "";
    }

    public ArgType getArgType() { return ArgType.COMMAND; }
    public boolean hasArgs() { return args != null && !args.isEmpty(); }

    public static CommandArgs getCommandArgs(String argsAsString) {
        if (argsAsString != null) {
            String[] argsArray = argsAsString.split(", ");
            List<String> argsList = new ArrayList<>();
            Collections.addAll(argsList, argsArray);
            return new CommandArgs(argsList);
        }
        return new CommandArgs(null);
    }
}
