package ing.boykiss.blocksmith.structure.format;

import ing.boykiss.blocksmith.structure.Structure;

import java.io.InputStream;
import java.io.OutputStream;

public class VanillaStructure implements NBTStructureFormat {
    @Override
    public VanillaStructure fromSNBT(String snbt) {
        return null; // TODO
    }

    @Override
    public String toSNBT() {
        return ""; // TODO
    }

    @Override
    public VanillaStructure read(InputStream in) {
        return null; // TODO
    }

    @Override
    public void write(OutputStream out) {
        // TODO
    }

    @Override
    public VanillaStructure fromStructure(Structure structure) {
        return null; // TODO
    }

    @Override
    public Structure toStructure() {
        return null; // TODO
    }
}
