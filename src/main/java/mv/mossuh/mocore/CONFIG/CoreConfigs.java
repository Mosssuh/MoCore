package mv.mossuh.mocore.CONFIG;

import mv.mossuh.mocore.MoCore;

public class CoreConfigs {
    private static MoConfig textureHeadsData;

    public CoreConfigs(MoCore main) {
        textureHeadsData = new MoConfig("textureheadsdata.db", main, "datas");
    }

    public void configure() {
        textureHeadsData.registerConfig(false);
    }

    public static MoConfig getTextureHeadsData() { return textureHeadsData; }
}
