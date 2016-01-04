package br.com.jortec.minhasmotos.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;

import java.util.List;

import br.com.jortec.minhasmotos.MainActivity;
import br.com.jortec.minhasmotos.R;
import br.com.jortec.minhasmotos.adapter.MotoAdapter;
import br.com.jortec.minhasmotos.dominio.Moto;

/**
 * Created by Jorliano on 23/10/2015.
 */
public class MotosLuxoFragment extends MotoFragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moto ,container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //Carregar mais itens na lista
                GridLayoutManager layoutManage = (GridLayoutManager) recyclerView.getLayoutManager();
                MotoAdapter adapter = (MotoAdapter) recyclerView.getAdapter();

                if (listaMotos.size() == layoutManage.findLastCompletelyVisibleItemPosition() + 1) {
                    List<Moto> listAux = ((MainActivity) getActivity()).getLista(7);

                    for (int i = 0; i < listAux.size(); i++) {
                        adapter.addListItem(listAux.get(i), listaMotos.size());
                    }
                }
            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        listaMotos = ((MainActivity) getActivity()).getLista(7);
        MotoAdapter adapter = new MotoAdapter(getActivity(),listaMotos);
        recyclerView.addOnItemTouchListener(new MotoFragment.RecyclerViewTochListener(getActivity(),recyclerView,this));
        recyclerView.setAdapter(adapter);

       /* fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"onclickButton",Toast.LENGTH_SHORT).show();
            }
        });*/

        return view;
    }

}
