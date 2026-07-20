package mv.mossuh.mocore.UTILITIES.REQUIREMENTS;

import java.util.ArrayList;
import java.util.List;

public class EntityRequirement {

    private String entity = "ALL";
    private Byte data = -1;
    public EntityRequirement(String entity, Byte data) {
        if (entity != null) { this.entity = entity; }
        if (data != null) { this.data = data; }
    }

    public String getEntity() { return entity; }
    public byte getEntityData() { return data; }


    public static List<EntityRequirement> stringToEntities(String entities) {
        List<EntityRequirement> entitiesList = new ArrayList<>();

        String[] entitiesSplit = entities.split(" \\|\\| ");

        if (entitiesSplit.length >= 1) {
            for (String e : entitiesSplit) {
                String[] entitySplit = e.split(":", 2);
                String entity = entitySplit[0].replaceAll(" ", "");
                byte data = -1;
                if (entitySplit.length == 2) {
                    data = Byte.parseByte(entitySplit[1].replaceAll(" ", ""));
                }

                EntityRequirement entityRequirement = new EntityRequirement(entity, data);
                entitiesList.add(entityRequirement);
            }
        }
        return entitiesList;
    }

    public static boolean containsEntity(List<EntityRequirement> entityRequirements, String entity, String data) {
        for (EntityRequirement entityExp : entityRequirements) {
            String entityRequirement = entityExp.getEntity();
            String entityData = entityExp.getEntityData()+"";
            if (entityRequirement.equalsIgnoreCase("ALL")) {
                return true;
            } else if (entityRequirement.equalsIgnoreCase(entity)) {
                if (!entityData.equals("-1")) {
                    if (entityData.equals(data)) {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }
        return false;
    }
}
