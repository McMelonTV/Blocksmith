package ing.boykiss.blocksmith.structure.format;

import ing.boykiss.blocksmith.structure.StructureData;

public interface NBTStructure extends Structure {

    StructureData fromSNBT(String snbt);

    String toSNBT(StructureData structure);
}
