package pl.kfrak.awesomeapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.kfrak.awesomeapp.adapters.FileListAdapter;
import pl.kfrak.awesomeapp.models.FileItem;
import pl.kfrak.awesomeapp.R;


public class ExplorerFragment extends Fragment implements FileListAdapter.OnFileItemClicked {

    private static final String ARG_PATH_PARAM = "param1";
    private static final boolean USE_ACTIVITY_TO_NAVIGATE = true;

    @BindView(R.id.explorerFragment_filePathText)
    TextView filePathText;

    @BindView(R.id.ecplorerFragment_recyclerView)
    RecyclerView recyclerView;

    private String currentFilePath;
    private ExploratorInteractionListener mListener;
    private FileListAdapter filesAdapter;

    public ExplorerFragment() {
        // Required empty public constructor
    }


    public static ExplorerFragment newInstance(String filePath) {
        ExplorerFragment fragment = new ExplorerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PATH_PARAM, filePath);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentFilePath = getArguments().getString(ARG_PATH_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explorer, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //chcemy na naszym tekscie pokazac jaka jest aktualna sciezka
        filePathText.setText(currentFilePath);
        loadFileList();
    }

    private void loadFileList() {
        List<FileItem> fileItems = new ArrayList<>();
        File file = new File(currentFilePath);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File currentFile : files) {
                FileItem fileItem = new FileItem(currentFile);
                fileItems.add(fileItem);
            }
        } else {
            fileItems.add(new FileItem(file));
        }
        Log.d("Pliki", "Ile plikow" + fileItems.size());

        fileItems.add(0, new FileItem(file.getParent()));

        //adapter tworzy na podstawie FileItem nasz folderek (ewentualnie plik)
        filesAdapter = new FileListAdapter(getActivity().getApplicationContext(), fileItems, this);
        recyclerView.setAdapter(filesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ExploratorInteractionListener) {
            mListener = (ExploratorInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        //tez null bo moze trzymac referencje do naszego activity/kontekstu itd
        filesAdapter = null;
    }

    @Override
    public void onFileItemClicked(FileItem fileItem) {
        String path = fileItem.getPath();
        if (path != null && !path.equals((File.separator))) {

            if (USE_ACTIVITY_TO_NAVIGATE) {
                if (fileItem.isDirectory()) {
                    if (fileItem.getName().equals("..."))
                    mListener.onBackClicked();
                    mListener.onDirectoryClicked(fileItem.getPath());
                } else
                    mListener.onFileClicked(fileItem.getPath());
            } else {

                currentFilePath = fileItem.getPath();
                updateFilePath();
                loadFileList();
            }
        }
    }

    private void updateFilePath() {
    }

    public interface ExploratorInteractionListener {
        void onDirectoryClicked(String newPath);

        //nasze main activity musi nadpisac te dwie ponizsze, nowe funckje
        void onFileClicked(String filePath);

        void onBackClicked();
    }
}
