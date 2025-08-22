package ing.boykiss.blocksmith.structure.format;

public interface NBTStructureFormat extends StructureFormat {
    NBTStructureFormat fromSNBT(String snbt);

    String toSNBT();
}
