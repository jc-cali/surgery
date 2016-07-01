package ly.generalassemb.drewmahrt.shoppinglistwithsearch;

import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import ly.generalassemb.drewmahrt.shoppinglistwithsearch.setup.DBAssetHelper;

public class MainActivity extends AppCompatActivity {
    private ListView mShoppingListView;
    private CursorAdapter mCursorAdapter;
    private ShoppingSQLiteOpenHelper mHelper;

    Intent mDetailIntent;
//    ListView mGroceryList;
    Cursor cursor;
    AdapterView.OnItemClickListener mClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ignore the two lines below, they are for setup
        final DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        mShoppingListView = (ListView)findViewById(R.id.shopping_list_view);
        mHelper = new ShoppingSQLiteOpenHelper(MainActivity.this);
        cursor = mHelper.getShoppingList();


//  Use the bindView to combine the NAME and TYPE columns in my list view.
        mCursorAdapter = new CursorAdapter(MainActivity.this, cursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1,
                        parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView txt = (TextView) view.findViewById(android.R.id.text1);
                String rowData = cursor.getString(cursor.getColumnIndex("ITEM_NAME")) +
                        " - " + cursor.getString(cursor.getColumnIndex("TYPE"));
                txt.setText(rowData);
            }
        };


//        mCursorAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,cursor,new String[]{ShoppingSQLiteOpenHelper.COL_ITEM_NAME},new int[]{android.R.id.text1},0);

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

        mShoppingListView.setOnItemClickListener(mClickListener);
        mShoppingListView.setAdapter(mCursorAdapter);

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
            // Reassign cursor by rerunning the query using the search string.
            cursor = mHelper.searchShoppingList(query);

            // Closes the current cursor from getShoppingList and uses the new cursor called
            // from searchShoppingList.
            mCursorAdapter.changeCursor(cursor);
            // The view will refresh since the data from the new cursor has changed.
            mCursorAdapter.notifyDataSetChanged();
        }
    }
}
