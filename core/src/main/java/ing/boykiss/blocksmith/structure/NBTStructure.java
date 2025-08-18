package ing.boykiss.blocksmith.structure;

public interface NBTStructure extends Structure {

    StructureData fromSNBT(String snbt);

    String toSNBT(StructureData structure);
}
