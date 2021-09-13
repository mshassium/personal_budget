package ru.mshassium.personalbudget.views;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import ru.mshassium.personalbudget.views.daily.DailyLayout;

/**
 * The main view is a top-level placeholder for other views.
 */
@Theme(themeFolder = "personalbudget")
@PageTitle("Main")
@Route(value = "")
public class MainLayout extends com.vaadin.flow.component.applayout.AppLayout {

    public MainLayout() {
        setContent(new DailyLayout());
    }

}
