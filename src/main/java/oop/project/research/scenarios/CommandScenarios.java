package oop.project.research.scenarios;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.ArgumentType;

import java.util.Map;

public final class CommandScenarios {

    public static Map<String, Object> mul(String[] arguments) throws ArgumentParserException {
        var parser = ArgumentParsers.newFor("mul").build();
        parser.addArgument("left").type(int.class);
        parser.addArgument("right").type(int.class);
        var namespace = parser.parseArgs(arguments);
        //Note: namespace.getAttrs() returns a Map directly, but a major
        //part of this problem is to ensure we can safely get arguments with
        //the correct static type for use in a real program!
        var left = namespace.getInt("left"); //uses getInt to return int
        int right = namespace.get("right"); //uses type inference - clever but risky!
        return Map.of("left", left, "right", right);
    }

    public static Map<String, Object> div(String[] arguments) throws ArgumentParserException {
        var parser = ArgumentParsers.newFor("div").build();
        parser.addArgument("--left").type(double.class).required(true);
        parser.addArgument("--right").type(double.class).required(true);
        var namespace = parser.parseArgs(arguments);
        var left = namespace.getDouble("left");
        var right = namespace.getDouble("right");
        return Map.of("left", left, "right", right);
    }

    public static Map<String, Object> echo(String[] arguments) throws ArgumentParserException {
        var parser = ArgumentParsers.newFor("echo").build();
        parser.addArgument("message")
                .type(String.class)
                .nargs("?")
                .setDefault("echo,echo,echo...");
        var namespace = parser.parseArgs(arguments);
        var message = namespace.getString("message");
        return Map.of("message", message);
    }

    public static Map<String, Object> search(String[] arguments) throws ArgumentParserException {
        var parser = ArgumentParsers.newFor("search").build();
        parser.addArgument("term").type(String.class);
        ArgumentType<Boolean> strictBoolean = (p, arg, value) -> {
            if (value.equals("true")) return true;
            if (value.equals("false")) return false;
            throw new ArgumentParserException("invalid boolean value: '" + value + "'", p);
        };
        parser.addArgument("--case-insensitive", "-i")
                .type(strictBoolean)
                .setConst(true)
                .setDefault(false)
                .nargs("?");
        var namespace = parser.parseArgs(arguments);
        var term = namespace.getString("term");
        boolean caseInsensitive = namespace.getBoolean("case_insensitive");
        return Map.of("term", term, "case-insensitive", caseInsensitive);
    }

    public static Map<String, Object> dispatch(String[] arguments) throws ArgumentParserException {
        var parser = ArgumentParsers.newFor("dispatch").build();
        parser.addArgument("type").type(String.class).choices("static", "dynamic");
        parser.addArgument("value").type(String.class);
        var namespace = parser.parseArgs(arguments);
        var type = namespace.getString("type");
        var rawValue = namespace.getString("value");
        if (type.equals("static")) {
            try {
                var value = Integer.parseInt(rawValue);
                return Map.of("type", type, "value", value);
            } catch (NumberFormatException e) {
                throw new ArgumentParserException("value must be an int when type is static", parser);
            }
        }
        return Map.of("type", type, "value", rawValue);
    }

}