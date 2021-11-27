package com.gustavodmcarlos.finances.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gustavodmcarlos.finances.R
import com.gustavodmcarlos.finances.databinding.FragmentHomeBinding
import com.gustavodmcarlos.finances.ui.Transaction
import com.gustavodmcarlos.finances.ui.TransactionInfo


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val root: View = binding.root

        // set hello message
        val sharedPref = activity?.getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        val userName = sharedPref?.getString("givenName", "user")
        binding.homeTitle.text = "Hello, $userName!"

        // create transactions
        val transactions = arrayOf(
            TransactionInfo(R.drawable.ic_baseline_file_copy_24, "Getbox Plan", "Subscription", "18 Sept 2020", -144.00),
            TransactionInfo(R.drawable.ic_baseline_music_note_24, "Spotipay", "Subscription", "12 Sept 2020", -24.00),
            TransactionInfo(R.drawable.ic_baseline_play_arrow_24, "Mytube Ads", "Income", "10 Sept 2020", 32.00),
            TransactionInfo(R.drawable.ic_round_cases_24, "Freelance Work", "Income", "06 Sept 2020", 4.21)
        )

        if (container != null) {
            for (tInfo in transactions) {
                val t = Transaction(container?.context)
                t.load(tInfo)
                binding.transactionList.addView(t)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}