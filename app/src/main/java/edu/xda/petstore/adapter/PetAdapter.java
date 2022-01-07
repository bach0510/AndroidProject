package edu.xda.petstore.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.xda.petstore.R;
import edu.xda.petstore.model.Pet;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {
    Context context;
    List<Pet> petList;

    public PetAdapter(Context context, List<Pet> petList) {
        this.context = context;
        this.petList = petList;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.pet_row_item, parent, false);

        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {


        if (petList.get(position).getAnh() != null){
            byte[] blob= Base64.decode( petList.get(position).getAnh(), Base64.DEFAULT);
            Bitmap bmp= BitmapFactory.decodeByteArray(blob,0,blob.length);
//        ImageView image=new ImageView(this);
            holder.petImage.setImageBitmap(bmp);
        }
        holder.petText.setText(petList.get(position).getMaGiong() + '-' + petList.get(position).getTenGiong());
        holder.petPriceText.setText(String.valueOf(petList.get(position).getDonGia())+"VND");
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    public  static class PetViewHolder extends RecyclerView.ViewHolder{

        ImageView petImage;
        TextView petText;
        TextView petPriceText;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);

            petImage = itemView.findViewById(R.id.petImage);
            petText = itemView.findViewById(R.id.petText);
            petPriceText = itemView.findViewById(R.id.petPriceText);

        }
    }
}
