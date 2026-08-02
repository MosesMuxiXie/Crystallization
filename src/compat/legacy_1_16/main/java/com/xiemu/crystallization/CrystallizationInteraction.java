package com.xiemu.crystallization;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;

final class CrystallizationInteraction {
	private CrystallizationInteraction() {
	}

	static InteractionResult useIce(Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
		ItemStack stack = player.getItemInHand(hand);
		BlockPos pos = hitResult.getBlockPos();
		BlockState state = level.getBlockState(pos);

		if (stack.getItem() != Items.ICE || !canWaterlog(state)) {
			return InteractionResult.PASS;
		}

		if (level.isClientSide()) {
			return Crystallization.isClientEnabled()
					? InteractionResult.SUCCESS
					: InteractionResult.PASS;
		}

		if (!(player instanceof ServerPlayer)
				|| !Crystallization.isPlayerEnabled((ServerPlayer) player)
				|| player.isSpectator()
				|| !level.mayInteract(player, pos)
				|| !player.mayUseItemAt(pos, hitResult.getDirection(), stack)) {
			return InteractionResult.PASS;
		}

		ServerPlayer serverPlayer = (ServerPlayer) player;
		BlockState waterloggedState = state.setValue(BlockStateProperties.WATERLOGGED, true);
		if (!level.setBlock(pos, waterloggedState, 3)) {
			return InteractionResult.PASS;
		}

		if (!serverPlayer.isCreative()) {
			stack.shrink(1);
		}

		level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
		return InteractionResult.SUCCESS;
	}

	private static boolean canWaterlog(BlockState state) {
		return state.hasProperty(BlockStateProperties.WATERLOGGED)
				&& !state.getValue(BlockStateProperties.WATERLOGGED);
	}
}
