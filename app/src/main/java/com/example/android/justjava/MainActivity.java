/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();
        EditText nameInputText = (EditText) findViewById(R.id.name_input_text);
        String nameOrder = nameInputText.getText().toString();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        //        Log.v("MainActivity","The price is " + price);
//        displayMessage(createOrderSummary(price, hasWhippedCream, hasChocolate, nameOrder));
        String summaryMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, nameOrder);
        String subject = getString(R.string.order_summary_email_subject, nameOrder);
        composeEmail(subject, summaryMessage);
    }

    /**
     * Calculates the price of the order.
     * @param addWhippedCream checks if whipped cream is added
     * @param addChocolate checks if chocolate is added
     * @return total price is returned
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        //basic price for coffee
        int price = 5;
        // add $ 1 for whipped cream
        if (addWhippedCream) {
            price += 1;
        }
        // add $2 for chocolate
        if (addChocolate) {
            price += 2;
        }
        return (quantity * price);
    }

    /**
     * This methods are used to increment and decrement the quantity
     */
    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
        }else {
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.toast_100), Toast.LENGTH_LONG);
            toast.show();
        }
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
        }else {
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.toast_1), Toast.LENGTH_LONG);
            toast.show();
        }
        displayQuantity(quantity);
    }

    /**
     * This method displays the given price on the screen.
     * @param number is the total price
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
    */

    /**
     * This method displays the given quantity value on the screen.
     * @param number is the quantity of coffees ordered
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method creates the order summary.
     * @param price of order
     * @param addWhippedCream is whether or not the user wants whipped cream on his order
     * @param addChocolate is whether or not the user wants chocolate on his order
     * @param name is the name entered
     * @return the display order message
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price, price);
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    private void composeEmail(String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given text on the screen.
     * @param message to be displayed on order

    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
    */
}