package fr.isen.lombardo.androiderestaurant.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.lombardo.androiderestaurant.R
import fr.isen.lombardo.androiderestaurant.databinding.ActivityDetailsBinding
import fr.isen.lombardo.androiderestaurant.databinding.DishesCellBinding
import fr.isen.lombardo.androiderestaurant.models.Item


class CategoryAdapter(private val entries: List<Item>,
                      private val entryClickListener: (Item) -> Unit)
    : RecyclerView.Adapter<CategoryAdapter.DishesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishesViewHolder {
        return DishesViewHolder(
            DishesCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: DishesViewHolder, position: Int) {
        val dish = entries[position]
        holder.layout.setOnClickListener {
            entryClickListener.invoke(dish)
        }
        holder.bind(dish)
    }

    override fun getItemCount(): Int {
        return entries.count()
    }

    class DishesViewHolder(dishesBinding: DishesCellBinding): RecyclerView.ViewHolder(dishesBinding.root) {
        private val titleDish: TextView = dishesBinding.dishesTitle
        private val priceDish: TextView = dishesBinding.dishPrice
        private val imageDish: ImageView = dishesBinding.dishImage

        val layout = dishesBinding.root

        fun bind(dish: Item) {
            titleDish.text = dish.name
            priceDish.text = "${dish.prices.first().price} €"// dish.prices.first().price + " €"

            Picasso.get()
                    .load(dish.getFirstPicture())
                    .placeholder(R.drawable.carpaccio)
                    .resize(400, 200)
                    .into(imageDish)
                    //.error(R.drawable.villa_soleil)
        }
    }

}