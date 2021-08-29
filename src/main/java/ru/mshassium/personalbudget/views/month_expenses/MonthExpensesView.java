package ru.mshassium.personalbudget.views.month_expenses;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mshassium.personalbudget.db.entity.DailyTransactions;
import ru.mshassium.personalbudget.db.service.DailyTransactionService;
import ru.mshassium.personalbudget.views.MainLayout;
import ru.mshassium.personalbudget.views.model.DailyTransactionViewModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@PageTitle("Month Expenses")
@Route(value = "daily/:dailyTransactionID?/:action?(edit)", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class MonthExpensesView extends Div implements BeforeEnterObserver {

    private final String DAILYTRANSACTION_ID = "dailyTransactionID";
    private final String DAILYTRANSACTION_EDIT_ROUTE_TEMPLATE = "daily/%d/edit";

    private Grid<DailyTransactionViewModel> grid = new Grid<>(DailyTransactionViewModel.class, false);

    private DateTimePicker date;
    private TextField amount;
    private TextField description;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private DailyTransactions dailyTransaction;

    private BeanValidationBinder<DailyTransactions> binder;

    private DailyTransactionService dailyTransactionService;

    public MonthExpensesView(@Autowired DailyTransactionService dailyTransactionService) {
        addClassNames("hello-world-view", "flex", "flex-col", "h-full");
        this.dailyTransactionService = dailyTransactionService;
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
//        grid.addColumn("id").setAutoWidth(true);
        grid.addColumn("date").setAutoWidth(true);
        grid.addColumn("amountPerDay").setAutoWidth(true);
        grid.addColumn("spendPerDay").setAutoWidth(true);
        grid.addColumn("balancePerDay").setAutoWidth(true);
        grid.addColumn("balancePerMonth").setAutoWidth(true);
        grid.addColumn("adjustment").setAutoWidth(true);
        grid.addColumn("description").setAutoWidth(true);
//        grid.setDataProvider(new CrudServiceDataProvider<>(dailyTransactionService));
        grid.setDataProvider(new ListDataProvider<>(getDailyTransactionView()));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(DAILYTRANSACTION_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(MonthExpensesView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(DailyTransactions.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.forField(amount)
                .withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("amount");
        binder.forField(date).bind("date");
        binder.forField(description).bind("description");


        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.dailyTransaction == null) {
                    this.dailyTransaction = DailyTransactions.builder().build();
                }
                binder.writeBean(this.dailyTransaction);
                if (dailyTransaction.getDate() == null) {
                    dailyTransaction.setDate(LocalDateTime.now());
                }
                dailyTransactionService.update(this.dailyTransaction);
                clearForm();
                refreshGrid();
                Notification.show("DailyTransaction details stored.");
                UI.getCurrent().navigate(MonthExpensesView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the dailyTransaction details.");
            }
        });

        delete.addClickListener(e -> {
            try {
                if (this.dailyTransaction == null) {
                    return;
                }
                binder.writeBean(this.dailyTransaction);

//                dailyTransactionService.delete(this.dailyTransaction.getId());
                clearForm();
                refreshGrid();
                Notification.show("DailyTransaction deleted.");
                UI.getCurrent().navigate(MonthExpensesView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the dailyTransaction details.");
            }
        });

    }

    private List<DailyTransactionViewModel> getDailyTransactionView() {
        List<DailyTransactions> allForPeriod =
                dailyTransactionService.getAllForPeriod(LocalDateTime.now().minusDays(1),
                        LocalDateTime.now().plusDays(1));
        Map<Integer, List<DailyTransactions>> transactionsPerDay =
                allForPeriod.stream().collect(groupingBy(trans -> trans.getDate().getDayOfMonth()));
    return Collections.EMPTY_LIST;
//        return transactionsPerDay
//                .entrySet()
//                .stream()
//                .map(transactionEntry -> {
//                    Integer day = transactionEntry.getKey();
//                    List<DailyTransactions> transactions = transactionEntry.getValue();
//                    Integer spendPerDay = transactions.stream().map(DailyTransactions::getAmount).reduce(0, Integer::sum);
//                    String descriptions = transactions.stream().map(DailyTransactions::getDescription).collect(Collectors.joining(";"));
//                    LocalDateTime localDateTime = LocalDateTime.now().withDayOfMonth(day);
//                    String dateString = localDateTime.format(DateTimeFormatter.ofPattern("dd/MM"));
//                    return DailyTransactionViewModel.builder()
//                            .spendPerDay(spendPerDay)
//                            .date(dateString)
//                            .description(descriptions)
//                            .build();
//                })
//                .collect(Collectors.toList());
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> dailyTransactionId = event.getRouteParameters().getInteger(DAILYTRANSACTION_ID);
        if (dailyTransactionId.isPresent()) {
            Optional<DailyTransactions> dailyTransactionFromBackend = dailyTransactionService
                    .get(dailyTransactionId.get());
            if (dailyTransactionFromBackend.isPresent()) {
                populateForm(dailyTransactionFromBackend.get());
            } else {
                Notification.show(String.format("The requested dailyTransaction was not found, ID = %d",
                        dailyTransactionId.get()), 3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(MonthExpensesView.class);
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
        amount = new TextField("Amount");
        amount.setRequired(true);
        date = new DateTimePicker("Date");
        description = new TextField("Description");
        Component[] fields = new Component[]{date, amount, description};

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
        delete.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        buttonLayout.add(save, cancel, delete);
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

    private void populateForm(DailyTransactions value) {
        this.dailyTransaction = value;
        binder.readBean(this.dailyTransaction);

    }
}
