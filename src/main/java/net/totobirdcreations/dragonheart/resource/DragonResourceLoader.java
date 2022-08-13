package net.totobirdcreations.dragonheart.resource;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.entity.dragon.util.DragonSalt;
import net.totobirdcreations.dragonheart.event.ResourceEventHandlers;
import net.totobirdcreations.dragonheart.util.data.colour.HSVColour;
import net.totobirdcreations.dragonheart.util.data.colour.RGBColour;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.*;


public class DragonResourceLoader implements SimpleSynchronousResourceReloadListener {
    public static DragonResourceLoader INSTANCE         = new DragonResourceLoader();
    public static Identifier           RESET_CHANNEL    = new Identifier(DragonHeart.ID, "reset_dragon_resources");
    public static Identifier           REGISTER_CHANNEL = new Identifier(DragonHeart.ID, "register_dragon_resources");

    public HashMap<Identifier, DragonResource> dragonResources = new HashMap<>();


    public static DragonResource getResource(Identifier identifier) {
        if (INSTANCE.dragonResources.containsKey(identifier) && ! identifier.equals(NbtHelper.EMPTY_TYPE)) {
            return INSTANCE.dragonResources.get(identifier);
        } else {
            return new DragonResource(
                    NbtHelper.EMPTY_TYPE,
                    NbtHelper.EMPTY_TYPE,
                    NbtHelper.EMPTY_TYPE,
                    new ArrayList<>(),
                    RGBColour.WHITE,
                    new ArrayList<>(),
                    false,
                    true,
                    false,
                    false,
                    false,
                    new RGBColour( 0.843f , 0.729f , 0.588f ),
                    new RGBColour( 0.698f , 0.604f , 0.486f ),
                    new RGBColour( 1.0f   , 0.804f , 0.416f ),
                    null
            );
        }
    }


    public static Set<Identifier> getIdentifiers() {
        return new LinkedHashSet<>(INSTANCE.dragonResources.keySet());
    }


    @Override
    public Identifier getFabricId() {
        return new Identifier(DragonHeart.ID, "dragon_type_loader");
    }


    private String getJsonString(JsonObject object, String key) throws Exception {
        if (object.has(key)) {
            if (object.get(key).isJsonPrimitive()) {
                return object.get(key).getAsString();
            } else {
                throw new Exception("`" + key + "` not a primitive.");
            }
        } else {
            throw new Exception("Missing `" + key + "`.");
        }
    }


    private boolean getJsonBoolean(JsonObject object, String key) throws Exception {
        if (object.has(key)) {
            if (object.get(key).isJsonPrimitive()) {
                return object.get(key).getAsBoolean();
            } else {
                throw new Exception("`" + key + "` not a primitive.");
            }
        } else {
            throw new Exception("Missing `" + key + "`.");
        }
    }


    private RGBColour getJsonRGB(JsonObject object, String key) throws Exception {
        if (object.has(key)) {
            return this.getJsonRGB(object.get(key), key);
        } else {
            throw new Exception("Missing `" + key + "`.");
        }
    }


    private RGBColour getJsonRGB(JsonElement element, String key) throws Exception {
        if (element.isJsonArray()) {
            JsonArray array = element.getAsJsonArray();
            if (array.size() == 3) {
                for (int i = 0; i < array.size(); i++) {
                    if (! array.get(i).isJsonPrimitive()) {
                        throw new Exception("`" + key + "[" + i + "]` not a primitive.");
                    }
                }
                return new RGBColour(
                        array.get(0).getAsFloat(),
                        array.get(1).getAsFloat(),
                        array.get(2).getAsFloat()
                );
            } else {
                throw new Exception("`" + key + "` not a size 3 array.");
            }
        } else {
            throw new Exception("`" + key + "` not a array.");
        }
    }


    private Collection<RGBColour> getJsonRGBList(JsonObject object, String key) throws Exception {
        if (object.has(key)) {
            if (object.get(key).isJsonArray()) {
                JsonArray array = object.getAsJsonArray(key);
                if (array.size() >= 1) {
                    Collection<RGBColour> list = new ArrayList<>();
                    for (int i = 0; i < array.size(); i++) {
                        list.add(this.getJsonRGB(array.get(i), key + "[" + i + "]"));
                    }
                    return list;
                } else {
                    throw new Exception("`" + key + "` not a size 1+ array.");
                }
            } else {
                throw new Exception("`" + key + "` not an array.");
            }
        } else {
            throw new Exception("Missing `" + key + "`.");
        }
    }


    @Override
    public void reload(ResourceManager manager) {
        Exception exception = null;
        try {
            this.dragonResources = new HashMap<>();
            Map<Identifier, Resource> resources = manager.findResources("dragons", this::isValidFile);
            for (Identifier path : resources.keySet()) {
                Resource resource = resources.get(path);
                try (InputStream stream = resource.getInputStream()) {
                    JsonElement element = JsonParser.parseString(new String(stream.readAllBytes()));
                    if (element instanceof JsonObject object) {

                        String                entityBreath             = this.getJsonString(object, "entity_breath");
                        String                entitySpike              = this.getJsonString(object, "entity_spike");
                        Collection<RGBColour> bodyColours              = this.getJsonRGBList(object, "body_colours");
                        RGBColour             eyeColour                = this.getJsonRGB(object, "eye_colour");
                        Collection<RGBColour> creativeEggColours       = this.getJsonRGBList(object, "creative_egg_colours");
                        boolean               canBreatheInWater        = this.getJsonBoolean(object, "can_breathe_in_water");
                        boolean               canFreeze                = this.getJsonBoolean(object, "can_freeze");
                        boolean               hurtByWater              = this.getJsonBoolean(object, "hurt_by_water");
                        boolean               fireImmune               = this.getJsonBoolean(object, "fire_immune");
                        boolean               explosionImmuneWhenAwake = this.getJsonBoolean(object, "explosion_immune_when_awake");
                        RGBColour             colourBricks             = this.getJsonRGB(object, "colour_bricks");
                        RGBColour             colourCracks             = this.getJsonRGB(object, "colour_cracks");
                        RGBColour             colourGlow               = this.getJsonRGB(object, "colour_glow");

                        String   idPath      = path.getPath();
                        String[] idPathParts;
                        // Remove dragons directory.
                        idPathParts = idPath.split("/");
                        idPathParts = Arrays.copyOfRange(idPathParts, 1, idPathParts.length);
                        idPath                 = String.join("/", idPathParts);
                        // Remove .json suffix.
                        idPathParts = idPath.split("\\.");
                        idPathParts = Arrays.copyOfRange(idPathParts, 0, idPathParts.length - 1);
                        idPath      = String.join(".", idPathParts);
                        // Create resource.
                        Identifier id = new Identifier(path.getNamespace(), idPath);
                        if (! id.equals(NbtHelper.EMPTY_TYPE)) {
                            this.dragonResources.put(id, new DragonResource(
                                    id,
                                    new Identifier(entityBreath),
                                    new Identifier(entitySpike),
                                    bodyColours,
                                    eyeColour,
                                    creativeEggColours,
                                    canBreatheInWater,
                                    canFreeze,
                                    hurtByWater,
                                    fireImmune,
                                    explosionImmuneWhenAwake,
                                    colourBricks,
                                    colourCracks,
                                    colourGlow,
                                    TagKey.of(Registry.BIOME_KEY, new Identifier(id.getNamespace(), "dragons/" + id.getPath()))
                            ));
                        }
                    } else {
                        throw new Exception("Not an object.");
                    }
                } catch (Exception e) {
                    DragonHeart.LOGGER.error("Failed to load dragon type `" + path.toString() + "`: " + e);
                }
            }

        } catch (Exception e) {
            DragonHeart.LOGGER.error("Failed to load dragon types.");
        }

        // Send update to clients.
        ResourceEventHandlers.serverside_send_dragonresource_signal();
    }


    public boolean isValidFile(Identifier path) {
        return path.getPath().endsWith(".json");
    }


    public record DragonResource(
            Identifier            id,
            @Nullable
            Identifier            entityBreath,
            @Nullable
            Identifier            entitySpike,
            Collection<RGBColour> bodyColours,
            RGBColour             eyeColour,
            Collection<RGBColour> creativeEggColours,
            @Nullable
            Boolean               canBreatheInWater,
            @Nullable
            Boolean               canFreeze,
            @Nullable
            Boolean               hurtByWater,
            @Nullable
            Boolean               fireImmune,
            @Nullable
            Boolean               explosionImmuneWhenAwake,
            RGBColour             colourBricks,
            RGBColour             colourCracks,
            RGBColour             colourGlow,
            @Nullable
            TagKey<Biome>         spawnsIn
    ) {

        public Text getName() {
            String path = this.id.getPath().replace("/", ".");
            if (path.equals("")) {
                return Text.translatable("dragon." + DragonHeart.ID + ".base");
            } else {
                return Text.translatable("dragon." + this.id.getNamespace() + ".type." + path);
            }
        }

        public RGBColour chooseBodyColour(Object baseSeed) {
            if (this.bodyColours.size() == 0) {
                return RGBColour.WHITE;
            }
            return new ArrayList<>(this.bodyColours).get(
                    Random.create(DragonSalt.COLOUR + baseSeed.hashCode())
                            .nextInt(this.bodyColours.size()));
        }
        public RGBColour variateBodyColour(RGBColour baseRgb, Object variateSeed) {
            HSVColour base      = baseRgb.toHsv();
            Random    random    = Random.create(DragonSalt.COLOUR + variateSeed.hashCode());
            boolean   direction = random.nextBoolean();
            for (int i = 0; i < random.nextBetween(25, 50); i++) {
                float     amount  = random.nextFloat() * 0.1f * (direction ? 1 : -1);
                HSVColour newBase = base.addHue(amount);
                if (this.closestTypeIsThis(newBase)) {
                    base = newBase;
                } else {
                    direction = ! direction;
                }
            }
            base.s = Math.max(Math.min(base.s +
                            (random.nextFloat() * 0.25f - 0.125f),
                    1.0f
            ), 0.0f);
            base.v = Math.max(Math.min(base.v +
                            (random.nextFloat() * 0.25f - 0.125f),
                    1.0f
            ), 0.0f);
            return base.toRgb();
        }
        private boolean closestTypeIsThis(HSVColour colour) {
            Identifier closestId   = this.id;
            float      closestDist = -1.0f;
            for (Identifier id : getIdentifiers()) {
                DragonResource resource = getResource(id);
                for (RGBColour rgb : resource.bodyColours) {
                    HSVColour hsv  = rgb.toHsv();
                    float     dist = colour.hueDistance(hsv.h);
                    if (closestDist < 0.0f || dist < closestDist) {
                        closestId   = id;
                        closestDist = dist;
                    }
                }
            }
            return closestId.equals(this.id);
        }



        private static String fromIdentifier(Identifier text) {
                return fromString(text.toString());
            }

        private static String fromRGBList(Collection<RGBColour> colours) {
            Collection<String> parts = new ArrayList<>();
            for (RGBColour colour : colours) {
                parts.add(fromRGB(colour));
            }
                return String.join(",", parts);
            }

        private static String fromRGB(RGBColour rgbColour) {
                return fromString(String.valueOf(rgbColour.toInt()));
            }

        private static String fromString(String string) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < string.length(); i++) {
                char ch = string.charAt(i);
                if (ch == '/' || ch == ',' || ch == '\\') {
                    builder.append('\\');
                }
                builder.append(ch);
            }
            return builder.toString();
        }


        public String getString(Identifier path) {
            return fromIdentifier(path) + "/"
                    + fromIdentifier(this.id) + "/"
                    + fromRGBList(this.bodyColours) + "/"
                    + fromRGB(this.eyeColour) + "/"
                    + fromRGBList(this.creativeEggColours) + "/"
                    + fromRGB(this.colourBricks) + "/"
                    + fromRGB(this.colourCracks) + "/"
                    + fromRGB(this.colourGlow) + "/";
        }

        public static Collection<String> getParts(String data) {
            return getParts(data, '/', true);
        }

        private static Collection<String> getParts(String data, char delimiter, boolean requireDelimiterEnd) {
            Collection<String> parts = new ArrayList<>();
            StringBuilder builder = new StringBuilder();
            boolean escape = false;
            for (int i = 0; i < data.length(); i++) {
                char ch = data.charAt(i);
                if (escape) {
                    builder.append(ch);
                    escape = false;
                } else {
                    if (ch == '\\') {
                        escape = true;
                    } else if (ch == delimiter) {
                        parts.add(builder.toString());
                        builder = new StringBuilder();
                    } else {
                        builder.append(ch);
                    }
                }
            }
            if (!requireDelimiterEnd) {
                parts.add(builder.toString());
            }
            return parts;
        }

        private static Collection<RGBColour> toRGBList(Collection<String> strings) {
            Collection<RGBColour> colours = new ArrayList<>();
            for (String string : strings) {
                colours.add(toRGB(string));
            }
            return colours;
        }

        private static RGBColour toRGB(String string) {
            return new RGBColour(Integer.parseInt(string));
        }


        @Nullable
        public static DragonResource fromParts(Collection<String> parts) {
            try {
                Iterator<String> i = parts.iterator();
                return new DragonResource(
                        new Identifier(i.next()),
                        null,
                        null,
                        toRGBList(getParts(i.next(), ',', false)),
                        toRGB(i.next()),
                        toRGBList(getParts(i.next(), ',', false)),
                        null,
                        null,
                        null,
                        null,
                        null,
                        toRGB(i.next()),
                        toRGB(i.next()),
                        toRGB(i.next()),
                        null
                );
            } catch (Exception ignored) {
                return null;
            }
        }

    }

}
