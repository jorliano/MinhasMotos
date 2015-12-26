package br.com.jortec.minhasmotos.fragments;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.EventListenerAdapter;

import java.util.List;

import br.com.jortec.minhasmotos.MainActivity;
import br.com.jortec.minhasmotos.MotoDetalheActivity;
import br.com.jortec.minhasmotos.R;
import br.com.jortec.minhasmotos.adapter.MotoAdapter;
import br.com.jortec.minhasmotos.dominio.Moto;
import br.com.jortec.minhasmotos.extras.UtilConexao;
import br.com.jortec.minhasmotos.interfaces.RecyclerViewOnclickListener;

/**
 * Created by Jorliano on 11/10/2015.
 */
public class MotoFragment extends Fragment implements RecyclerViewOnclickListener {
    protected RecyclerView recyclerView;
    protected android.support.design.widget.FloatingActionButton fab;
    protected List<Moto> listaMotos;
    protected SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_moto, container, false);

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
        MotoAdapter adapter = new MotoAdapter(getActivity(), listaMotos);
        //adapter.setRecyclerViewOnclickListener(this);
        recyclerView.addOnItemTouchListener(new RecyclerViewTochListener(getActivity(), recyclerView, this));
        recyclerView.setAdapter(adapter);

        /*fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "onclickButton", Toast.LENGTH_SHORT).show();
            }
        });*/
        fab = (android.support.design.widget.FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.design.widget.Snackbar.make(view,"FAB click", android.support.design.widget.Snackbar.LENGTH_SHORT)
                        .setAction("ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
            }
        });

        //SwipeRefreshLayout
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (UtilConexao.isConectado(getContext())) {
                    MotoAdapter adapter = (MotoAdapter) recyclerView.getAdapter();
                    LinearLayoutManager layoutManage = (LinearLayoutManager) recyclerView.getLayoutManager();

                    if (listaMotos.size() == layoutManage.findLastCompletelyVisibleItemPosition() + 1) {
                        List<Moto> listAux = ((MainActivity) getActivity()).getLista(2);

                        for (int i = 0; i < listAux.size(); i++) {
                            adapter.addListItem(listAux.get(i), 0);
                            recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, 0);
                        }

                    }

                } else {
                    android.support.design.widget.Snackbar.make(view,"Sem acesso a internet , verifique sua conexão", android.support.design.widget.Snackbar.LENGTH_LONG)
                            .setAction("ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).show();

                   /* SnackbarManager.show(
                            Snackbar.with(getActivity())
                                    .text("Sem acesso a internet , verifique sua conexão")
                                    .color(getActivity().getResources().getColor(android.R.color.black))
                                    .textColor(getActivity().getResources().getColor(android.R.color.white))
                                    .eventListener(new EventListenerAdapter() {
                                        @Override
                                        public void onShow(Snackbar snackbar) {
                                            super.onShow(snackbar);
                                            ObjectAnimator.ofFloat(fab, "translationY", - snackbar.getHeight()).start();
                                        }

                                        @Override
                                        public void onDismiss(Snackbar snackbar) {
                                            super.onDismissed(snackbar);
                                            ObjectAnimator.ofFloat(fab, "translationY",0).start();
                                        }
                                    }));*/
                }
                swipeRefreshLayout.setRefreshing(false);
            }

        });

        return view;
    }

    @Override
    public void onclickListener(View view, int position) {

        Moto moto = new Moto();
        moto.setModelo(listaMotos.get(position).getModelo());
        moto.setMarca(listaMotos.get(position).getMarca());
        moto.setCategoria(listaMotos.get(position).getCategoria());
        moto.setDescricao(listaMotos.get(position).getDescricao());
        moto.setFoto(listaMotos.get(position).getFoto());

        Intent intent = new Intent(getActivity(), MotoDetalheActivity.class);
        intent.putExtra("dados", moto);

        //TRANSACTION
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
           View tvMarca = view.findViewById(R.id.txMarca);
           View tvModelo = view.findViewById(R.id.txModelo);
           //View tvDescricao = view.findViewById(R.id.txt_descricao_detalhe);
           View tvImagem = view.findViewById(R.id.imagem);


            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                    Pair.create(tvImagem, "element1"),
                    Pair.create(tvModelo, "element2"),
                    Pair.create(tvMarca, "element3"));

            getActivity().startActivity(intent, options.toBundle());
        }else{
            startActivity(intent);
        }



    }

    @Override
    public void onLongPressClickListener(View view, int position) {
        Toast.makeText(getActivity(), "onLongPressClickListener", Toast.LENGTH_SHORT).show();
    }

    public static class RecyclerViewTochListener implements RecyclerView.OnItemTouchListener {
        private Context context;
        private GestureDetector gestureDetector;
        private RecyclerViewOnclickListener recyclerViewOnclickListene;

        public RecyclerViewTochListener(Context c, final RecyclerView rv, RecyclerViewOnclickListener rvocl) {
            this.context = c;
            this.recyclerViewOnclickListene = rvocl;

            //Eventos para o tipo de toque na tela
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);

                    View view = rv.findChildViewUnder(e.getX(), e.getY());

                    if (view != null && recyclerViewOnclickListene != null) {
                        recyclerViewOnclickListene.onLongPressClickListener(view, rv.getChildPosition(view));
                    }
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    View view = rv.findChildViewUnder(e.getX(), e.getY());

                    if (view != null && recyclerViewOnclickListene != null) {
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
