package com.reogent.reports.Config;

public enum Main {
    LANG("lang", "RU"),
    COOLDOWN("cooldown", 20),
    SELF_REPORTS("self_reports", false),
    TELEPORT_BACK("back_teleportation", true)
    ;

    private String path;
    private Object def;

    Main(String path, Object value) {
        this.path = path;
        this.def = value;
    }

    public Object getDefault()
    {
        return this.def;
    }

    public String getPath()
    {
        return this.path;
    }
}

