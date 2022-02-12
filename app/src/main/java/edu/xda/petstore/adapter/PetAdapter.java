package edu.xda.petstore.adapter;

import android.content.Context;
import android.content.Intent;
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

import edu.xda.petstore.PetDetail;
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

        View view = LayoutInflater.from(context).inflate(R.layout.pet_row_item, parent, false);/// sử dụng layout pet_row_item

        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {

        // nếu ảnh của pet khác null thì set ảnh cho items
        if (petList.get(position).getAnh() != null){
            byte[] blob= Base64.decode( petList.get(position).getAnh(), Base64.DEFAULT);
            Bitmap bmp= BitmapFactory.decodeByteArray(blob,0,blob.length);
//        ImageView image=new ImageView(this);
            holder.petImage.setImageBitmap(bmp);
        }
        holder.petText.setText(petList.get(position).getMaGiong() + '-' + petList.get(position).getTenGiong());
        holder.petPriceText.setText(String.valueOf(petList.get(position).getDonGia())+" VNĐ");

        holder.itemView.setOnClickListener(new View.OnClickListener(){// bắt sụ kiện mở ra màn chi tiết thú cưng

            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PetDetail.class);
                // truyền tham số cho màn chi tiết
                i.putExtra("maGiong",petList.get(position).getMaGiong());
                i.putExtra("tenGiong",petList.get(position).getTenGiong());
                i.putExtra("moTa",petList.get(position).getMoTa());
                i.putExtra("donGia",petList.get(position).getDonGia());
                i.putExtra("anh",petList.get(position).getAnh());
                i.putExtra("id",petList.get(position).getId());
                context.startActivity(i);// mở activity màn chi tiết
            }
        });
    }

    @Override
    public int getItemCount() {
        return petList.size();
    } // lấy ra số lượng pet list

    public  static class PetViewHolder extends RecyclerView.ViewHolder{ // holder để khai báo view của activity

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
