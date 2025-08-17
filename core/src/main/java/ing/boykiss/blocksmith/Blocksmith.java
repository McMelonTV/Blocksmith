package ing.boykiss.blocksmith;

import ing.boykiss.blocksmith.block.BlockRegistry;
import ing.boykiss.blocksmith.item.ItemRegistry;
import ing.boykiss.blocksmith.resourcepack.BlocksmithRP;
import lombok.Getter;

import java.net.InetSocketAddress;
import java.util.UUID;

public class Blocksmith {
    @Getter
    private final ItemRegistry itemRegistry = new ItemRegistry();
    @Getter
    private final BlockRegistry blockRegistry = new BlockRegistry();

    @Getter
    private final BlocksmithRP resourcePack;

    public Blocksmith(UUID rpUuid, InetSocketAddress rpHost) {
        this.resourcePack = new BlocksmithRP(rpUuid, rpHost);
    }
}