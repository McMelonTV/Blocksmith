package ing.boykiss.blocksmith.resourcepack;

import ing.boykiss.blocksmith.Blocksmith;
import lombok.Getter;
import net.kyori.adventure.resource.ResourcePackInfo;
import net.kyori.adventure.text.Component;
import team.unnamed.creative.BuiltResourcePack;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.base.Writable;
import team.unnamed.creative.metadata.pack.PackFormat;
import team.unnamed.creative.serialize.minecraft.MinecraftResourcePackWriter;
import team.unnamed.creative.server.ResourcePackServer;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

public class BlocksmithResourcePack {
    private static final Writable ICON = Writable.resource(Blocksmith.class.getClassLoader(), "blocksmith_icon.png");

    private final ResourcePackServer packServer;
    @Getter
    private final ResourcePackInfo packInfo;

    @Getter
    private final UUID uuid;

    public BlocksmithResourcePack(UUID uuid) {
        this.uuid = uuid;

        ResourcePack pack = ResourcePack.resourcePack();
        pack.packMeta(PackFormat.format(42), Component.text("Blocksmith Custom Server Resources"));
        pack.icon(ICON);

        /*other stuff*/

        BuiltResourcePack builtPack = buildPack(pack);
        packServer = createServer(builtPack).orElse(null);
        URI uri = URI.create(packServer != null ? "http://" + packServer.address().getHostString() + ":" + packServer.address().getPort() : "");
        packInfo = ResourcePackInfo.resourcePackInfo(uuid, uri, builtPack.hash());
    }

    private static BuiltResourcePack buildPack(ResourcePack pack) {
        return MinecraftResourcePackWriter.minecraft().build(pack);
    }

    private static Optional<ResourcePackServer> createServer(BuiltResourcePack builtPack) {
        try {
            return Optional.of(ResourcePackServer.server()
                    .address("127.0.0.1", 8888)
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
}
