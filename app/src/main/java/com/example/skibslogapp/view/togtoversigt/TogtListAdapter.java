package com.example.skibslogapp.view.togtoversigt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skibslogapp.R;
import com.example.skibslogapp.datalayer.local.EtapeDAO;
import com.example.skibslogapp.etapeoversigt.EtapeOversigt_frag;
import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Togt;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * This is a custom adapter for the RecycleView displaying the "Togts".
 */
public class TogtListAdapter extends RecyclerView.Adapter<TogtListAdapter.TogtListViewHolder> {

    private List<Togt> togtArrayList;
    private Context mContext;

    public TogtListAdapter(List<Togt> list, Context context) {
        togtArrayList = list;
        mContext = context;
    }

    /**
     * Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
     * The new ViewHolder will be used to display items of the adapter using
     * onBindViewHolder(ViewHolder, int, List). Since it will be re-used to display different items
     * in the data set, it is a good idea to cache references to sub views of the View to
     * avoid unnecessary findViewById(int) calls.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public TogtListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.togt_list_item, parent,false);
        TogtListViewHolder viewHolder = new TogtListViewHolder(view);
        return viewHolder;
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * This method should update the contents of the itemView to reflect the item at the given position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item
     *               at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull TogtListViewHolder holder, final int position) {

        Togt currTogt = togtArrayList.get(position);
        holder.togtName.setText(currTogt.getName());
        holder.ship.setText(currTogt.getSkib());
        holder.startDest.setText("Fra " + currTogt.getStartDestination());

        List<Etape> etaper = new EtapeDAO(mContext).getEtaper(currTogt);
        Etape firstEtape = etaper.get(0);
        if( firstEtape.getStatus() != Etape.Status.NEW ){
            Calendar cal = Calendar.getInstance();
            cal.setTime(firstEtape.getStartDate());
            holder.date.setText(String.format(Locale.US, "%02d/%02d", cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) ));
            holder.year.setText(String.format(Locale.US, "%d", cal.get(Calendar.YEAR)));
        }else{
            holder.date.setText("Ikke påbegyndt");
            holder.year.setText("");
        }
    }

    /**
     * Returns the amount of items in the list, which is the size of the ArrayList.
     * If this returns 0, the list and the recycleview will be empty
     */
    @Override
    public int getItemCount() {
        return togtArrayList.size();
    }

//    /**
//     * This function removes an item from the togtArrayList, and thus from the RecycleView.
//     * It also notifies any any registered observers that the item previously located at <code>position</code>
//     * has been removed from the data set. The items previously located at and after
//     * <code>position</code> may now be found at <code>oldPosition - 1</code>.
//     *
//     * @param position The position the element is in the RecycleView
//     */
//    public void delete(int position){
//        Togt togt = togtArrayList.get(position);
//        togtArrayList.remove(position);
//        TogtDAO togtDAO = new TogtDAO(mContext);
//        togtDAO.deleteTogt(togt);
//        notifyItemRemoved(position);
//    }

    /**
     * The ViewHolder design pattern can be applied when using a custom adapter.
     *
     * Every time when the adapter calls getView() method, the findViewById() method is also called.
     * This is a very intensive work for the mobile CPU and so affects the performance of the
     * application and the battery consumption increases.
     *
     * To avoid this, ViewHolder is used.
     * A ViewHolder holds the reference to the id of the view resource and calls to the resource
     * will not be required. Thus performance of the application increases.
     */
    public class TogtListViewHolder extends RecyclerView.ViewHolder{

        TextView togtName,ship,startDest,date,year;

        public TogtListViewHolder(@NonNull View itemView) {
            super(itemView);
            togtName = itemView.findViewById(R.id.togtoversigt_card_name);
            ship = itemView.findViewById(R.id.togtoversigt_card_shipname);
            startDest = itemView.findViewById(R.id.togtoversigt_card_startdestination);
            date = itemView.findViewById(R.id.togtoversigt_card_date);
            year = itemView.findViewById(R.id.togtoversigt_card_year);
            itemView.setOnClickListener( (View view) -> {
                int position = getAdapterPosition();
                Togt togt = togtArrayList.get(position);
//                    EtapeOversigt_frag etapeOversigt_frag = new EtapeOversigt_frag(togt);
//                    Bundle bundle = new Bundle();
//                    bundle.putLong("ID", togt.getId());
//                    etapeOversigt_frag.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.anim.enter_right_to_left,
                                R.anim.exit_right_to_left,
                                R.anim.enter_left_to_right,
                                R.anim.exit_left_to_right)
                        .replace(R.id.fragContainer,new EtapeOversigt_frag(togt))
                        .addToBackStack(null)
                        .commit();
            });
        }
    }
}














