package ly.generalassemb.drewmahrt.shoppinglistwithsearch;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
<<<<<<< HEAD:starter-old/ShoppingListWithSearch/app/src/main/java/ly/generalassemb/drewmahrt/shoppinglistwithsearch/MainActivity.java
<<<<<<< HEAD
import android.view.View;
<<<<<<< HEAD
import android.widget.AdapterView;
=======
import android.view.ViewGroup;
>>>>>>> 8a8b0f03b88e2072d39af23995f2dc9327143e2a
=======
>>>>>>> parent of c2a4901... Finished Android Search Lab including grocery detail but no detail from search results
=======
>>>>>>> parent of c2a4901... Finished Android Search Lab including grocery detail but no detail from search results:starter-code/ShoppingListWithSearch/app/src/main/java/ly/generalassemb/drewmahrt/shoppinglistwithsearch/MainActivity.java
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import ly.generalassemb.drewmahrt.shoppinglistwithsearch.setup.DBAssetHelper;

public class MainActivity extends AppCompatActivity {
    private ListView mShoppingListView;
    private CursorAdapter mCursorAdapter;
<<<<<<< HEAD:starter-old/ShoppingListWithSearch/app/src/main/java/ly/generalassemb/drewmahrt/shoppinglistwithsearch/MainActivity.java
<<<<<<< HEAD
<<<<<<< HEAD
    private ShoppingSQLiteOpenHelper mHelper;

    Intent mDetailIntent;
//    ListView mGroceryList;
//    Cursor mCursor;
    AdapterView.OnItemClickListener mClickListener;
=======
    private ShoppingSQLiteOpenHelper mSQLHelper;
>>>>>>> 8a8b0f03b88e2072d39af23995f2dc9327143e2a
=======
>>>>>>> parent of c2a4901... Finished Android Search Lab including grocery detail but no detail from search results
=======
>>>>>>> parent of c2a4901... Finished Android Search Lab including grocery detail but no detail from search results:starter-code/ShoppingListWithSearch/app/src/main/java/ly/generalassemb/drewmahrt/shoppinglistwithsearch/MainActivity.java

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ignore the two lines below, they are for setup
        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        mShoppingListView = (ListView)findViewById(R.id.shopping_list_view);

        ShoppingSQLiteOpenHelper helper = new ShoppingSQLiteOpenHelper(MainActivity.this);
        Cursor cursor = helper.getShoppingList();

        mCursorAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,cursor,new String[]{ShoppingSQLiteOpenHelper.COL_ITEM_NAME},new int[]{android.R.id.text1},0);
        mShoppingListView.setAdapter(mCursorAdapter);
<<<<<<< HEAD:starter-old/ShoppingListWithSearch/app/src/main/java/ly/generalassemb/drewmahrt/shoppinglistwithsearch/MainActivity.java
<<<<<<< HEAD

<<<<<<< HEAD
        // Takes care of the initial intent in the OnCreate and executes a cursor search if the intent is a search.
        handleIntent(getIntent());


        mClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDetailIntent = new Intent(MainActivity.this, GroceryDetailActivity.class);

                // I had to capitalize the column names from the grocery_list table.
                String name = cursor.getString(cursor.getColumnIndex("ITEM_NAME"));
                String description = cursor.getString(cursor.getColumnIndex("DESCRIPTION"));
                String price = cursor.getString(cursor.getColumnIndex("PRICE"));
                String type = cursor.getString(cursor.getColumnIndex("TYPE"));
                mDetailIntent.putExtra("ITEM_NAME", name);
                mDetailIntent.putExtra("DESCRIPTION", description);
                mDetailIntent.putExtra("PRICE", price);
                mDetailIntent.putExtra("TYPE", type);
                startActivity(mDetailIntent);
            }
        };

        //mShoppingListView.setAdapter(mCursorAdapter);
        mShoppingListView.setOnItemClickListener(mClickListener);

=======
        handleIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    public void handleIntent(Intent intent){
        if (Intent.ACTION_SEARCH.equals(intent.getAction())){

            mSQLHelper = new ShoppingSQLiteOpenHelper(MainActivity.this);
            Cursor results = mSQLHelper.getSearchResults(intent.getStringExtra(SearchManager.QUERY));
            mCursorAdapter.changeCursor(results);
            mCursorAdapter.notifyDataSetChanged();

//            mCursorAdapter = new CursorAdapter(MainActivity.this, results, 0) {
//                @Override
//                public View newView(Context context, Cursor cursor, ViewGroup parent) {
//                    return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
//                }
//
//                @Override
//                public void bindView(View view, Context context, Cursor cursor) {
//                    TextView txt = (TextView) view.findViewById(android.R.id.text1);
//                    String rowData = cursor.getString(cursor.getColumnIndex("name") + cursor.getColumnIndex("type"));
//                    txt.setText(rowData);
//
//                }
//            };
        }
>>>>>>> 8a8b0f03b88e2072d39af23995f2dc9327143e2a
=======
>>>>>>> parent of c2a4901... Finished Android Search Lab including grocery detail but no detail from search results
    }
=======
    }
>>>>>>> parent of c2a4901... Finished Android Search Lab including grocery detail but no detail from search results:starter-code/ShoppingListWithSearch/app/src/main/java/ly/generalassemb/drewmahrt/shoppinglistwithsearch/MainActivity.java
}
