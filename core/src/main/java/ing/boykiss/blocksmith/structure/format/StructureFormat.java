package ing.boykiss.blocksmith.structure.format;

import ing.boykiss.blocksmith.structure.Structure;

import java.io.InputStream;
import java.io.OutputStream;

public interface StructureFormat {
    /**
     * This can either create a new instance or edit `this` and return it.
     */
    StructureFormat read(InputStream in);

    void write(OutputStream out);

    StructureFormat fromStructure(Structure structure);

    Structure toStructure();
}
