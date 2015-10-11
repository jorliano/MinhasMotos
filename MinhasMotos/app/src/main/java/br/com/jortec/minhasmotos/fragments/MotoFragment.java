package br.com.jortec.minhasmotos.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.jortec.minhasmotos.MainActivity;
import br.com.jortec.minhasmotos.R;
import br.com.jortec.minhasmotos.adapter.MotoAdapter;
import br.com.jortec.minhasmotos.dominio.Moto;

/**
 * Created by Jorliano on 11/10/2015.
 */
public class MotoFragment extends Fragment {
    private RecyclerView recyclerView;
    List<Moto> listaMotos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moto ,container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);

        listaMotos = ((MainActivity) getActivity()).getLista(7);
        MotoAdapter adapter = new MotoAdapter(getActivity(),listaMotos);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
