package net.totobirdcreations.dragonheart.entity.dragon.util;

import java.util.UUID;


public class UuidOp {

    public static int uuidToInt(UUID uuid) {
        return Integer.parseUnsignedInt(uuid.toString().split("-")[0], 16);
    }

}
