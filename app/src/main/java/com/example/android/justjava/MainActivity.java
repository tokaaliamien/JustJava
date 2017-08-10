package com.example.android.justjava;

/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    int numberOfCoffees = 2;
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
        int price = calculatePrice();

        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = whippedCream.isChecked();

        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate);
        boolean hasChocolate = chocolate.isChecked();

        String name = ((EditText) findViewById(R.id.name)).getText().toString();


        String message = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for" + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    /**
     * Calculates the price of the order.
     */

    private int calculatePrice() {
        int price = quantity * 5;
        return price;
    }

    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String name) {
        if (hasChocolate)
            price = price + (2 * quantity);
        if (hasWhippedCream)
            price = price + (1 * quantity);

        return "Name : " + name
                + "\nAdd whipped cream?" + hasWhippedCream
                + "\nAdd Choclate? " + hasChocolate
                + "\nQuantity :" + quantity +
                "\nTotal : $" + price
                + "\nThank you!";
    }


    public void incrementNumber(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "Cant order more than 100 cups", Toast.LENGTH_LONG).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * @param view
     */
    public void decrementNumber(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "Cant order less than 1 cup", Toast.LENGTH_LONG).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given text on the screen.
     */
}
