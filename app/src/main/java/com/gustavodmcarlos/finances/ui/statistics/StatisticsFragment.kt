package com.gustavodmcarlos.finances.ui.statistics

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.gustavodmcarlos.finances.databinding.FragmentStatisticsBinding
import com.gustavodmcarlos.finances.ui.MapsActivity

class StatisticsFragment : Fragment() {

    private lateinit var statisticsViewModel: StatisticsViewModel
    private var _binding: FragmentStatisticsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        statisticsViewModel =
            ViewModelProvider(this).get(StatisticsViewModel::class.java)

        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.openMapButton.setOnClickListener {
            val intent = Intent(activity, MapsActivity::class.java)
            startActivity(intent)
        }

        // chart images from https://quickchart.io/
        val pieChartUrl = "https://quickchart.io/chart?c=%7B%0A%20%20%22type%22%3A%20%22outlabeledPie%22%2C%0A%20%20%22data%22%3A%20%7B%0A%20%20%20%20labels%3A%20%5B%27Earned%27%2C%20%27Spent%27%5D%2C%0A%20%20%20%20%22datasets%22%3A%20%5B%7B%0A%20%20%20%20%20%20%20%20%22backgroundColor%22%3A%20%5B%22%2303ad12%22%2C%20%22%23de0d0d%22%5D%2C%0A%20%20%20%20%20%20%20%20%22borderWidth%22%3A%200%2C%0A%20%20%20%20%20%20%20%20data%3A%20%5B302%2C%20140%5D%0A%20%20%20%20%7D%5D%0A%20%20%7D%2C%0A%20%20%22options%22%3A%20%7B%0A%20%20%20%20%22plugins%22%3A%20%7B%0A%20%20%20%20%20%20%22legend%22%3A%20false%2C%0A%20%20%20%20%20%20%22outlabels%22%3A%20%7B%0A%20%20%20%20%20%20%20%20%22text%22%3A%20%22%25l%20%25p%22%2C%0A%20%20%20%20%20%20%20%20%22color%22%3A%20%22white%22%2C%0A%20%20%20%20%20%20%20%20%22stretch%22%3A%2035%2C%0A%20%20%20%20%20%20%20%20%22font%22%3A%20%7B%0A%20%20%20%20%20%20%20%20%20%20%22resizable%22%3A%20true%2C%0A%20%20%20%20%20%20%20%20%20%20%22minSize%22%3A%2012%2C%0A%20%20%20%20%20%20%20%20%20%20%22maxSize%22%3A%2018%0A%20%20%20%20%20%20%20%20%7D%0A%20%20%20%20%20%20%7D%0A%20%20%20%20%7D%0A%20%20%7D%0A%7D%0A"
        val barChartUrl = "https://quickchart.io/chart?c=%7B%0A%20%20%22type%22%3A%20%22horizontalBar%22%2C%0A%20%20%22data%22%3A%20%7B%0A%20%20%20%20%22labels%22%3A%20%5B%0A%20%20%20%20%20%20%22July%22%2C%0A%20%20%20%20%20%20%22August%22%2C%0A%20%20%20%20%20%20%22September%22%2C%0A%20%20%20%20%20%20%22October%22%2C%0A%20%20%20%20%20%20%22November%22%2C%0A%20%20%20%20%20%20%22December%22%0A%20%20%20%20%5D%2C%0A%20%20%20%20%22datasets%22%3A%20%5B%0A%20%20%20%20%20%20%7B%0A%20%20%20%20%20%20%20%20%22label%22%3A%20%22Earned%22%2C%0A%20%20%20%20%20%20%20%20%22backgroundColor%22%3A%20%22%2303ad12%22%2C%0A%20%20%20%20%20%20%20%20%22borderWidth%22%3A%201%2C%0A%20%20%20%20%20%20%20%20%22data%22%3A%20%5B%0A%20%20%20%20%20%20%20%20%20%2032%2C%0A%20%20%20%20%20%20%20%20%20%2062%2C%0A%20%20%20%20%20%20%20%20%20%2064%2C%0A%20%20%20%20%20%20%20%20%20%2041%2C%0A%20%20%20%20%20%20%20%20%20%2031%2C%0A%20%20%20%20%20%20%20%20%20%2032%0A%20%20%20%20%20%20%20%20%5D%0A%20%20%20%20%20%20%7D%2C%0A%20%20%20%20%20%20%7B%0A%20%20%20%20%20%20%20%20%22label%22%3A%20%22Spent%22%2C%0A%20%20%20%20%20%20%20%20%22backgroundColor%22%3A%20%22%23de0d0d%22%2C%0A%20%20%20%20%20%20%20%20%22data%22%3A%20%5B%0A%20%20%20%20%20%20%20%20%20%209%2C%0A%20%20%20%20%20%20%20%20%20%20100%2C%0A%20%20%20%20%20%20%20%20%20%2013%2C%0A%20%20%20%20%20%20%20%20%20%2064%2C%0A%20%20%20%20%20%20%20%20%20%2057%2C%0A%20%20%20%20%20%20%20%20%20%2026%0A%20%20%20%20%20%20%20%20%5D%0A%20%20%20%20%20%20%7D%0A%20%20%20%20%5D%0A%20%20%7D%2C%0A%20%20%22options%22%3A%20%7B%0A%20%20%20%20%22elements%22%3A%20%7B%0A%20%20%20%20%20%20%22rectangle%22%3A%20%7B%0A%20%20%20%20%20%20%20%20%22borderWidth%22%3A%202%0A%20%20%20%20%20%20%7D%0A%20%20%20%20%7D%2C%0A%20%20%20%20%22responsive%22%3A%20true%2C%0A%20%20%20%20%22legend%22%3A%20%7B%0A%20%20%20%20%20%20%22position%22%3A%20%22right%22%0A%20%20%20%20%7D%2C%0A%20%20%20%20%22title%22%3A%20%7B%0A%20%20%20%20%20%20%22display%22%3A%20true%2C%0A%20%20%20%20%20%20%22text%22%3A%20%22Second%20semester%20report%202020%22%0A%20%20%20%20%7D%0A%20%20%7D%0A%7D"

        Glide.with(this)
            .load(pieChartUrl)
            .into(binding.pieChart)

        Glide.with(this)
            .load(barChartUrl)
            .into(binding.barChart)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}