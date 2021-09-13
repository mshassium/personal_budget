package ru.mshassium.personalbudget.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.data.binder.PropertyDefinition;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The main view is a top-level placeholder for other views.
 */
@Theme(themeFolder = "personalbudget")
@PageTitle("Main")
@Route(value = "")
public class MainLayout extends AppLayout {

    public MainLayout() {
        Div div = new Div();
        Div div2 = new Div();
        div.setText("Text");
        div2.setText("Text2");
        FlexLayout flexLayout = new FlexLayout(div);
        FlexLayout flexLayout2 = new FlexLayout(div2);
        Grid<TestGrid> grid = new Grid(TestGrid.class, true);
        List<PropertyDefinition<TestGrid, ?>> collect = grid.getPropertySet().getProperties().collect(Collectors.toList());
        grid.setDataProvider(new ListDataProvider<TestGrid>(Collections.singleton(new TestGrid("123"))));
        addToDrawer(flexLayout);
        addToNavbar(flexLayout2);
        setContent(grid);
    }

    public class TestGrid{
        private String ids;

        public TestGrid(String ids) {
            this.ids = ids;
        }

        public String getIds() {
            return ids;
        }
    }

}
