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

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UsefulString{
    private StringBuilder builder;
    private boolean isString = false;
    public UsefulString(String string) {
        if (string != null && !string.isEmpty()) { this.isString = true; }
        this.builder = new StringBuilder(string != null ? string : "");
    }


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


    private static final Pattern HEX_PATTERN = Pattern.compile("#[a-fA-F0-9]{6}");

    public static UsefulString get(String string) {
        return new UsefulString(string);
    }

    public boolean isString() { return isString; }

    public UsefulString hex() {
        if (ServerVersion.isAtLeast(ServerVersion.MC1_16)) {
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
        return this;
    }


    public UsefulString replaceString(String target, String replacement) {
        UsefulMethods.replacePlaceholder(builder, target, replacement);
        return this;
    }

    public UsefulString setPlaceholders(Player player) {
        if (isString) {
            if (player != null && player.isOnline()) {
                String string = builder.toString();
                builder = new StringBuilder(PlaceholderAPI.setPlaceholders(player, string));
            }
        }
        return this;
    }

    public UsefulString setPlaceholders(CommandSender sender) {
        if (isString) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                String string = builder.toString();
                builder = new StringBuilder(PlaceholderAPI.setPlaceholders(player, string));
            }
        }
        return this;
    }

    public UsefulString setPlaceholders(OfflinePlayer player) {
        if (isString) {
            if (player != null) {
                String string = builder.toString();
                builder = new StringBuilder(PlaceholderAPI.setPlaceholders(player, string));
            }
        }
        return this;
    }

    public UsefulString setPlaceholders(UUID uuid) {
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
        return this;
    }

    public UsefulString setArgs(CommandArgs args) {
        if (args != null && args.hasArgs()) {
            List<String> argList = args.getArgs();
            for (int i = 0; i < argList.size(); i++) {
                UsefulMethods.replacePlaceholder(builder, "%args_" + (i + 1) + "%", argList.get(i));
            }
        }
        return this;
    }

    public UsefulString setVariables(List<VariableArg> variables) {
        if (variables != null && !variables.isEmpty()) {
            for (VariableArg variable : variables) {
                if (variable.isVariable() && variable.isValue()) {
                    UsefulMethods.replacePlaceholder(builder, variable.getVariable(), variable.getValue());
                }
            }
        }
        return this;
    }

    public UsefulString setRandomNumberVariable() {
        String string = builder.toString();
        builder = new StringBuilder(UsefulMethods.setRandomNumber(string));
        return this;
    }

    public UsefulString setMathPlaceholder() {
        if (isString) {
            String string = builder.toString();
            builder = new StringBuilder(UsefulMethods.setMathPlaceholder(string));
        }
        return this;
    }

    public UsefulString setChangeOutputPlaceholder() {
        if (isString) {
            String string = builder.toString();
            builder = new StringBuilder(UsefulMethods.setChangeOutputPlaceholder(string));
        }
        return this;
    }

    public UsefulString setTimeFormatter(TimeFormat format) {
        if (isString) {
            String string = builder.toString();
            builder = new StringBuilder(UsefulMethods.setTimeFormatter(string, format));
        }
        return this;
    }

    public UsefulString removeColors() {
        String string = builder.toString();

        string = ChatColor.stripColor(string);
        string = ChatColor.translateAlternateColorCodes('&', string);
        string = ChatColor.stripColor(string);

        builder = new StringBuilder(string);
        return this;
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

