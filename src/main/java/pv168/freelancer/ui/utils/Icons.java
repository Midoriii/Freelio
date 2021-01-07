package pv168.freelancer.ui.utils;

/**
 * Utility class containing instances of all the used icons.
 *
 * @author xbenes2
 */
public final class Icons {

    public static final CustomIcon MINIMIZE_ICON = createIcon("/minus.png", 24, 24);
    public static final CustomIcon MINIMIZE_ICON_HOVER = createIcon("/minus_hover.png", 24, 24);
    public static final CustomIcon MINIMIZE_ICON_CLICK = createIcon("/minus_click.png", 24, 24);
    public static final CustomIcon QUIT_ICON = createIcon("/quit.png",24, 24);
    public static final CustomIcon QUIT_ICON_HOVER = createIcon("/quit_hover.png",24, 24);
    public static final CustomIcon QUIT_ICON_CLICK = createIcon("/quit_click.png",24, 24);

    public static final CustomIcon ADD_ICON = createIcon("/add.png",24, 24);
    public static final CustomIcon EDIT_ICON = createIcon("/edit.png",24, 24);
    public static final CustomIcon DELETE_ICON = createIcon("/minus_invert.png",24, 24);
    public static final CustomIcon ADD_ICON_S = createIcon("/add.png",16, 16);
    public static final CustomIcon EDIT_ICON_S = createIcon("/edit.png",16, 16);
    public static final CustomIcon DELETE_ICON_S = createIcon("/minus_invert.png",16, 16);
    public static final CustomIcon CONFIRM_ICON = createIcon("/confirm.png",24, 24);
    public static final CustomIcon DOLLAR_ICON = createIcon("/dollar.png",24, 24);

    public static final CustomIcon TOOLBAR_EDIT_ICON = createIcon("/toolbar_edit.png",16, 16);
    public static final CustomIcon TOOLBAR_DELETE_ICON = createIcon("/toolbar_delete.png",16, 16);

    public static final CustomIcon WORK_ICON = createIcon("/time.png",24, 24);
    public static final CustomIcon PROFIT_ICON = createIcon("/profit.png",24, 24);

    public static final CustomIcon CALENDAR_ICON = createIcon("/calendar3.png", 18, 18);

    private Icons() {
        throw new AssertionError("This class is not instantiable");
    }

    private static CustomIcon createIcon(String name, int width, int height) {
        return new CustomIcon(name, width, height);
    }
}
