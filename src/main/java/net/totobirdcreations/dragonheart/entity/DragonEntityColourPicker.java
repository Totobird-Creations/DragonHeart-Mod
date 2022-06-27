package net.totobirdcreations.dragonheart.entity;

import net.minecraft.util.math.random.Random;
import net.totobirdcreations.dragonheart.util.Curve;
import net.totobirdcreations.dragonheart.util.HSVColour;
import net.totobirdcreations.dragonheart.util.RGBColour;

import java.util.HashMap;
import java.util.UUID;


public class DragonEntityColourPicker {

    public static float                                         THRESHOLD  = 0.375f;
    public static long                                          SALT       = 193069481;
    public static int                                           DIGITS     = 4;
    public static Curve                                         SATURATION = new Curve(0.625f, 0.25f);
    public static HashMap<DragonEntity.DragonType, RGBColour[]> OPTIONS;

    static {
        OPTIONS = new HashMap();
        OPTIONS.put(DragonEntity.DragonType.FIRE, new RGBColour[]{
                new RGBColour(1.0f  , 0.0f  , 0.0f ),
                new RGBColour(1.0f  , 0.25f , 0.0f )
        });
        OPTIONS.put(DragonEntity.DragonType.ICE, new RGBColour[]{
                new RGBColour(0.0f  , 1.0f  , 1.0f ),
                new RGBColour(0.0f  , 0.75f , 1.0f )
        });
        OPTIONS.put(DragonEntity.DragonType.LIGHTNING, new RGBColour[]{
                new RGBColour(0.75f , 0.0f  , 1.0f ),
                new RGBColour(1.0f  , 0.0f  , 1.0f ),
                new RGBColour(1.0f  , 1.0f  , 1.0f )
        });
    }


    public static RGBColour chooseRandom(Random rand) {
        int   digits  = (int)(Math.pow(10, DIGITS));
        float chosenR = (float)(rand.nextBetween(0, digits)) / (float)digits;
        float chosenG = (float)(rand.nextBetween(0, digits)) / (float)digits;
        float chosenB = (float)(rand.nextBetween(0, digits)) / (float)digits;
        return new RGBColour(chosenR, chosenG, chosenB);
    }

    public static RGBColour adjust(RGBColour colour) {
        if (colour.greyscale().r >= THRESHOLD) {
            return colour;
        } else {
            if (colour.r < THRESHOLD) {
                colour.r = THRESHOLD;
            }
            if (colour.g < THRESHOLD) {
                colour.g = THRESHOLD;
            }
            if (colour.b < THRESHOLD) {
                colour.b = THRESHOLD;
            }
            return colour;
        }
    }

    public static RGBColour choose(Random rand) {
        RGBColour colour = chooseRandom(rand);
        return adjust(colour);
    }

    public static DragonEntity.DragonType checkClosest(RGBColour colour) {
        DragonEntity.DragonType bestKey  = DragonEntity.DragonType.NONE;
        float                   bestDist = 0.0f;
        for (int k=0;k< OPTIONS.size() ;k++) {
            DragonEntity.DragonType key = (DragonEntity.DragonType)(OPTIONS.keySet().toArray()[k]);
            for (int o=0;o< OPTIONS.get(key).length ;o++) {
                RGBColour other = OPTIONS.get(key)[o];
                float  dist  = colour.distance(other);
                if (bestKey == DragonEntity.DragonType.NONE || dist < bestDist) {
                    bestKey  = key;
                    bestDist = dist;
                }
            }
        }
        return bestKey;
    }

    public static RGBColour chooseFromCategory(DragonEntity.DragonType category, UUID uuid) {
        long   sugar = Integer.parseUnsignedInt(uuid.toString().split("-")[0], 16);
        Random rand  = Random.create(SALT + sugar);

        while (true) {
            RGBColour               colour  = choose(rand);
            DragonEntity.DragonType closest = checkClosest(colour);
            if (closest == category) {
                HSVColour hsv = colour.toHsv();
                hsv.s         = SATURATION.interpolate(hsv.s);
                return hsv.toRgb();
            }
        }
    }

}
