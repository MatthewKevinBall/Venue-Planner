package planner.venue.venue;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class displayMenu extends AppCompatActivity {

    final int menu_size = 7;
    double totalPrice;

    Button mainMenuButton;
    Button chickenBurgerButton, Soft_Drink, Chicken_Burger, Beef_Burger,Steak, Chicken_Nuggets, Fish_and_Chips, Fries;
    TextView totalTrollyPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menulayout);
        String StringRawInfo = returnMenuString();
        String menuString[] = StringRawInfo.split("\\s+");
        final Item[] listOfItems = createItemArray(menuString);

        final ArrayList<String> trolly = new ArrayList<String>();
        final ArrayList<String> trolly_price = new ArrayList<String>();


        final ArrayList<Item> orderTrolly = new ArrayList<Item>();
        totalPrice = 0.00;

        ListView names = findViewById(R.id.ItemsInTrolly);
        final CustomListAdapter trollyList = new CustomListAdapter(this, trolly, trolly_price);



        names.setAdapter(trollyList);

        mainMenuButton = (Button)findViewById(R.id.endButton);
        chickenBurgerButton = (Button)findViewById(R.id.chickenBurgerButton);
        totalTrollyPrice = (TextView)findViewById(R.id.totalTrollyPrice);

        mainMenuButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(displayMenu.this, mainMenu.class);
                startActivity(intent);
            }
        });

        chickenBurgerButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                trolly.add(listOfItems[1].name);
                trolly_price.add(Double.toString(listOfItems[1].price));

                orderTrolly.add(listOfItems[1]);
                trollyList.notifyDataSetChanged();
                setPrice(orderTrolly);


            }
        });




    }

    public void setPrice(ArrayList<Item> orderTrolly){
        totalPrice = 0;
        for(int i = 0; i < orderTrolly.size(); i++){
            totalPrice += orderTrolly.get(i).getPrice();

        }

        totalTrollyPrice.setText(String.valueOf(totalPrice));


    }

    public String returnMenuString(){


        InputStream input;
        String text = "";
        try {
            InputStream is = getAssets().open("menu.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return text;

    }

    //turns the text document into array of tables
    public Item[] createItemArray(String[] menuString){
        Item[] listOfItems = new Item[menu_size];

        int[] itemNumbers = new int[menu_size];
        String[] itemName = new String[menu_size];
        Double[] itemPrice = new Double[menu_size];

        int total_items = menu_size * 3;
        for(int i = 0; i < menu_size; i++){
            int j = i *3;
            itemNumbers[i] = Integer.parseInt(menuString[j]);
            itemName[i] = menuString[j+1];
            itemPrice[i] = Double.parseDouble(menuString[j+2]);


        }

        for(int i = 0; i<menu_size; i++){

            itemName[i] = itemName[i].replaceAll("_", " ");
        }

        for(int i = 0; i < menu_size; i++){
            listOfItems[i] = new Item(itemNumbers[i], itemName[i], itemPrice[i]);
        }



        return listOfItems;
    }
}
