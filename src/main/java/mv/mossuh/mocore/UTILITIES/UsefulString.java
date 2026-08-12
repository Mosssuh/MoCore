package mv.mossuh.mocore.UTILITIES;

import me.clip.placeholderapi.PlaceholderAPI;
import mv.mossuh.mocore.UTILITIES.ARGS.CommandArgs.CommandArgs;
import mv.mossuh.mocore.UTILITIES.ARGS.VariableArgs.VariableArg;
import mv.mossuh.mocore.VERSION.ServerVersion;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UsefulString<T extends UsefulString<T>> {
    private StringBuilder builder;
    private boolean isString = false;
    protected UsefulString(String string) {
        if (string != null && !string.isEmpty()) { this.isString = true; }
        this.builder = new StringBuilder(string != null ? string : "");
    }

    public static UsefulString<?> get(String string) {
        return new Self(string);
    }

    private static final class Self extends UsefulString<Self> {
        private Self(String string) { super(string); }
    }

    @SuppressWarnings("unchecked")
    protected T self() { return (T) this; }

    private void replacePattern(Pattern pattern, Function<Matcher, String> replacementFunction) {
        Matcher matcher = pattern.matcher(builder);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String replacement = replacementFunction.apply(matcher);
            matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(result);
        builder = new StringBuilder(result.toString());
    }

    private void replacePatternVariables(Pattern pattern, Map<String, String> map) {
        Matcher matcher = pattern.matcher(builder);
        StringBuilder result = null;
        int lastEnd = 0;
        while (matcher.find()) {
            String replacement = map.get(matcher.group());
            if (replacement != null) {
                if (result == null) {
                    result = new StringBuilder(builder.length());
                }
                result.append(builder, lastEnd, matcher.start()).append(replacement);
                lastEnd = matcher.end();
            }
        }
        if (result != null) {
            result.append(builder, lastEnd, builder.length());
            builder = result;
        }
    }


    private static final Pattern HEX_PATTERN = Pattern.compile("#[a-fA-F0-9]{6}");
    private static final Pattern VARIABLE_TOKEN_PATTERN = Pattern.compile("%[^%]+%");

    /*
    public static UsefulString get(String string) {
        return new UsefulString(string);
    }
     */

    public boolean isString() { return isString; }

    public T hex() {
        if (isString && builder.indexOf("#") != -1 && ServerVersion.isAtLeast(ServerVersion.MC1_16)) {
            replacePattern(HEX_PATTERN, matcher -> {
                String hex = matcher.group().substring(1); // Remove #
                StringBuilder hexColor = new StringBuilder("§x");
                for (char c : hex.toCharArray()) {
                    hexColor.append('§').append(c);
                }
                return hexColor.toString();
            });
        }
        builder = new StringBuilder(ChatColor.translateAlternateColorCodes('&', builder.toString()));
        return self();
    }


    public T replaceString(String target, String replacement) {
        UsefulMethods.replacePlaceholder(builder, target, replacement);
        return self();
    }

    public T setPlaceholders(Player player) {
        if (isString) {
            if (player != null && player.isOnline()) {
                String string = builder.toString();
                builder = new StringBuilder(PlaceholderAPI.setPlaceholders(player, string));
            }
        }
        return self();
    }

    public T setPlaceholders(CommandSender sender) {
        if (isString) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                String string = builder.toString();
                builder = new StringBuilder(PlaceholderAPI.setPlaceholders(player, string));
            }
        }
        return self();
    }

    public T setPlaceholders(OfflinePlayer player) {
        if (isString) {
            if (player != null) {
                String string = builder.toString();
                builder = new StringBuilder(PlaceholderAPI.setPlaceholders(player, string));
            }
        }
        return self();
    }

    public T setPlaceholders(UUID uuid) {
        if (isString) {
            if (uuid != null) {
                if (Bukkit.getPlayer(uuid) != null) {
                    Player player = Bukkit.getPlayer(uuid);
                    String string = builder.toString();
                    builder = new StringBuilder(PlaceholderAPI.setPlaceholders(player, string));
                } else {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
                    String string = builder.toString();
                    builder = new StringBuilder(PlaceholderAPI.setPlaceholders(player, string));
                }
            }
        }
        return self();
    }

    public T setArgs(CommandArgs args) {
        if (isString && args != null && args.hasArgs() && builder.indexOf("%args_") != -1) {
            List<String> argList = args.getArgs();
            if (!argList.isEmpty()) {
                Map<String, String> tokenMap = new HashMap<>(Math.max(8, argList.size() * 2));
                for (int i = 0; i < argList.size(); i++) {
                    tokenMap.put("%args_" + (i + 1) + "%", argList.get(i));
                }
                replacePatternVariables(VARIABLE_TOKEN_PATTERN, tokenMap);
            }
        }
        return self();
    }

    public T setVariables(List<VariableArg> variables) {
        if (isString && variables != null && !variables.isEmpty() && builder.indexOf("%") != -1) {
            Map<String, String> tokens = null;
            List<VariableArg> exceptions = null;

            for (VariableArg variable : variables) {
                if (variable.isVariable() && variable.isValue()) {
                    String key = variable.getVariable();
                    if (isToken(key)) {
                        if (tokens == null) {
                            tokens = new HashMap<>(Math.max(16, variables.size() * 2));
                        }
                        tokens.put(key, variable.getValue());
                    } else {
                        if (exceptions == null) {
                            exceptions = new ArrayList<>();
                        }
                        exceptions.add(variable);
                    }
                }
            }

            if (tokens != null) {
                replacePatternVariables(VARIABLE_TOKEN_PATTERN, tokens);
            }

            if (exceptions != null) {
                for (VariableArg variable : exceptions) {
                    UsefulMethods.replacePlaceholder(builder, variable.getVariable(), variable.getValue());
                }
            }
        }
        return self();
    }

    private static boolean isToken(String key) {
        return key != null && key.length() > 1 && key.charAt(0) == '%' && key.charAt(key.length() - 1) == '%';
    }

    public T setRandomNumberVariable() {
        String string = builder.toString();
        builder = new StringBuilder(UsefulMethods.setRandomNumber(string));
        return self();
    }

    public T setMathPlaceholder() {
        if (isString) {
            String string = builder.toString();
            builder = new StringBuilder(UsefulMethods.setMathPlaceholder(string));
        }
        return self();
    }

    public T setChangeOutputPlaceholder() {
        if (isString) {
            String string = builder.toString();
            builder = new StringBuilder(UsefulMethods.setChangeOutputPlaceholder(string));
        }
        return self();
    }

    public T setTimeFormatter(TimeFormat format) {
        if (isString) {
            String string = builder.toString();
            builder = new StringBuilder(UsefulMethods.setTimeFormatter(string, format));
        }
        return self();
    }

    public T removeColors() {
        String string = builder.toString();

        string = ChatColor.stripColor(string);
        string = ChatColor.translateAlternateColorCodes('&', string);
        string = ChatColor.stripColor(string);

        builder = new StringBuilder(string);
        return self();
    }

    public boolean evaluateString() {
        return UsefulMethods.evaluateString(builder.toString());
    }

    public boolean isNumeric() {
        return UsefulMethods.isNumeric(builder.toString());
    }

    public String apply() {
        return builder.toString();
    }

    public void sendMessage(CommandSender sender) {
        if (sender instanceof Player) {
            sender.sendMessage(builder.toString());
        } else {
            Bukkit.getConsoleSender().sendMessage(builder.toString());
        }
    }

    public void sendMessage(Player player) {
        if (isString) {
            if (player != null && player.isOnline()) {
                player.sendMessage(builder.toString());
            }
        }
    }

    public void sendMessage(UUID uuid) {
        if (isString) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null && player.isOnline()) {
                player.sendMessage(builder.toString());
            }
        }
    }

    public void sendMessageInConsole() {
        Bukkit.getConsoleSender().sendMessage(builder.toString());
    }

    public void sendMessageToOnlinePlayers() {
        if (isString) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(builder.toString());
            }
        }
    }

    public boolean hasPermission(CommandSender sender) {
        if (isString) {
            if (sender != null) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    return player.hasPermission(builder.toString());
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasPermission(Player player) {
        if (isString) {
            if (player != null) {
                return player.hasPermission(builder.toString());
            }
        }
        return false;
    }

    public boolean hasPermission(UUID uuid) {
        if (isString) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                return player.hasPermission(builder.toString());
            }
        }
        return false;
    }
}

