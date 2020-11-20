package pv168.freelancer.ui.utils;



public final class Icons {

    public static final CustomIcon MINIMIZE_ICON = createIcon("/minus.png", 24, 24);
    public static final CustomIcon QUIT_ICON = createIcon("/quit.png",24, 24);

    public static final CustomIcon ADD_ICON = createIcon("/add.png",24, 24);
    public static final CustomIcon EDIT_ICON = createIcon("/edit.png",24, 24);
    public static final CustomIcon DELETE_ICON = createIcon("/minus_invert.png",24, 24);
    public static final CustomIcon ADD_ICON_S = createIcon("/add.png",16, 16);
    public static final CustomIcon EDIT_ICON_S = createIcon("/edit.png",16, 16);
    public static final CustomIcon DELETE_ICON_S = createIcon("/minus_invert.png",16, 16);
    public static final CustomIcon CONFIRM_ICON = createIcon("/confirm.png",24, 24);

    public static final CustomIcon INVOICE_ICON = createIcon("/invoice.png",24, 24);
    public static final CustomIcon WORK_ICON = createIcon("/time.png",24, 24);
    public static final CustomIcon PROFIT_ICON = createIcon("/profit.png",24, 24);

    private Icons() {
        throw new AssertionError("This class is not instantiable");
    }

    private static CustomIcon createIcon(String name, int width, int height) {
        return new CustomIcon(name, width, height);
    }
}
