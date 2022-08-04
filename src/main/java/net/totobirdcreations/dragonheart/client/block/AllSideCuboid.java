package net.totobirdcreations.dragonheart.client.block;

import net.minecraft.block.BlockState;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonGriefedBlockEntity;


public class AllSideCuboid extends ModelPart.Cuboid {

    public AllSideCuboid(int u, int v, float x, float y, float z, float sizeX, float sizeY, float sizeZ, float extraX, float extraY, float extraZ, boolean mirror, float textureWidth, float textureHeight) {
        super(u, v, x, y, z, sizeX, sizeY, sizeZ, extraX, extraY, extraZ, mirror, textureWidth, textureHeight);
        float u0 = (float)u;
        float v0 = (float)v;
        float u1 = u0 + sizeX;
        float v1 = v0 + sizeY;
        for (ModelPart.Quad quad : this.sides) {
            for (int i = 0; i < quad.vertices.length; i++) {
                quad.vertices[i] = quad.vertices[i].remap(
                        i == 0 || i == 3 ? u1 : u,
                        i == 0 || i == 1 ? v : v1
                );
            }
        }
    }


    public void renderCuboid(MatrixStack.Entry entry, VertexConsumer consumer, int overlay, DragonGriefedBlockEntity entity) {
        World    world    = entity.getWorld();
        BlockPos center   = entity.getPos();
        Matrix4f position = entry.getPositionMatrix();
        Matrix3f normal   = entry.getNormalMatrix();
        for (ModelPart.Quad quad : this.sides) {
            Direction direction = Direction.fromVector(
                    Math.round(quad.direction.getX()),
                    Math.round(quad.direction.getY()),
                    Math.round(quad.direction.getZ())
            );
            // Cull covered sides.
            boolean shouldCull = this.shouldCull(world, center, direction);
            if (! shouldCull) {
                // Do whatever ModelPart$Cuboid does.
                Vec3f quadNormal     = quad.direction.copy();
                Vec3f quadNormalCopy = quadNormal.copy();
                quadNormal.transform(normal);
                for (ModelPart.Vertex vertex : quad.vertices) {
                    Vector4f vector = new Vector4f(
                            vertex.pos.getX() / 16.0F,
                            vertex.pos.getY() / 16.0F,
                            vertex.pos.getZ() / 16.0F,
                            1.0f
                    );
                    vector.transform(position);
                    consumer.vertex(
                            vector.getX(), vector.getY(), vector.getZ(),
                            1.0f, 1.0f, 1.0f, 1.0f,
                            vertex.u, vertex.v,
                            overlay,
                            this.getLight(world, center, direction),
                            quadNormalCopy.getX(), quadNormalCopy.getY(), quadNormalCopy.getZ()
                    );
                }
            }
        }
    }


    public boolean shouldCull(World world, BlockPos pos, Direction direction) {
        // Check if block in direction is solid full square.
        BlockState state = world.getBlockState(pos.add(direction.getVector()));
        return (
                //state.isSideSolidFullSquare(world, pos, direction.getOpposite()) &&
                state.isOpaqueFullCube(world, pos)
        );
    }


    public int getLight(World world, BlockPos pos, Direction direction) {
        // Get light at the block's side.
        return WorldRenderer.getLightmapCoordinates(world, pos.add(direction.getVector()));
    }

}
