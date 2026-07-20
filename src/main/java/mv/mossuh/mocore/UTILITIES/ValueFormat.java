package mv.mossuh.mocore.UTILITIES;

public class ValueFormat {
    private String thousand = "";
    private String million = "";
    private String billion = "";
    private String trillion = "";
    private String quadrillion = "";
    private String quintillion = "";
    private String sextillion = "";
    private String septillion = "";
    private String octillion = "";
    private String nonillion = "";
    public ValueFormat(String thousand, String million, String billion, String trillion, String quadrillion,
                       String quintillion,  String sextillion, String septillion, String octillion, String nonillion) {
        if (thousand != null) { this.thousand = UsefulString.get(thousand).hex().apply(); }
        if (million != null) { this.million = UsefulString.get(million).hex().apply(); }
        if (billion != null) { this.billion = UsefulString.get(billion).hex().apply(); }
        if (trillion != null) { this.trillion = UsefulString.get(trillion).hex().apply(); }
        if (quadrillion != null) { this.quadrillion = UsefulString.get(quadrillion).hex().apply(); }
        if (quintillion != null) { this.quintillion = UsefulString.get(quintillion).hex().apply(); }
        if (sextillion != null) { this.sextillion = UsefulString.get(sextillion).hex().apply(); }
        if (septillion != null) { this.septillion = UsefulString.get(septillion).hex().apply(); }
        if (octillion != null) { this.octillion = UsefulString.get(octillion).hex().apply(); }
        if (nonillion != null) { this.nonillion = UsefulString.get(nonillion).hex().apply(); }
    }

    public String getThousand() { return thousand; }
    public String getMillion() { return million; }
    public String getBillion() { return billion; };
    public String getTrillion() { return trillion; }
    public String getQuadrillion() { return quadrillion; }
    public String getQuintillion() { return quintillion; }
    public String getSextillion() { return sextillion; }
    public String getSeptillion() { return septillion; }
    public String getOctillion() { return octillion; }
    public String getNonillion() { return nonillion; }
}
