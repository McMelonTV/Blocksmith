package ing.boykiss.testserver;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.item.ItemStack;

import java.util.Optional;

public class TestServer {
    public static void main(String[] args) {
        MinecraftServer server = MinecraftServer.init();

        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer instance = instanceManager.createInstanceContainer();

        instance.setChunkSupplier(LightingChunk::new);
//        instance.setGenerator(unit -> unit.modifier().fillHeight(0, 10, Block.STONE));
        instance.setGenerator(unit -> unit.modifier().fillHeight(0, 1, Blocks.DISPLAY.getBlock()));

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            Player player = event.getPlayer();
            event.setSpawningInstance(instance);
            player.setRespawnPoint(new Pos(0, 10, 0));

            player.setGameMode(GameMode.CREATIVE);

            Optional<ItemStack> optionalItemStack = Items.DIA.createStack(1);
            optionalItemStack.ifPresent(player.getInventory()::addItemStack);

            Pos p1 = new Pos(2, 10, 0);
            Pos p2 = new Pos(1, 10, 0);
            instance.setBlock(p1, Blocks.DEFAULT.getBlock());
            instance.setBlock(p2, Blocks.DISPLAY.getBlock());
        });

        server.start("127.0.0.1", 25565);
    }
}
