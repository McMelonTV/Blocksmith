package ing.boykiss.blocksmith.structure.format;

import ing.boykiss.blocksmith.structure.StructureData;

import java.io.InputStream;
import java.io.OutputStream;

public class VanillaStructure implements NBTStructure {
    @Override
    public StructureData fromSNBT(String snbt) {
        return null;
    }

    @Override
    public String toSNBT(StructureData structure) {
        return "";
    }

    @Override
    public StructureData load(InputStream in) {
        return null;
    }

    @Override
    public void write(StructureData structure, OutputStream out) {

    }
}
