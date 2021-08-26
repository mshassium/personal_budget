package ru.mshassium.personalbudget.views.helloworld;

import java.util.Optional;

import ru.mshassium.personalbudget.data.entity.DailyTransaction;
import ru.mshassium.personalbudget.data.service.DailyTransactionService;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.artur.helpers.CrudServiceDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import ru.mshassium.personalbudget.views.MainLayout;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.textfield.TextField;
import java.time.Duration;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;

@PageTitle("Hello World")
@Route(value = "daily/:dailyTransactionID?/:action?(edit)", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HelloWorldView extends Div implements BeforeEnterObserver {

    private final String DAILYTRANSACTION_ID = "dailyTransactionID";
    private final String DAILYTRANSACTION_EDIT_ROUTE_TEMPLATE = "daily/%d/edit";

    private Grid<DailyTransaction> grid = new Grid<>(DailyTransaction.class, false);

    private TextField id;
    private DateTimePicker date;
    private TextField amount;
    private TextField description;
    private TextField type;
    private TextField user;
    private TextField spendingPeriod;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<DailyTransaction> binder;

    private DailyTransaction dailyTransaction;

    private DailyTransactionService dailyTransactionService;

    public HelloWorldView(@Autowired DailyTransactionService dailyTransactionService) {
        addClassNames("hello-world-view", "flex", "flex-col", "h-full");
        this.dailyTransactionService = dailyTransactionService;
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("id").setAutoWidth(true);
        grid.addColumn("date").setAutoWidth(true);
        grid.addColumn("amount").setAutoWidth(true);
        grid.addColumn("description").setAutoWidth(true);
        grid.addColumn("type").setAutoWidth(true);
        grid.addColumn("user").setAutoWidth(true);
        grid.addColumn("spendingPeriod").setAutoWidth(true);
        grid.setDataProvider(new CrudServiceDataProvider<>(dailyTransactionService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(DAILYTRANSACTION_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(HelloWorldView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(DailyTransaction.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.forField(id).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("id");
        binder.forField(amount).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("amount");
        binder.forField(user).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("user");
        binder.forField(spendingPeriod).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("spendingPeriod");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.dailyTransaction == null) {
                    this.dailyTransaction = new DailyTransaction();
                }
                binder.writeBean(this.dailyTransaction);

                dailyTransactionService.update(this.dailyTransaction);
                clearForm();
                refreshGrid();
                Notification.show("DailyTransaction details stored.");
                UI.getCurrent().navigate(HelloWorldView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the dailyTransaction details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> dailyTransactionId = event.getRouteParameters().getInteger(DAILYTRANSACTION_ID);
        if (dailyTransactionId.isPresent()) {
            Optional<DailyTransaction> dailyTransactionFromBackend = dailyTransactionService
                    .get(dailyTransactionId.get());
            if (dailyTransactionFromBackend.isPresent()) {
                populateForm(dailyTransactionFromBackend.get());
            } else {
                Notification.show(String.format("The requested dailyTransaction was not found, ID = %d",
                        dailyTransactionId.get()), 3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(HelloWorldView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("flex flex-col");
        editorLayoutDiv.setWidth("400px");

        Div editorDiv = new Div();
        editorDiv.setClassName("p-l flex-grow");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        id = new TextField("Id");
        date = new DateTimePicker("Date");
        date.setStep(Duration.ofSeconds(1));
        amount = new TextField("Amount");
        description = new TextField("Description");
        type = new TextField("Type");
        user = new TextField("User");
        spendingPeriod = new TextField("Spending Period");
        Component[] fields = new Component[]{id, date, amount, description, type, user, spendingPeriod};

        for (Component field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("w-full flex-wrap bg-contrast-5 py-s px-l");
        buttonLayout.setSpacing(true);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(DailyTransaction value) {
        this.dailyTransaction = value;
        binder.readBean(this.dailyTransaction);

    }
}
