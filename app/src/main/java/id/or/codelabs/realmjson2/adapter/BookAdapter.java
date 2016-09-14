package id.or.codelabs.realmjson2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import id.or.codelabs.realmjson2.MainActivity;
import id.or.codelabs.realmjson2.R;
import id.or.codelabs.realmjson2.model.Book;

/**
 * Created by FitriFebriana on 9/8/2016.
 */
public class BookAdapter extends BaseAdapter {

    public static final String TAG = MainActivity.class.getName();
    private LayoutInflater inflater;
    private List<Book> books = null;

    public BookAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<Book> details) {
        this.books = details;
    }

    @Override
    public int getCount() {
        if (books == null) {
            return 0;
        }
        return books.size();
    }

    @Override
    public Object getItem(int i) {
        if (books == null || books.get(i) == null) {
            return null;
        }
        return books.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.book_listitem, parent, false);

        Book book = books.get(i);

        if (book != null) {
            ((TextView) view.findViewById(R.id.id)).setText(book.getId());
            ((TextView) view.findViewById(R.id.title)).setText(book.getTitle());
            ((TextView) view.findViewById(R.id.author)).setText(book.getAuthor());
            Log.d("ID BUKU", book.getId() + " ");
        }
        return view;
    }
}
