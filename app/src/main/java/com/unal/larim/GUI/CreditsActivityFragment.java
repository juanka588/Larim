package com.unal.larim.GUI;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.unal.larim.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class CreditsActivityFragment extends Fragment {

    private int layout = R.layout.simple_list_item_activated_1;
    public CreditsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_credits, container, false);
        ListView recList = (ListView) root.findViewById(android.R.id.list);
        Context context=root.getContext();
        List<String> content=new ArrayList<>();
        content.add("Mario A. Higuera G.\nidea,concept and development");
        content.add("Andres Granados\niOS Developer");
        content.add("Juan Camilo Rodriguez Duran\nAndroid Developer");
        content.add("Martha Chacon\nGraphical Design");
        content.add("Giovanni Romero\nWeb Server Support");
        recList.setAdapter(new ArrayAdapter<>(context, layout, content));
        recList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        return root;
    }
}
