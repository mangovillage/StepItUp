package org.spoorn.stepitup.neoforge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import org.spoorn.stepitup.StepItUp;

@Mod(value = StepItUp.MOD_ID, dist = Dist.CLIENT)
public class StepItUpNeoForge {

    public StepItUpNeoForge() {
        StepItUp.init();
    }
}
