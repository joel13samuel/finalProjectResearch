package oop.project.research.scenarios;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.ArgumentType;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

public final class ArgumentScenarios {

    public static Map<String, Object> add(String[] arguments) throws ArgumentParserException {
        var parser = ArgumentParsers.newFor("add").build();
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

    public static Map<String, Object> sub(String[] arguments) throws ArgumentParserException {
        var parser = ArgumentParsers.newFor("sub").build();
        parser.addArgument("left").type(double.class);
        parser.addArgument("right").type(double.class);
        var namespace = parser.parseArgs(arguments);
        var left = namespace.getDouble("left");
        var right = namespace.getDouble("right");
        return Map.of("left", left, "right", right);
    }

    public static Map<String, Object> fizzbuzz(String[] arguments) throws ArgumentParserException {
        var parser = ArgumentParsers.newFor("fizzbuzz").build();
        parser.addArgument("number").type(int.class);
        var namespace = parser.parseArgs(arguments);
        var number = namespace.getInt("number");
        if (number >= 1 && number <= 100) {
            return Map.of("number", number);
        }
        throw new ArgumentParserException("number must be between 1 and 100", parser);
    }

    public static Map<String, Object> difficulty(String[] arguments) throws ArgumentParserException {
        var parser = ArgumentParsers.newFor("difficulty").build();
        parser.addArgument("difficulty").type(String.class);
        var namespace = parser.parseArgs(arguments);
        var difficulty = namespace.getString("difficulty");
        if (Objects.equals(difficulty, "peaceful") || Objects.equals(difficulty, "easy") ||
                Objects.equals(difficulty, "normal") || Objects.equals(difficulty, "hard")) {
            return Map.of("difficulty", difficulty);
        }
        throw new ArgumentParserException("Not a real mode", parser);
    }

    public static Map<String, Object> date(String[] arguments) throws ArgumentParserException {
        var parser = ArgumentParsers.newFor("date").build();
        parser.addArgument("date").type((ArgumentType<LocalDate>) (parser1, arg, value) -> {
            try {
                return LocalDate.parse(value);
            } catch (Exception e) {
                throw new ArgumentParserException("Invalid date format: " + value, parser1);
            }
        });
        var namespace = parser.parseArgs(arguments);
        LocalDate date = namespace.get("date");
        return Map.of("date", date);
    }

}
