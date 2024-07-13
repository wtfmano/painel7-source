package androidx.appcompat.view.menu;

import android.content.Context;
import android.view.MenuItem;
import android.view.SubMenu;
import androidx.collection.SimpleArrayMap;
import androidx.core.internal.view.SupportMenuItem;
import androidx.core.internal.view.SupportSubMenu;

/* loaded from: classes.dex */
public abstract class BaseMenuWrapper {
    final Context mContext;
    private SimpleArrayMap<SupportMenuItem, MenuItem> mMenuItems;
    private SimpleArrayMap<SupportSubMenu, SubMenu> mSubMenus;

    public BaseMenuWrapper(Context context) {
        this.mContext = context;
    }

    public final MenuItem getMenuItemWrapper(MenuItem menuItem) {
        if (menuItem instanceof SupportMenuItem) {
            SupportMenuItem supportMenuItem = (SupportMenuItem) menuItem;
            if (this.mMenuItems == null) {
                this.mMenuItems = new SimpleArrayMap<>();
            }
            MenuItem wrappedItem = this.mMenuItems.get(menuItem);
            if (wrappedItem == null) {
                MenuItem wrappedItem2 = new MenuItemWrapperICS(this.mContext, supportMenuItem);
                this.mMenuItems.put(supportMenuItem, wrappedItem2);
                return wrappedItem2;
            }
            return wrappedItem;
        }
        return menuItem;
    }

    public final SubMenu getSubMenuWrapper(SubMenu subMenu) {
        if (subMenu instanceof SupportSubMenu) {
            SupportSubMenu supportSubMenu = (SupportSubMenu) subMenu;
            if (this.mSubMenus == null) {
                this.mSubMenus = new SimpleArrayMap<>();
            }
            SubMenu wrappedMenu = this.mSubMenus.get(supportSubMenu);
            if (wrappedMenu == null) {
                SubMenu wrappedMenu2 = new SubMenuWrapperICS(this.mContext, supportSubMenu);
                this.mSubMenus.put(supportSubMenu, wrappedMenu2);
                return wrappedMenu2;
            }
            return wrappedMenu;
        }
        return subMenu;
    }

    public final void internalClear() {
        if (this.mMenuItems != null) {
            this.mMenuItems.clear();
        }
        if (this.mSubMenus != null) {
            this.mSubMenus.clear();
        }
    }

    public final void internalRemoveGroup(int groupId) {
        if (this.mMenuItems != null) {
            int i = 0;
            while (i < this.mMenuItems.size()) {
                if (this.mMenuItems.keyAt(i).getGroupId() == groupId) {
                    this.mMenuItems.removeAt(i);
                    i--;
                }
                i++;
            }
        }
    }

    public final void internalRemoveItem(int id) {
        if (this.mMenuItems != null) {
            for (int i = 0; i < this.mMenuItems.size(); i++) {
                if (this.mMenuItems.keyAt(i).getItemId() == id) {
                    this.mMenuItems.removeAt(i);
                    return;
                }
            }
        }
    }
}
