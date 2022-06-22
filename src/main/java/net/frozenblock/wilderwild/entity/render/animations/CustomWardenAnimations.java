package net.frozenblock.wilderwild.entity.render.animations;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

/**
 * put custom warden animations in here. for example, death, flying, swimming. not for overrides
 */
@Environment(EnvType.CLIENT)
public class CustomWardenAnimations {

    public static final Animation DYING = Animation.Builder.create(3.5F)
            .addBoneAnimation(
                    "body",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.52F, AnimationHelper.method_41829(-12.59F, 0F, 6.67F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.2F, AnimationHelper.method_41829(-42.5F, 0.0F, 22.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.64F, AnimationHelper.method_41829(-42.7453F, -5.5223F, 27.5827F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "body",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.36F, AnimationHelper.method_41823(5.7F, -11.93F, 4.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.44F, AnimationHelper.method_41823(12.0F, -56F, 11.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.44F, AnimationHelper.method_41823(12.0F, -70F, 8.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.64F, AnimationHelper.method_41823(12.0F, -70F, 8.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.84F, AnimationHelper.method_41823(12.0F, -52.0F, 8.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.95F, AnimationHelper.method_41823(12.0F, -91.58F, 8.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.09F, AnimationHelper.method_41823(12.0F, -139.03F, 8.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.198F, AnimationHelper.method_41823(12.0F, -206.77F, 8.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.5F, AnimationHelper.method_41823(12.0F, -500F, 8.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "head",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.52F, AnimationHelper.method_41829(9.5459F, 2.9932F, -17.25F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.32F, AnimationHelper.method_41829(-17.5093F, -0.434F, 2.4621F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.64F, AnimationHelper.method_41829(-17.5093F, -0.434F, 2.4621F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "head",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.64F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37884)
                    )
            )
            .addBoneAnimation(
                    "right_arm",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.48F, AnimationHelper.method_41829(-52.5F, -0F, 50F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.96F, AnimationHelper.method_41829(-59.5544F, 33.4855F, 67.9672F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.68F, AnimationHelper.method_41829(-73.7812F, -48.0134F, 72.9396F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.64F, AnimationHelper.method_41829(-58.1913F, -52.867F, 72.31F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "right_arm",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.48F, AnimationHelper.method_41823(1F, 0.0F, 2F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.12F, AnimationHelper.method_41823(2F, -1F, 1F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.36F, AnimationHelper.method_41823(0.0F, 3.0F, 2.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.64F, AnimationHelper.method_41823(0.0F, 4.0F, 2.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "left_arm",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.2F, AnimationHelper.method_41829(0, 0, -12.5F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.56F, AnimationHelper.method_41829(-17.1773F, -40.7763F, -47.1799F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.0F, AnimationHelper.method_41829(-24.0177F, 6.4316F, -116.2232F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.64F, AnimationHelper.method_41829(20.6913F, 10.8284F, -137.6989F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "left_arm",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.88F, AnimationHelper.method_41823(-3.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.64F, AnimationHelper.method_41823(-3.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "right_leg",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.16F, AnimationHelper.method_41829(-20.2134F, 4.3897F, 7.6436F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.36F, AnimationHelper.method_41829(-48.42F, 5.21F, -0.33F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.64F, AnimationHelper.method_41829(-133.8547F, 12.8091F, -12.025F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "right_leg",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.36F, AnimationHelper.method_41823(4.78F, -6.87F, -0.42F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.84F, AnimationHelper.method_41823(8.0F, -23F, -2F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.4356F, AnimationHelper.method_41823(8.0F, -44.8F, -2.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.8812F, AnimationHelper.method_41823(8.0F, -52.54F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.4455F, AnimationHelper.method_41823(8.0F, -60.0F, -2.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.64F, AnimationHelper.method_41823(8.0F, -60.0F, -2.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.84F, AnimationHelper.method_41823(8.0F, -42.0F, -2.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.95F, AnimationHelper.method_41823(8.0F, -81.58F, -2.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.198F, AnimationHelper.method_41823(8.0F, -196.77F, -2.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.5F, AnimationHelper.method_41823(8.0F, -490.0F, -2.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "left_leg",
                    new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F, AnimationHelper.method_41829(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.36F, AnimationHelper.method_41829(-12.4952F, -9.8466F, -5.9812F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.88F, AnimationHelper.method_41829(-41.1768F, -7.7721F, 0.4528F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.1584F, AnimationHelper.method_41829(-59.4054F, -17.4853F, -6.5109F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.1905F, AnimationHelper.method_41829(-87.61F, -23.3F, -7.95F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.64F, AnimationHelper.method_41829(-128.1353F, -32.1013F, 8.7832F), Transformation.Interpolations.field_37885)
                    )
            )
            .addBoneAnimation(
                    "left_leg",
                    new Transformation(
                            Transformation.Targets.TRANSLATE,
                            new Keyframe(0.0F, AnimationHelper.method_41823(0.0F, 0.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.4059F, AnimationHelper.method_41823(3.73F, -12.3F, 2.24F), Transformation.Interpolations.field_37885),
                            new Keyframe(0.8317F, AnimationHelper.method_41823(5.64F, -25.2F, -2.42F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.18F, AnimationHelper.method_41823(6.0F, -37.2F, -4.8F), Transformation.Interpolations.field_37885),
                            new Keyframe(1.4455F, AnimationHelper.method_41823(7.0F, -45.37F, -4.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.099F, AnimationHelper.method_41823(8.0F, -57.0F, -2.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.64F, AnimationHelper.method_41823(8.0F, -65.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.84F, AnimationHelper.method_41823(8.0F, -47.0F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(2.95F, AnimationHelper.method_41823(8.0F, -86.58F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.09F, AnimationHelper.method_41823(8.0F, -134.03F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.198F, AnimationHelper.method_41823(8.0F, -201.77F, 0.0F), Transformation.Interpolations.field_37885),
                            new Keyframe(3.5F, AnimationHelper.method_41823(8.0F, -495.0F, 0.0F), Transformation.Interpolations.field_37885)
                    )
            )
            .build();
}
