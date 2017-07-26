package pl.kfrak.awesomeapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pl.kfrak.awesomeapp.R;
import pl.kfrak.awesomeapp.models.FileItem;
import pl.kfrak.awesomeapp.viewHolders.FileViewHolders;

/**
 * Created by RENT on 2017-07-25.
 */

public class FileListAdapter extends RecyclerView.Adapter<FileViewHolders> {

    private final LayoutInflater inflater;
    private List<FileItem> fileItems;
    private OnFileItemClicked onFileItemClicked;

    public FileListAdapter(Context context, List<FileItem> fileItems, OnFileItemClicked onFileItemClicked) {
        this.fileItems = fileItems;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.onFileItemClicked = onFileItemClicked;
    }

    @Override
    public FileViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.file_item, parent, false);
        return new FileViewHolders(view);
    }

    @Override
    public void onBindViewHolder(FileViewHolders holder, int position) {
        final FileItem fileItem = fileItems.get(position);
        holder.nameText.setText(fileItem.getName());
        if (fileItem != null) {
            if (fileItem.isDirectory()) {
                holder.icon.setImageResource(R.drawable.ic_folder);
            } else {
                //holder.fileSizeText.setText((int) fileItem.getFileSizeInBytes());
                holder.icon.setImageResource(R.drawable.ic_crop);
            }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //obiekt interfejsu, zapisany jako prywatna i dodany w konstruktorze. Jak korzystam z ExplorerFragment, mam kontekst poyniej liste a poyniej
                        //to co bedyie mnie nasuchiwao(kontext, liste plików + this)
                        onFileItemClicked.onFileItemClicked(fileItem);
                    }
                });
            }
        }



    @Override
    public int getItemCount() {
        return fileItems.size();
    }

    //interfejs jesli bedzie ... to musi nadpisac metode tą koło void
    public interface OnFileItemClicked {
        void onFileItemClicked(FileItem fileItem);
    }
}
