package ing.boykiss.blocksmith.structure;

import java.io.InputStream;
import java.io.OutputStream;

public interface Structure {
    StructureData load(InputStream in);

    void write(StructureData structure, OutputStream out);
}
