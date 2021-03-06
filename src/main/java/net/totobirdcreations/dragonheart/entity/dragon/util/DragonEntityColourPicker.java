package net.totobirdcreations.dragonheart.entity.dragon.util;

import net.minecraft.util.math.random.Random;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;
import net.totobirdcreations.dragonheart.util.Curve;
import net.totobirdcreations.dragonheart.util.colour.HSVColour;
import net.totobirdcreations.dragonheart.util.colour.RGBColour;

import java.util.HashMap;
import java.util.UUID;


public class DragonEntityColourPicker {

    public static float                                         THRESHOLD  = 0.125f;
    public static int                                           DIGITS     = 4;
    public static Curve                                         SATURATION = new Curve(0.875f, 0.25f);
    public static HashMap<DragonEntity.DragonType, RGBColour[]> OPTIONS    = new HashMap<>();

    static {
        OPTIONS.put(DragonEntity.DragonType.FIRE, new RGBColour[]{
                // Interval
                new RGBColour( 1.0f  , 0.0f  , 0.25f ), // Pink-Red
                new RGBColour( 1.0f  , 0.0f  , 0.0f  ),
                new RGBColour( 1.0f  , 0.25f , 0.0f  ), // Red-Orange
                new RGBColour( 1.0f  , 0.5f  , 0.0f  ),
                new RGBColour( 1.0f  , 0.75f , 0.0f  ), // Orange-Yellow
                // Special
                new RGBColour( 0.0f  , 0.5f  , 0.0f  ), // Dark Green
                new RGBColour( 0.5f  , 0.5f  , 0.5f  ), // Grey
        });
        OPTIONS.put(DragonEntity.DragonType.ICE, new RGBColour[]{
                // Interval
                new RGBColour( 0.25f , 1.0f  , 0.0f  ), // Yellow-Green
                new RGBColour( 0.0f  , 1.0f  , 0.0f  ),
                new RGBColour( 0.0f  , 1.0f  , 0.25f ),
                new RGBColour( 0.0f  , 1.0f  , 0.5f  ), // Green-Blue
                new RGBColour( 0.0f  , 1.0f  , 0.75f ),
                new RGBColour( 0.0f  , 1.0f  , 1.0f  ), // Teal
                new RGBColour( 0.0f  , 0.75f , 1.0f  ),
                new RGBColour( 0.0f  , 0.5f  , 1.0f  ), // Blue
                // Special
                new RGBColour( 1.0f  , 1.0f  , 1.0f  ), // White
                new RGBColour( 0.75f , 0.75f , 0.75f )
        });
        OPTIONS.put(DragonEntity.DragonType.LIGHTNING, new RGBColour[]{
                // Interval
                new RGBColour( 0.0f  , 0.25f , 1.0f  ), // Electric Blue
                new RGBColour( 0.0f  , 0.0f  , 1.0f  ),
                new RGBColour( 0.25f , 0.0f  , 1.0f  ),
                new RGBColour( 0.5f  , 0.0f  , 1.0f  ),
                new RGBColour( 0.75f , 0.0f  , 1.0f  ), // Purple
                new RGBColour( 1.0f  , 0.0f  , 1.0f  ),
                new RGBColour( 1.0f  , 0.0f  , 0.75f ),
                new RGBColour( 1.0f  , 0.0f  , 0.5f  ), // Pink
                // Special
                new RGBColour( 0.0f  , 0.0f  , 0.0f  ), // Black
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
        if (colour.toHsv().greyscale().v < THRESHOLD) {
            if (colour.r < THRESHOLD) {
                colour.r = THRESHOLD;
            }
            if (colour.g < THRESHOLD) {
                colour.g = THRESHOLD;
            }
            if (colour.b < THRESHOLD) {
                colour.b = THRESHOLD;
            }
        }
        return colour;
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
                float     dist  = colour.toLab().distance(other.toLab());
                if (bestKey == DragonEntity.DragonType.NONE || dist < bestDist) {
                    bestKey  = key;
                    bestDist = dist;
                }
            }
        }
        return bestKey;
    }

    public static RGBColour chooseFromCategory(DragonEntity.DragonType category, UUID uuid) {
        Random rand  = Random.create(DragonSalt.COLOUR + UuidOp.uuidToInt(uuid));

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
