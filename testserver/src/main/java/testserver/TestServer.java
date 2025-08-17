package testserver;

import ing.boykiss.blocksmith.Blocksmith;
import ing.boykiss.blocksmith.resourcepack.BlocksmithRP;
import net.kyori.adventure.text.Component;
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

import java.net.InetSocketAddress;
import java.util.UUID;

public class TestServer {
    public static Blocksmith BLOCKSMITH = new Blocksmith(UUID.randomUUID(), new InetSocketAddress("127.0.0.1", 8888));
    public static BlocksmithRP BLOCKSMITH_RP = BLOCKSMITH.getResourcePack();

    public static void main(String[] args) {
        MinecraftServer server = MinecraftServer.init();

        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer instance = instanceManager.createInstanceContainer();

        instance.setChunkSupplier(LightingChunk::new);
//        instance.setGenerator(unit -> unit.modifier().fillHeight(0, 10, Block.STONE));
//        instance.setGenerator(unit -> unit.modifier().fillHeight(0, 1, Blocks.DISPLAY.getBlock()));
        instance.setGenerator(unit -> unit.modifier().fillHeight(0, 1, Blocks.SPAWNER.getBlock()));

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            Player player = event.getPlayer();
            BLOCKSMITH_RP.sendToPlayer(player, true, true, Component.text("You need to download a resource pack to see our custom content"));

            event.setSpawningInstance(instance);
            player.setRespawnPoint(new Pos(0, 10, 0));

            player.setGameMode(GameMode.CREATIVE);

            ItemStack itemStack = Items.STICK.createStack(1);
            player.getInventory().addItemStack(itemStack);

            Pos p1 = new Pos(1, 10, 0);
            Pos p2 = new Pos(2, 10, 0);
            Pos p3 = new Pos(3, 10, 0);
            instance.setBlock(p1, Blocks.NORMAL.getBlock());
            instance.setBlock(p2, Blocks.DISPLAY.getBlock());
            instance.setBlock(p3, Blocks.SPAWNER.getBlock());
        });

        BLOCKSMITH_RP.startServer();
        server.start("127.0.0.1", 25565);
    }
}
