package com.ramadan.challenge.feature.restaurants.restaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ramadan.challenge.core.common.BaseFragment
import com.ramadan.challenge.databinding.FragmentRestaurantDetailsBinding
import com.ramadan.challenge.domain.entity.Restaurant

private const val ARG_RESTAURANT = "restaurant"

class RestaurantDetails : BaseFragment() {
    private var restaurant: Restaurant? = null
    private var _binding: FragmentRestaurantDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            bundle.getParcelable<Restaurant>(ARG_RESTAURANT).also { restaurant = it }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentRestaurantDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        restaurant?.name?.let { _binding?.name?.text = NAME.plus(it) }
        restaurant?.city?.let { _binding?.city?.text = CITY.plus(it) }
        restaurant?.address?.let { _binding?.address?.text = ADDRESS.plus(it) }
    }

    companion object {
        @JvmStatic
        fun newInstance(restaurant: Restaurant?) =
            RestaurantDetails().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_RESTAURANT, restaurant)
                }
            }

        const val NAME = "Name: "
        const val ADDRESS = "Address: "
        const val CITY = "City: "
    }
}
