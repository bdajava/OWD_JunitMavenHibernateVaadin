// based on
// https://github.com/vaadin/valo-demo/blob/master/src/main/java/com/vaadin/tests/themes/valo/ValoMenuLayout.java

package lv.polarisit.demosidemenu;

import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

/**
 * 
 * @since
 * @author Vaadin Ltd
 */
public class ValoMenuLayout extends HorizontalLayout {

    CssLayout contentArea = new CssLayout();

    CssLayout menuArea = new CssLayout();

    public ValoMenuLayout() {
        setSizeFull();

        menuArea.setPrimaryStyleName("valo-menu");

        contentArea.setPrimaryStyleName("valo-content");
        contentArea.addStyleName("v-scrollable");
        contentArea.setSizeFull();

        addComponents(menuArea, contentArea);
        setExpandRatio(contentArea, 1);
    }

    public ComponentContainer getContentContainer() {
        return contentArea;
    }

    public void addMenu(Component menu) {
        menu.addStyleName("valo-menu-part");
        menuArea.addComponent(menu);
    }

}