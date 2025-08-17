package ing.boykiss.blocksmith.resourcepack;

import ing.boykiss.blocksmith.Blocksmith;
import lombok.Getter;
import net.kyori.adventure.resource.ResourcePackInfo;
import net.kyori.adventure.resource.ResourcePackRequest;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.Nullable;
import team.unnamed.creative.BuiltResourcePack;
import team.unnamed.creative.base.Writable;
import team.unnamed.creative.metadata.pack.PackFormat;
import team.unnamed.creative.serialize.minecraft.MinecraftResourcePackWriter;
import team.unnamed.creative.server.ResourcePackServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.UUID;

public class BlocksmithRP {
    private static final Writable ICON = Writable.resource(Blocksmith.class.getClassLoader(), "blocksmith_icon.png");

    private final ResourcePackServer packServer;
    @Getter
    private final ResourcePackInfo packInfo;

    @Getter
    private final UUID uuid;

    public BlocksmithRP(UUID uuid, InetSocketAddress host) {
        this.uuid = uuid;

        team.unnamed.creative.ResourcePack pack = team.unnamed.creative.ResourcePack.resourcePack();
        pack.packMeta(PackFormat.format(42), Component.text("Blocksmith Custom Server Resources"));
        pack.icon(ICON);

        /*other stuff*/

        BuiltResourcePack builtPack = MinecraftResourcePackWriter.minecraft().build(pack);

        packServer = createServer(builtPack, host);
        packInfo = ResourcePackInfo.resourcePackInfo(uuid, createURI(host), builtPack.hash());
    }

    private static @Nullable ResourcePackServer createServer(BuiltResourcePack builtPack, InetSocketAddress host) {
        try {
            return ResourcePackServer.server()
                    .address(host)
                    .pack(builtPack)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private URI createURI(InetSocketAddress host) {
        return URI.create(packServer != null ? "http://" + host.getHostString() + ":" + host.getPort() : "");
    }

    public void startServer() {
        if (packServer != null) packServer.start();
    }

    public void sendToPlayer(Player player, @Nullable Boolean required, @Nullable Boolean replace, @Nullable Component prompt) {
        ResourcePackRequest.Builder builder = createBaseRequest();
        if (required != null) builder.required(required);
        if (replace != null) builder.replace(replace);
        if (prompt != null) builder.prompt(prompt);
        player.sendResourcePacks(builder.build());
    }

    private ResourcePackRequest.Builder createBaseRequest() {
        return ResourcePackRequest.resourcePackRequest().packs(getPackInfo());
    }
}
