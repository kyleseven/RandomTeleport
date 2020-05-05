package me.kyleseven.randomteleport.config;

public class MsgConfig extends ConfigLoader {
    private static MsgConfig msgConfig;

    public MsgConfig() {
        super("messages.yml");
    }

    public static MsgConfig getInstance() {
        if (msgConfig == null) {
            msgConfig = new MsgConfig();
        }
        return msgConfig;
    }

    public static void reload() {
        getInstance().loadFile();
    }

    /*
    Config keys
     */

    public String getPrefix() {
        return config.getString("prefix");
    }

    public String getNotification(double meters) {
        return config.getString("notification").replaceAll("%METERS", String.format("%.2f", meters));
    }

    public String getCooldown(int seconds) {
        return config.getString("cooldown").replaceAll("%SECONDS", String.valueOf(seconds));
    }

    public String getReload() {
        return config.getString("reload");
    }

    public String getInvalid() {
        return config.getString("invalid");
    }
}
