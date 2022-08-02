package net.totobirdcreations.dragonheart.resource;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.entity.dragon.util.DragonSalt;
import net.totobirdcreations.dragonheart.entity.dragon.util.UuidOp;
import net.totobirdcreations.dragonheart.event.EventHandlers;
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
            return INSTANCE.dragonResources.get(identifier).copy();
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
                    new RGBColour( 1.0f   , 0.804f , 0.416f )
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


    public String getJsonString(JsonObject object, String key) throws Exception {
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


    public boolean getJsonBoolean(JsonObject object, String key) throws Exception {
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


    public RGBColour getJsonRGB(JsonObject object, String key) throws Exception {
        if (object.has(key)) {
            return this.getJsonRGB(object.get(key), key);
        } else {
            throw new Exception("Missing `" + key + "`.");
        }
    }


    public RGBColour getJsonRGB(JsonElement element, String key) throws Exception {
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


    public ArrayList<RGBColour> getJsonRGBList(JsonObject object, String key) throws Exception {
        if (object.has(key)) {
            if (object.get(key).isJsonArray()) {
                JsonArray array = object.getAsJsonArray(key);
                if (array.size() >= 1) {
                    ArrayList<RGBColour> list = new ArrayList<>();
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
        try {
            this.dragonResources = new HashMap<>();
            Map<Identifier, Resource> resources = manager.findResources("dragons", this::isValidFile);
            for (Identifier path : resources.keySet()) {
                Resource resource = resources.get(path);
                try (InputStream stream = resource.getInputStream()) {
                    JsonElement element = JsonParser.parseString(new String(stream.readAllBytes()));
                    if (element instanceof JsonObject object) {

                        String               entityBreath             = this.getJsonString(object, "entity_breath");
                        String               entitySpike              = this.getJsonString(object, "entity_spike");
                        ArrayList<RGBColour> bodyColours              = this.getJsonRGBList(object, "body_colours");
                        RGBColour            eyeColour                = this.getJsonRGB(object, "eye_colour");
                        ArrayList<RGBColour> creativeEggColours       = this.getJsonRGBList(object, "creative_egg_colours");
                        boolean              canBreatheInWater        = this.getJsonBoolean(object, "can_breathe_in_water");
                        boolean              canFreeze                = this.getJsonBoolean(object, "can_freeze");
                        boolean              hurtByWater              = this.getJsonBoolean(object, "hurt_by_water");
                        boolean              fireImmune               = this.getJsonBoolean(object, "fire_immune");
                        boolean              explosionImmuneWhenAwake = this.getJsonBoolean(object, "explosion_immune_when_awake");
                        RGBColour            colourBricks             = this.getJsonRGB(object, "colour_bricks");
                        RGBColour            colourCracks             = this.getJsonRGB(object, "colour_cracks");
                        RGBColour            colourGlow               = this.getJsonRGB(object, "colour_glow");

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
                                    colourGlow
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
            EventHandlers.serverside_send_dragonresource_signal();
            throw e;
        }

        EventHandlers.serverside_send_dragonresource_signal();

    }


    public boolean isValidFile(Identifier path) {
        return path.getPath().endsWith(".json");
    }


    public record DragonResource(
            Identifier           id,
            Identifier           entityBreath,
            Identifier           entitySpike,
            ArrayList<RGBColour> bodyColours,
            RGBColour            eyeColour,
            ArrayList<RGBColour> creativeEggColours,
            boolean              canBreatheInWater,
            boolean              canFreeze,
            boolean              hurtByWater,
            boolean              fireImmune,
            boolean              explosionImmuneWhenAwake,
            RGBColour            colourBricks,
            RGBColour            colourCracks,
            RGBColour            colourGlow
    ) {

        public Text getName() {
            String path = this.id.getPath().replace("/", ".");
            if (path.equals("")) {
                return Text.translatable("dragon." + DragonHeart.ID + ".base");
            } else {
                return Text.translatable("dragon." + this.id.getNamespace() + ".type." + path);
            }
        }

        public RGBColour chooseBodyColour(UUID uuid) {
            Random         rand       = Random.create(DragonSalt.COLOUR + UuidOp.uuidToInt(uuid));
            return this.bodyColours.size() == 0
                    ? RGBColour.WHITE
                    : this.bodyColours.get(rand.nextInt(this.bodyColours.size()));
        }



        public static String fromIdentifier(Identifier text) {
                return fromString(text.toString());
            }

        public static String fromBoolean(boolean bool) {
                return fromString(bool ? "T" : "F");
            }

        public static String fromRGBList(ArrayList<RGBColour> arrayList) {
            ArrayList<String> parts = new ArrayList<>();
            for (RGBColour rgbColour : arrayList) {
                parts.add(fromRGB(rgbColour));
            }
                return String.join(",", parts);
            }

        public static String fromRGB(RGBColour rgbColour) {
                return fromString(String.valueOf(rgbColour.asInt()));
            }

        public static String fromString(String string) {
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
                    + fromIdentifier(this.entityBreath) + "/"
                    + fromIdentifier(this.entitySpike) + "/"
                    + fromRGBList(this.bodyColours) + "/"
                    + fromRGB(this.eyeColour) + "/"
                    + fromRGBList(this.creativeEggColours) + "/"
                    + fromBoolean(this.canBreatheInWater) + "/"
                    + fromBoolean(this.canFreeze) + "/"
                    + fromBoolean(this.hurtByWater) + "/"
                    + fromBoolean(this.fireImmune) + "/"
                    + fromBoolean(this.explosionImmuneWhenAwake) + "/"
                    + fromRGB(this.colourBricks) + "/"
                    + fromRGB(this.colourCracks) + "/"
                    + fromRGB(this.colourGlow) + "/";
        }

        public static ArrayList<String> getParts(String data) {
            return getParts(data, '/', true);
        }

        public static ArrayList<String> getParts(String data, char delimiter, boolean requireDelimiterEnd) {
            ArrayList<String> parts = new ArrayList<>();
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

        public static ArrayList<RGBColour> toRGBList(ArrayList<String> strings) {
            ArrayList<RGBColour> colours = new ArrayList<>();
            for (String string : strings) {
                colours.add(toRGB(string));
            }
            return colours;
        }

        public static RGBColour toRGB(String string) {
                return new RGBColour(Integer.parseInt(string));
            }

        public static boolean toBoolean(String string) throws Exception {
            if (string.equals("T")) {
                return true;
            } else if (string.equals("F")) {
                return false;
            } else {
                throw new Exception();
            }
        }


        @Nullable
        public static DragonResource fromParts(ArrayList<String> parts) {
            try {
                return new DragonResource(
                        new Identifier(parts.get(1)),
                        new Identifier(parts.get(2)),
                        new Identifier(parts.get(3)),
                        toRGBList(getParts(parts.get(4), ',', false)),
                        toRGB(parts.get(5)),
                        toRGBList(getParts(parts.get(6), ',', false)),
                        toBoolean(parts.get(7)),
                        toBoolean(parts.get(8)),
                        toBoolean(parts.get(9)),
                        toBoolean(parts.get(10)),
                        toBoolean(parts.get(11)),
                        toRGB(parts.get(12)),
                        toRGB(parts.get(13)),
                        toRGB(parts.get(14))
                );
            } catch (Exception ignored) {
                return null;
            }
        }


        public DragonResource copy() {
            return new DragonResource(
                    new Identifier(this.id.getNamespace(), this.id.getPath()),
                    new Identifier(this.entityBreath.getNamespace(), this.entityBreath.getPath()),
                    new Identifier(this.entitySpike.getNamespace(), this.entitySpike.getPath()),
                    new ArrayList<>(this.bodyColours),
                    new RGBColour(this.eyeColour.r, this.eyeColour.g, this.eyeColour.b),
                    new ArrayList<>(this.creativeEggColours),
                    this.canBreatheInWater,
                    this.canFreeze,
                    this.hurtByWater,
                    this.fireImmune,
                    this.explosionImmuneWhenAwake,
                    new RGBColour(this.colourBricks.r, this.colourBricks.g, this.colourBricks.b),
                    new RGBColour(this.colourCracks.r, this.colourCracks.g, this.colourCracks.b),
                    new RGBColour(this.colourGlow.r, this.colourGlow.g, this.colourGlow.b)
            );
        }

    }

}
