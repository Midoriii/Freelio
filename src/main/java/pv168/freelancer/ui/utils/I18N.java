package pv168.freelancer.ui.utils;

import java.util.ResourceBundle;

public final class I18N {

    private final ResourceBundle bundle;
    private final String prefix;

    public I18N(Class<?> clazz) {
        var packagePath = "i18n" + '/';
        bundle = ResourceBundle.getBundle(packagePath + "i18n");
        prefix = clazz.getSimpleName() + ".";
    }

    public String getString(String key) {
        return bundle.getString(prefix + key);
    }

    public String getButtonString(String key) { return bundle.getString("Button." + key); }

    public String getDialogString(String key) { return bundle.getString("Dialog." + key); }

    public <E extends Enum<E>> String getString(E key) {
        return bundle.getString(key.getClass().getSimpleName() + "." + key.name());
    }
}
