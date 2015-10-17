package br.com.jortec.minhasmotos.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.List;

import br.com.jortec.minhasmotos.MainActivity;
import br.com.jortec.minhasmotos.R;
import br.com.jortec.minhasmotos.adapter.MotoAdapter;
import br.com.jortec.minhasmotos.dominio.Moto;
import br.com.jortec.minhasmotos.interfaces.RecyclerViewOnclickListener;

/**
 * Created by Jorliano on 11/10/2015.
 */
public class MotoFragment extends Fragment implements RecyclerViewOnclickListener {
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

                //Carregar mais itens na lista
                 LinearLayoutManager layoutManage = (LinearLayoutManager) recyclerView.getLayoutManager();
                //GridLayoutManager layoutManage = (GridLayoutManager) recyclerView.getLayoutManager();
                MotoAdapter adapter = (MotoAdapter) recyclerView.getAdapter();

                if (listaMotos.size() == layoutManage.findLastCompletelyVisibleItemPosition() + 1) {
                    List<Moto> listAux = ((MainActivity) getActivity()).getLista(7);

                    for (int i = 0; i < listAux.size(); i++) {
                        adapter.addListItem(listAux.get(i), listaMotos.size());
                    }
                }
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

       // GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        listaMotos = ((MainActivity) getActivity()).getLista(7);
        MotoAdapter adapter = new MotoAdapter(getActivity(),listaMotos);
        //adapter.setRecyclerViewOnclickListener(this);
        recyclerView.addOnItemTouchListener(new RecyclerViewTochListener(getActivity(),recyclerView,this));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onclickListener(View view, int position) {
        Toast.makeText(getActivity(),"onclickListener",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongPressClickListener(View view, int position) {
        Toast.makeText(getActivity(),"onLongPressClickListener",Toast.LENGTH_SHORT).show();
    }

    public static class RecyclerViewTochListener implements RecyclerView.OnItemTouchListener{
        private Context context;
        private GestureDetector gestureDetector;
        private RecyclerViewOnclickListener recyclerViewOnclickListene;

        public RecyclerViewTochListener(Context c,final RecyclerView rv, RecyclerViewOnclickListener rvocl){
            this.context = c;
            this.recyclerViewOnclickListene = rvocl;

            //Eventos para o tipo de toque na tela
            gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);

                    View view = rv.findChildViewUnder(e.getX(),e.getY());

                    if(view != null && recyclerViewOnclickListene != null){
                        recyclerViewOnclickListene.onLongPressClickListener(view,rv.getChildPosition(view));
                    }
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    View view = rv.findChildViewUnder(e.getX(),e.getY());

                    if(view != null && recyclerViewOnclickListene != null){
                        recyclerViewOnclickListene.onclickListener(view, rv.getChildPosition(view));
                    }

                    return (true);
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            gestureDetector.onTouchEvent(e); // chama o evento do GestureDetector
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
