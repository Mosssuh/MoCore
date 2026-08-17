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


public abstract class UsefulString<T extends UsefulString<T>> {
    private StringBuilder builder;
    private boolean isString = false;

    protected UsefulString(String string) {
        if (string != null && !string.isEmpty()) { this.isString = true; }
        this.builder = new StringBuilder(string != null ? string : "");
    }

    @SuppressWarnings("unchecked")
    protected T self() { return (T) this; }

    private void replacePattern(Pattern pattern, Function<Matcher, String> replacementFunction) {
        Matcher matcher = pattern.matcher(builder);
        StringBuffer result = new StringBuffer(builder.length());
        while (matcher.find()) {
            matcher.appendReplacement(result, Matcher.quoteReplacement(replacementFunction.apply(matcher)));
        }
        matcher.appendTail(result);
        builder = new StringBuilder(result);
    }

    private static final class TrieNode {
        final Map<Character, TrieNode> children = new HashMap<>();
        TrieNode fail;
        String value;
        int length;
    }

    private static TrieNode buildAutomaton(Map<String, String> map) {
        TrieNode root = new TrieNode();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key == null || key.isEmpty()) continue;
            TrieNode node = root;
            for (int i = 0; i < key.length(); i++) {
                node = node.children.computeIfAbsent(key.charAt(i), c -> new TrieNode());
            }
            node.value = entry.getValue();
            node.length = key.length();
        }

        root.fail = root;
        Deque<TrieNode> queue = new ArrayDeque<TrieNode>();
        for (TrieNode child : root.children.values()) {
            child.fail = root;
            queue.add(child);
        }
        while (!queue.isEmpty()) {
            TrieNode current = queue.poll();
            for (Map.Entry<Character, TrieNode> e : current.children.entrySet()) {
                char c = e.getKey();
                TrieNode child = e.getValue();
                TrieNode failNode = current.fail;
                while (failNode != root && !failNode.children.containsKey(c)) {
                    failNode = failNode.fail;
                }
                TrieNode candidate = failNode.children.get(c);
                child.fail = (candidate != null && candidate != child) ? candidate : root;
                queue.add(child);
            }
        }
        return root;
    }

    private void replaceLiteralTokens(Map<String, String> map) {
        if (map == null || map.isEmpty() || builder.length() == 0) return;

        TrieNode root = buildAutomaton(map);
        TrieNode node = root;

        StringBuilder result = null;
        int lastEnd = 0;
        int len = builder.length();

        for (int i = 0; i < len; i++) {
            char c = builder.charAt(i);
            TrieNode next = node.children.get(c);
            while (next == null && node != root) {
                node = node.fail;
                next = node.children.get(c);
            }
            node = (next != null) ? next : root;

            TrieNode match = node;
            while (match != root && match.value == null) {
                match = match.fail;
            }

            if (match.value != null) {
                int matchStart = i - match.length + 1;
                if (result == null) {
                    result = new StringBuilder(len);
                }
                result.append(builder, lastEnd, matchStart).append(match.value);
                lastEnd = i + 1;
                node = root; // no-overlapping: seguimos limpios después del match
            }
        }

        if (result != null) {
            result.append(builder, lastEnd, len);
            builder = result;
        }
    }


    private static final Pattern HEX_PATTERN = Pattern.compile("#[a-fA-F0-9]{6}");

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
        if (isString && args != null && args.hasArgs()) {
            List<String> argList = args.getArgs();
            if (!argList.isEmpty()) {
                Map<String, String> tokenMap = new HashMap<>(Math.max(8, argList.size() * 2));
                for (int i = 0; i < argList.size(); i++) {
                    tokenMap.put("%args_" + (i + 1) + "%", argList.get(i));
                }
                replaceLiteralTokens(tokenMap);
            }
        }
        return self();
    }

    public T setVariables(List<VariableArg> variables) {
        if (isString && variables != null && !variables.isEmpty()) {
            Map<String, String> tokenMap = null;

            for (VariableArg variable : variables) {
                if (variable.isVariable() && variable.isValue()) {
                    if (tokenMap == null) {
                        tokenMap = new HashMap<>(Math.max(16, variables.size() * 2));
                    }
                    tokenMap.put(variable.getVariable(), variable.getValue());
                }
            }

            if (tokenMap != null) {
                replaceLiteralTokens(tokenMap);
            }
        }
        return self();
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
            ((Player) sender).sendMessage(builder.toString());
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

    public static UsefulString<?> get(String string) {
        return new Self(string);
    }

    private static final class Self extends UsefulString<Self> {
        private Self(String string) { super(string); }
    }
}

