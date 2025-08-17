package ing.boykiss.blocksmith.resourcepack;

import ing.boykiss.blocksmith.Blocksmith;
import lombok.Getter;
import net.kyori.adventure.resource.ResourcePackInfo;
import net.kyori.adventure.resource.ResourcePackRequest;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import team.unnamed.creative.BuiltResourcePack;
import team.unnamed.creative.base.Writable;
import team.unnamed.creative.metadata.pack.PackFormat;
import team.unnamed.creative.serialize.minecraft.MinecraftResourcePackWriter;
import team.unnamed.creative.server.ResourcePackServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

public class ResourcePack {
    private static final Writable ICON = Writable.resource(Blocksmith.class.getClassLoader(), "blocksmith_icon.png");

    private final ResourcePackServer packServer;
    @Getter
    private final ResourcePackInfo packInfo;

    @Getter
    private final UUID uuid;

    public ResourcePack(UUID uuid, InetSocketAddress host) {
        this.uuid = uuid;

        team.unnamed.creative.ResourcePack pack = team.unnamed.creative.ResourcePack.resourcePack();
        pack.packMeta(PackFormat.format(42), Component.text("Blocksmith Custom Server Resources"));
        pack.icon(ICON);

        /*other stuff*/

        BuiltResourcePack builtPack = buildPack(pack);
        packServer = createServer(builtPack, host).orElse(null);
        URI uri = URI.create(packServer != null ? "http://" + host.getHostString() + ":" + host.getPort() : "");
        packInfo = ResourcePackInfo.resourcePackInfo(uuid, uri, builtPack.hash());
    }

    private static BuiltResourcePack buildPack(team.unnamed.creative.ResourcePack pack) {
        return MinecraftResourcePackWriter.minecraft().build(pack);
    }

    private static Optional<ResourcePackServer> createServer(BuiltResourcePack builtPack, InetSocketAddress host) {
        try {
            return Optional.of(ResourcePackServer.server()
                    .address(host)
                    .pack(builtPack)
                    .build());
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<ResourcePackServer> getPackServer() {
        return Optional.ofNullable(packServer);
    }

    public void sendToPlayer(Player player, Component prompt, boolean required, boolean replace) {
        player.sendResourcePacks(ResourcePackRequest.resourcePackRequest().packs(getPackInfo()).prompt(prompt).required(required).replace(replace).build());
    }
}
