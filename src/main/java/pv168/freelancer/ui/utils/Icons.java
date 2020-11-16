package pv168.freelancer.ui.utils;



public final class Icons {

    public static final CustomIcon MINIMIZE_ICON = createIcon("/minimize.png");
    public static final CustomIcon QUIT_ICON = createIcon("/quit.png");

    private Icons() {
        throw new AssertionError("This class is not instantiable");
    }

    private static CustomIcon createIcon(String name) {
        return new CustomIcon(name);
    }
}
