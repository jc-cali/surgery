package ly.generalassemb.drewmahrt.shoppinglistwithsearch;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
<<<<<<< HEAD
import android.widget.AdapterView;
=======
import android.view.ViewGroup;
>>>>>>> 8a8b0f03b88e2072d39af23995f2dc9327143e2a
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import ly.generalassemb.drewmahrt.shoppinglistwithsearch.setup.DBAssetHelper;

public class MainActivity extends AppCompatActivity {
    private ListView mShoppingListView;
    private CursorAdapter mCursorAdapter;
<<<<<<< HEAD
    private ShoppingSQLiteOpenHelper mHelper;

    Intent mDetailIntent;
//    ListView mGroceryList;
//    Cursor mCursor;
    AdapterView.OnItemClickListener mClickListener;
=======
    private ShoppingSQLiteOpenHelper mSQLHelper;
>>>>>>> 8a8b0f03b88e2072d39af23995f2dc9327143e2a

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ignore the two lines below, they are for setup
        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        mShoppingListView = (ListView)findViewById(R.id.shopping_list_view);

        mHelper = new ShoppingSQLiteOpenHelper(MainActivity.this);
        final Cursor cursor = mHelper.getShoppingList();

//  My attempt to get the type field in the list view to display in the main activity
//        String data1 = ShoppingSQLiteOpenHelper.COL_ITEM_NAME;
//        String data2 = ShoppingSQLiteOpenHelper.COL_ITEM_TYPE;
//        String data3 = data1 + " - " + data2;
//        mCursorAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,cursor,new String[]{data3},new int[]{android.R.id.text1},0);

        mCursorAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,cursor,new String[]{ShoppingSQLiteOpenHelper.COL_ITEM_NAME},new int[]{android.R.id.text1},0);

        mShoppingListView.setAdapter(mCursorAdapter);

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
    }

    // This is called since launchMode is set to "singleTop" in the AndroidManifest.xml file.
    // The activity is re-launched if on top of the activity stack and reuses the existing instance of the intent
    // that was called to relaunch it.
    // Note: An activity will always be paused before receiving a new intent, so you can count on onResume() being called   // after this method.
    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        // Call menu xml file.
        menuInflater.inflate(R.menu.main_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        // Call search from main_menu.xml file.
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Cursor cursor = mHelper.searchShoppingList(query);

            // Closes the current cursor from getShoppingList and uses the new cursor called
            // from searchShoppingList.

            // NOTE: This cursor closes the previous cursor query, which errors the app when attempting to
            // get grocery detail from my search results.
            mCursorAdapter.changeCursor(cursor);
            // The view will refresh since the data from the new cursor has changed.
            mCursorAdapter.notifyDataSetChanged();
        }
    }

}
