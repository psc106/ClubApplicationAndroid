package teamproject.com.clubapplication.test;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.Horder> {

    ArrayList<String> strings;

    public TestAdapter(ArrayList<String> strings) {
        this.strings = strings;
    }

    @NonNull
    @Override
    public Horder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_test, parent, false);
        Horder horder = new Horder(view);
        return horder;
    }

    @Override
    public void onBindViewHolder(@NonNull Horder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public class Horder extends RecyclerView.ViewHolder {

        public Horder(View itemView) {
            super(itemView);
        }
    }
}
