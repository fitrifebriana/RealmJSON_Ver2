package id.or.codelabs.realmjson2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.or.codelabs.realmjson2.adapter.BookAdapter;
import id.or.codelabs.realmjson2.model.Book;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private BookAdapter adapter;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.deleteRealm(realmConfiguration);
        realm = Realm.getInstance(realmConfiguration);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Load from json file first time
        if (adapter == null){
            List<Book> books = null;

            try {
                books = loadBooks();
            } catch (IOException e){
                e.printStackTrace();
            }

            //Grid view adapter
            adapter = new BookAdapter(this);
            adapter.setData(books);

            //Grid view which will display list
            gridView = (GridView)findViewById(R.id.books_list);
            gridView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            gridView.invalidate();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private List<Book> loadBooks() throws IOException {
        loadJsonFromStream();
        loadJsonFromObject();
        loadJsonFromString();

        return realm.where(Book.class).findAll();
    }

    private void loadJsonFromString()  {
        final String json = "{id: \"abc99\", title: \"Hello Kitty\", author: \"Dia\"}";

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createObjectFromJson(Book.class, json);
            }
        });
    }

    private void loadJsonFromObject() {
        Map<String, String> book = new HashMap<String, String>();
        book.put("id", "abc88");
        book.put("title", "Narnia");
        book.put("author", "Si Ujang");
        final JSONObject json = new JSONObject(book);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createObjectFromJson(Book.class, json);
            }
        });
    }

    private void loadJsonFromStream() throws IOException {
        //Use stream if you worried about the size of json
        InputStream stream = getAssets().open("books.json");

        //Store items to realm
        realm.beginTransaction();
        try {
            realm.createAllFromJson(Book.class, stream);
            realm.commitTransaction();
        }catch(IOException e){
            realm.cancelTransaction();
        }finally {
            if (stream != null){
                stream.close();
            }
        }
    }
}
