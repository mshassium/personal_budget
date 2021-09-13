package ru.mshassium.personalbudget.views.daily;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import lombok.extern.log4j.Log4j2;
import ru.mshassium.personalbudget.db.entity.Wallet;

@Log4j2
public class DailyLayout extends FlexLayout {

    private HorizontalLayout summaryHeader = new HorizontalLayout();
    private FlexLayout tableData = new FlexLayout();

    public DailyLayout() {
        Select<Wallet> walletSelect = new Select<>(new Wallet(1L));
        Icon addNewWalletIco = new Icon(VaadinIcon.PLUS);
        Button addNewWallet = new Button(addNewWalletIco);
        Icon changeWalletIco = new Icon(VaadinIcon.EDIT);
        Button changeWallet = new Button(changeWalletIco);
        Icon removeWalletIco = new Icon(VaadinIcon.MINUS);
        Button removeWallet = new Button(removeWalletIco);
        summaryHeader.add(walletSelect, addNewWallet, removeWallet, changeWallet);
        add(summaryHeader);
    }
}
