package net.frozenblock.wilderwild.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class MesogleaDripParticle extends TextureSheetParticle {

    MesogleaDripParticle(ClientLevel clientLevel, double d, double e, double f) {
        super(clientLevel, d, e, f);
        this.setSize(0.5f, 0.5f);
        this.gravity = 0.06f;
        this.quadSize = 0.5f;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public int getLightColor(float f) {
        return 240;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.preMoveUpdate();
        if (this.removed) {
            return;
        }
        this.yd -= this.gravity;
        this.move(this.xd, this.yd, this.zd);
        this.postMoveUpdate();
        if (this.removed) {
            return;
        }
        this.xd *= 0.98f;
        this.yd *= 0.98f;
        this.zd *= 0.98f;
        BlockPos blockPos = new BlockPos(this.x, this.y, this.z);
        FluidState fluidState = this.level.getFluidState(blockPos);
        if (fluidState.getType() == Fluids.WATER && this.y < (double) ((float) blockPos.getY() + fluidState.getHeight(this.level, blockPos))) {
            this.remove();
        }
    }

    protected void preMoveUpdate() {
        if (this.lifetime-- <= 0) {
            this.remove();
        }
    }

    protected void postMoveUpdate() {
    }

    //Blue Pearlescent
    @Environment(value = EnvType.CLIENT)
    public static class BPMesogleaFallProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public BPMesogleaFallProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            MesogleaDripParticle.FallAndLandParticle dripParticle = new MesogleaDripParticle.FallAndLandParticle(clientLevel, d, e, f, RegisterParticles.BLUE_PEARLESCENT_LANDING_MESOGLEA);
            dripParticle.pickSprite(this.sprite);
            return dripParticle;
        }
    }

    @Environment(value = EnvType.CLIENT)
    public static class BPMesogleaHangProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public BPMesogleaHangProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            return new DripHangParticle(clientLevel, d, e, f, RegisterParticles.BLUE_PEARLESCENT_FALLING_MESOGLEA, sprite);
        }
    }

    @Environment(value = EnvType.CLIENT)
    public static class BPMesogleaLandProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public BPMesogleaLandProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            MesogleaDripParticle.DripLandParticle dripParticle = new MesogleaDripParticle.DripLandParticle(clientLevel, d, e, f);
            dripParticle.pickSprite(this.sprite);
            return dripParticle;
        }
    }

    //Purple Pearlescent
    @Environment(value = EnvType.CLIENT)
    public static class PPMesogleaFallProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public PPMesogleaFallProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            MesogleaDripParticle.FallAndLandParticle dripParticle = new MesogleaDripParticle.FallAndLandParticle(clientLevel, d, e, f, RegisterParticles.PURPLE_PEARLESCENT_LANDING_MESOGLEA);
            dripParticle.pickSprite(this.sprite);
            return dripParticle;
        }
    }

    @Environment(value = EnvType.CLIENT)
    public static class PPMesogleaHangProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public PPMesogleaHangProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            return new DripHangParticle(clientLevel, d, e, f, RegisterParticles.PURPLE_PEARLESCENT_FALLING_MESOGLEA, sprite);
        }
    }

    @Environment(value = EnvType.CLIENT)
    public static class PPMesogleaLandProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public PPMesogleaLandProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            MesogleaDripParticle.DripLandParticle dripParticle = new MesogleaDripParticle.DripLandParticle(clientLevel, d, e, f);
            dripParticle.pickSprite(this.sprite);
            return dripParticle;
        }
    }

    //Blue
    @Environment(value = EnvType.CLIENT)
    public static class BMesogleaFallProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public BMesogleaFallProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            MesogleaDripParticle.FallAndLandParticle dripParticle = new MesogleaDripParticle.FallAndLandParticle(clientLevel, d, e, f, RegisterParticles.BLUE_LANDING_MESOGLEA);
            dripParticle.pickSprite(this.sprite);
            return dripParticle;
        }
    }

    @Environment(value = EnvType.CLIENT)
    public static class BMesogleaHangProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public BMesogleaHangProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            return new DripHangParticle(clientLevel, d, e, f, RegisterParticles.BLUE_FALLING_MESOGLEA, sprite);
        }
    }

    @Environment(value = EnvType.CLIENT)
    public static class BMesogleaLandProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public BMesogleaLandProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            MesogleaDripParticle.DripLandParticle dripParticle = new MesogleaDripParticle.DripLandParticle(clientLevel, d, e, f);
            dripParticle.pickSprite(this.sprite);
            return dripParticle;
        }
    }

    //Yellow
    @Environment(value = EnvType.CLIENT)
    public static class YMesogleaFallProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public YMesogleaFallProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            MesogleaDripParticle.FallAndLandParticle dripParticle = new MesogleaDripParticle.FallAndLandParticle(clientLevel, d, e, f, RegisterParticles.YELLOW_LANDING_MESOGLEA);
            dripParticle.pickSprite(this.sprite);
            return dripParticle;
        }
    }

    @Environment(value = EnvType.CLIENT)
    public static class YMesogleaHangProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public YMesogleaHangProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            MesogleaDripParticle.DripHangParticle dripParticle = new MesogleaDripParticle.DripHangParticle(clientLevel, d, e, f, RegisterParticles.YELLOW_FALLING_MESOGLEA, sprite);
            return dripParticle;
        }
    }

    @Environment(value = EnvType.CLIENT)
    public static class YMesogleaLandProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public YMesogleaLandProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            MesogleaDripParticle.DripLandParticle dripParticle = new MesogleaDripParticle.DripLandParticle(clientLevel, d, e, f);
            dripParticle.pickSprite(this.sprite);
            return dripParticle;
        }
    }

    //Lime
    @Environment(value = EnvType.CLIENT)
    public static class LMesogleaFallProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public LMesogleaFallProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            MesogleaDripParticle.FallAndLandParticle dripParticle = new MesogleaDripParticle.FallAndLandParticle(clientLevel, d, e, f, RegisterParticles.LIME_LANDING_MESOGLEA);
            dripParticle.pickSprite(this.sprite);
            return dripParticle;
        }
    }

    @Environment(value = EnvType.CLIENT)
    public static class LMesogleaHangProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public LMesogleaHangProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            MesogleaDripParticle.DripHangParticle dripParticle = new MesogleaDripParticle.DripHangParticle(clientLevel, d, e, f, RegisterParticles.LIME_FALLING_MESOGLEA, sprite);
            return dripParticle;
        }
    }

    @Environment(value = EnvType.CLIENT)
    public static class LMesogleaLandProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public LMesogleaLandProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            MesogleaDripParticle.DripLandParticle dripParticle = new MesogleaDripParticle.DripLandParticle(clientLevel, d, e, f);
            dripParticle.pickSprite(this.sprite);
            return dripParticle;
        }
    }

    //Red
    @Environment(value = EnvType.CLIENT)
    public static class RMesogleaFallProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public RMesogleaFallProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            MesogleaDripParticle.FallAndLandParticle dripParticle = new MesogleaDripParticle.FallAndLandParticle(clientLevel, d, e, f, RegisterParticles.RED_HANGING_MESOGLEA);
            dripParticle.pickSprite(this.sprite);
            return dripParticle;
        }
    }

    @Environment(value = EnvType.CLIENT)
    public static class RMesogleaHangProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public RMesogleaHangProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            return new DripHangParticle(clientLevel, d, e, f, RegisterParticles.RED_FALLING_MESOGLEA, sprite);
        }
    }

    @Environment(value = EnvType.CLIENT)
    public static class RMesogleaLandProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public RMesogleaLandProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            MesogleaDripParticle.DripLandParticle dripParticle = new MesogleaDripParticle.DripLandParticle(clientLevel, d, e, f);
            dripParticle.pickSprite(this.sprite);
            return dripParticle;
        }
    }

    //Pink
    @Environment(value = EnvType.CLIENT)
    public static class PMesogleaFallProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public PMesogleaFallProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            MesogleaDripParticle.FallAndLandParticle dripParticle = new MesogleaDripParticle.FallAndLandParticle(clientLevel, d, e, f, RegisterParticles.PINK_LANDING_MESOGLEA);
            dripParticle.pickSprite(this.sprite);
            return dripParticle;
        }
    }

    @Environment(value = EnvType.CLIENT)
    public static class PMesogleaHangProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public PMesogleaHangProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            return new DripHangParticle(clientLevel, d, e, f, RegisterParticles.PINK_FALLING_MESOGLEA, sprite);
        }
    }

    @Environment(value = EnvType.CLIENT)
    public static class PMesogleaLandProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public PMesogleaLandProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            MesogleaDripParticle.DripLandParticle dripParticle = new MesogleaDripParticle.DripLandParticle(clientLevel, d, e, f);
            dripParticle.pickSprite(this.sprite);
            return dripParticle;
        }
    }


    @Environment(value = EnvType.CLIENT)
    static class DripLandParticle extends MesogleaDripParticle {
        DripLandParticle(ClientLevel clientLevel, double d, double e, double f) {
            super(clientLevel, d, e, f);
            this.lifetime = (int) (16.0 / (Math.random() * 0.8 + 0.2));
            this.scale(0.7F);
        }
    }

    @Environment(value = EnvType.CLIENT)
    static class FallingParticle extends MesogleaDripParticle {
        FallingParticle(ClientLevel clientLevel, double d, double e, double f) {
            this(clientLevel, d, e, f, (int) (64.0 / (Math.random() * 0.8 + 0.2)));
            this.scale(0.7F);
        }

        FallingParticle(ClientLevel clientLevel, double d, double e, double f, int i) {
            super(clientLevel, d, e, f);
            this.lifetime = i;
        }

        @Override
        protected void postMoveUpdate() {
            if (this.onGround) {
                this.remove();
            }
        }
    }

    @Environment(value = EnvType.CLIENT)
    static class FallAndLandParticle extends MesogleaDripParticle.FallingParticle {
        protected final ParticleOptions landParticle;

        FallAndLandParticle(ClientLevel clientLevel, double d, double e, double f, ParticleOptions particleOptions) {
            super(clientLevel, d, e, f);
            this.landParticle = particleOptions;
        }

        @Override
        protected void postMoveUpdate() {
            if (this.onGround) {
                this.remove();
                this.level.addParticle(this.landParticle, this.x, this.y, this.z, 0.0, 0.0, 0.0);
                SoundEvent soundEvent = RegisterSounds.PARTICLE_MESOGLEA_DRIP_LAND;
                float f = Mth.randomBetween(this.random, 0.3F, 1.0F);
                this.level.playLocalSound(this.x, this.y, this.z, soundEvent, SoundSource.BLOCKS, f, 1.0F, false);
            }
        }
    }

    @Environment(value = EnvType.CLIENT)
    static class DripHangParticle extends MesogleaDripParticle {
        private final ParticleOptions fallingParticle;
        private final SpriteSet spriteSet;

        DripHangParticle(ClientLevel clientLevel, double d, double e, double f, ParticleOptions particleOptions, SpriteSet spriteSet) {
            super(clientLevel, d, e - 0.1, f);
            this.fallingParticle = particleOptions;
            this.gravity *= 0.00f;
            this.lifetime = 40;
            this.spriteSet = spriteSet;
            this.setSpriteFromAge(this.spriteSet);
            this.scale(0.7F);
        }

        @Override
        protected void preMoveUpdate() {
            if (this.lifetime-- <= 0) {
                this.remove();
                this.level.addParticle(this.fallingParticle, this.x, this.y, this.z, this.xd, this.yd, this.zd);
            }
        }

        @Override
        protected void postMoveUpdate() {
            if (!this.removed) {
                this.setSprite(spriteSet.get((int) (this.age * 0.2) + 1, (int) (this.lifetime * 0.2) + 1));
            }
        }
    }

}
