package net.totobirdcreations.dragonheart.util.helper;

import net.minecraft.util.Identifier;


public class DataHelper {

    public static boolean dragonTypeMatches(Identifier source, Identifier pattern) {
        return stringPartMatches(source.getNamespace(), pattern.getNamespace()) && stringPartMatches(source.getPath(), pattern.getPath());
    }

    public static boolean stringPartMatches(String source, String pattern) {
        return pattern.equals(".") || pattern.equals(source);
    }

}
