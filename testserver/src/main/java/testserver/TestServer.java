package testserver;

import ing.boykiss.blocksmith.resourcepack.BlocksmithResourcePack;
import net.kyori.adventure.resource.ResourcePackRequest;
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
import team.unnamed.creative.server.ResourcePackServer;

import java.util.Optional;
import java.util.UUID;

public class TestServer {
    public static void main(String[] args) {
        MinecraftServer server = MinecraftServer.init();
        BlocksmithResourcePack resourcePack = new BlocksmithResourcePack(UUID.randomUUID());

        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer instance = instanceManager.createInstanceContainer();

        instance.setChunkSupplier(LightingChunk::new);
//        instance.setGenerator(unit -> unit.modifier().fillHeight(0, 10, Block.STONE));
//        instance.setGenerator(unit -> unit.modifier().fillHeight(0, 1, Blocks.DISPLAY.getBlock()));
        instance.setGenerator(unit -> unit.modifier().fillHeight(0, 1, Blocks.SPAWNER.getBlock()));

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            Player player = event.getPlayer();
            player.sendResourcePacks(ResourcePackRequest.resourcePackRequest().packs(resourcePack.getPackInfo()).prompt(Component.text("You need to download a resource pack to see our custom content")).required(true).replace(true).build());

            event.setSpawningInstance(instance);
            player.setRespawnPoint(new Pos(0, 10, 0));

            player.setGameMode(GameMode.CREATIVE);

            Optional<ItemStack> optionalItemStack = Items.STICK.createStack(1);
            optionalItemStack.ifPresent(player.getInventory()::addItemStack);

            Pos p1 = new Pos(1, 10, 0);
            Pos p2 = new Pos(2, 10, 0);
            Pos p3 = new Pos(3, 10, 0);
            instance.setBlock(p1, Blocks.NORMAL.getBlock());
            instance.setBlock(p2, Blocks.DISPLAY.getBlock());
            instance.setBlock(p3, Blocks.SPAWNER.getBlock());
        });

        resourcePack.getPackServer().ifPresent(ResourcePackServer::start);
        server.start("127.0.0.1", 25565);
    }
}
