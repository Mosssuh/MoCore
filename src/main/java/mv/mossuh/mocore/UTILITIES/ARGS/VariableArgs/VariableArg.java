package mv.mossuh.mocore.UTILITIES.ARGS.VariableArgs;

import mv.mossuh.mocore.UTILITIES.ARGS.ArgType;
import mv.mossuh.mocore.UTILITIES.ARGS.Args;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableArg implements Args {

    private String variable = "";
    private String value = "";
    public VariableArg(String variable, String value) {
        if (variable != null) { this.variable = variable; }
        if (value != null) { this.value = value; }
    }

    public String getVariable() { return variable; }
    public String getValue() { return value; }
    public void setVariable(String variable) { this.variable = variable; }
    public void setValue(String value) { this.value = value; }

    public boolean isVariable() {
        return !variable.isEmpty();
    }
    public boolean isValue() {
        return !value.isEmpty();
    }

    public static String getValue(List<VariableArg> variables, String variable) {
        for (VariableArg v : variables) {
            if (v.getVariable().equals(variable)) {
                return v.getValue();
            }
        }
        return "";
    }

    public static List<VariableArg> fromConfig(List<String> variableList) {
        List<VariableArg> variables = new ArrayList<>();
        if (variableList != null && !variableList.isEmpty()) {
            for (String variableString : variableList) {
                String[] variableStringSplit = variableString.split(" -> ", 2);
                if (variableStringSplit.length == 2) {
                    variables.add(new VariableArg(variableStringSplit[0], variableStringSplit[1]));
                } else {
                    variables.add(new VariableArg(variableStringSplit[0], null));
                }
            }
        }
        return variables;
    }

    public static List<VariableArg> fromString(String variablesAsString) {
        if (variablesAsString == null || variablesAsString.isEmpty()) {
            return Collections.emptyList();
        }

        List<VariableArg> variables = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\[([^:\\]]+)(?:::(.*?))?]");
        Matcher matcher = pattern.matcher(variablesAsString);

        while (matcher.find()) {
            String variable = matcher.group(1);
            String value = matcher.group(2);
            variables.add(new VariableArg(variable, value));
        }

        return variables;
    }



    public static String toString(List<VariableArg> variables) {
        if (variables == null || variables.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder(variables.size() * 10);
        for (int i = 0; i < variables.size(); i++) {
            VariableArg variable = variables.get(i);
            result.append("[")
                    .append(variable.getVariable())
                    .append("::")
                    .append(variable.getValue() != null ? variable.getValue() : "")
                    .append("]");

            if (i < variables.size() - 1) {
                result.append(", ");
            }
        }

        return result.toString();
    }

    @Override
    public ArgType getArgType() {
        return ArgType.VARIABLE;
    }

    @Override
    public boolean hasArgs() {
        return  !variable.isEmpty() && !value.isEmpty();
    }
}
