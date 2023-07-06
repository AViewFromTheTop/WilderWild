/*
 * Copyright 2022-2023 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.mixin.worldgen;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.structures.SwampHutPiece;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;


@Mixin(value = SwampHutPiece.class, priority = 999)
public class SwampHutPieceMixin {

	@ModifyExpressionValue(method = "postProcess", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/Blocks;SPRUCE_PLANKS:Lnet/minecraft/world/level/block/Block;"))
	public Block wilderWild$newPlanks(Block original) {
		if (WilderSharedConstants.config().newWitchHuts()) {
			return RegisterBlocks.CYPRESS_PLANKS;
		}
		return original;
	}

	@ModifyExpressionValue(method = "postProcess", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/Blocks;SPRUCE_STAIRS:Lnet/minecraft/world/level/block/Block;"))
	public Block wilderWild$newStairs(Block original) {
		if (WilderSharedConstants.config().newWitchHuts()) {
			return RegisterBlocks.CYPRESS_STAIRS;
		}
		return original;
	}

}