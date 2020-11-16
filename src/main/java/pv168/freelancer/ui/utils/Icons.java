package pv168.freelancer.ui.utils;



public final class Icons {

    public static final CustomIcon MINIMIZE_ICON = createIcon("/minimize.png", 24, 24);
    public static final CustomIcon QUIT_ICON = createIcon("/quit.png",24, 24);

    private Icons() {
        throw new AssertionError("This class is not instantiable");
    }

    private static CustomIcon createIcon(String name, int width, int height) {
        return new CustomIcon(name, width, height);
    }
}
