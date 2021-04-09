package utility;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.layout.AnchorPane;

/**
 * A special TableCell that features built-in formatting for the dollar sign.
 * Slightly modified to use Doubles instead of Longs.<br>
 * <a href=
 * "https://stackoverflow.com/questions/48552499/accounting-style-table-cell-in-javafx">Source.</a>
 * 
 * @author James_D, kleopatra
 *
 * @param <S> The type of the underlying TableView.
 */
public class PriceTableCell<S> extends TableCell<S, Double> {
    private final AnchorPane pane;
    private final Label valueLabel;
    // locale-aware currency format to use for formatting
    private DecimalFormat format;

    public PriceTableCell() {
        // grab an instance
        format = (DecimalFormat) NumberFormat.getCurrencyInstance();
        // get the currency symbol
        String symbol = format.getCurrency().getSymbol();
        // replace the currency symbol with an empty string
        DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();
        symbols.setCurrencySymbol("");
        format.setDecimalFormatSymbols(symbols);

        Label currencySignLabel = new Label(symbol);
        valueLabel = new Label();
        pane = new AnchorPane(currencySignLabel, valueLabel);
        AnchorPane.setLeftAnchor(currencySignLabel, 0.0);
        AnchorPane.setRightAnchor(valueLabel, 0.0);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    @Override
    protected void updateItem(Double price, boolean empty) {
        super.updateItem(price, empty);

        if (empty) {
            setGraphic(null);
        } else {
            // manual formatting
            // String text = String.format("%,d.%02d", price / 100, Math.abs(price % 100));
            valueLabel.setText(format.format(price));
            setGraphic(pane);
        }
    }
}
