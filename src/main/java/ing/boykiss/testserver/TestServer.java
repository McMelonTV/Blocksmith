package ing.boykiss.testserver;

import ing.boykiss.blocksmith.block.BlocksmithBlock;
import net.kyori.adventure.key.Key;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.ItemStack;

import java.util.Optional;

public class TestServer {
    public static void main(String[] args) {
        MinecraftServer server = MinecraftServer.init();

        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer instance = instanceManager.createInstanceContainer();

        instance.setChunkSupplier(LightingChunk::new);
        instance.setGenerator(unit -> unit.modifier().fillHeight(0, 10, Block.STONE));

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            Player player = event.getPlayer();
            event.setSpawningInstance(instance);
            player.setRespawnPoint(new Pos(0, 10, 0));

            player.setGameMode(GameMode.CREATIVE);

            Optional<ItemStack> optionalItemStack = Items.DIA.createStack(1);
            optionalItemStack.ifPresent(player.getInventory()::addItemStack);

            Pos zero = new Pos(0, 0, 0);
            Pos one = new Pos(1, 1, 1);
            instance.setBlock(zero, Block.AIR.withTag(BlocksmithBlock.BLOCKSMITH_BLOCK_ID_TAG, Key.key("ns", "test")));
            instance.setBlock(one, Block.AIR);
            Key keyZ = instance.getBlock(zero).getTag(BlocksmithBlock.BLOCKSMITH_BLOCK_ID_TAG);
            Key keyO = instance.getBlock(one).getTag(BlocksmithBlock.BLOCKSMITH_BLOCK_ID_TAG);
            System.out.println(keyZ == null ? "null" : keyZ.asString());
            System.out.println(keyO == null ? "null" : keyO.asString());
        });

        server.start("127.0.0.1", 25565);
    }
}
