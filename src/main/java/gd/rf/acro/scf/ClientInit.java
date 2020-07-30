package gd.rf.acro.scf;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;

public class ClientInit implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientSidePacketRegistry.INSTANCE.register(SCF.SEND_SOUND,
                (packetContext, attachedData) -> {
                    if(attachedData.readInt()==1)
                    {
                        packetContext.getPlayer().playSound(SoundEvents.BLOCK_LAVA_EXTINGUISH,1,1);
                    }
                });
    }
}
