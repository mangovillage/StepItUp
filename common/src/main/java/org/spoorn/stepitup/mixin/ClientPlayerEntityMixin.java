package org.spoorn.stepitup.mixin;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spoorn.stepitup.StepItUp;
import org.spoorn.stepitup.config.ModConfig;

@Mixin(LocalPlayer.class)
public abstract class ClientPlayerEntityMixin {

    private static final Identifier STEP_UP_MODIFIER_ID = Identifier.fromNamespaceAndPath(StepItUp.MOD_ID, "step_up");
    private static final double STEP_UP_HEIGHT = 1.25;

    @Shadow public abstract boolean isShiftKeyDown();

    // Step up, should apply before autojump thereby "disabling" autojump
    @Inject(method = "move", at = @At(value = "HEAD"))
    private void enableStepUp(MoverType movementType, Vec3 movement, CallbackInfo ci) {
        LivingEntity thisEntity = ((LivingEntity) (Object) this);
        AttributeInstance stepHeightAttribute = thisEntity.getAttribute(Attributes.STEP_HEIGHT);
        if (stepHeightAttribute == null) {
            return;
        }

        stepHeightAttribute.removeModifier(STEP_UP_MODIFIER_ID);
        if (ModConfig.get().enableStepUp && (!this.isShiftKeyDown() || ModConfig.get().enableStepUpWhenSneaking)) {
            stepHeightAttribute.addOrUpdateTransientModifier(new AttributeModifier(
                    STEP_UP_MODIFIER_ID,
                    STEP_UP_HEIGHT - stepHeightAttribute.getValue(),
                    AttributeModifier.Operation.ADD_VALUE
            ));
        }
    }

    @Inject(method = "canAutoJump", at = @At(value = "HEAD"), cancellable = true)
    private void disableAutoJump(CallbackInfoReturnable<Boolean> cir) {
        if (ModConfig.get().enableStepUp && (!this.isShiftKeyDown() || ModConfig.get().enableStepUpWhenSneaking)) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
