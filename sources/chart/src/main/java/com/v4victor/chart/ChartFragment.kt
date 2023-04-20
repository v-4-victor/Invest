package com.v4victor.chart

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.v4victor.chart.databinding.ChartFragmentBinding
import com.v4victor.core.dto.Chart
import com.v4victor.core.dto.CompanyProfileHolder
import com.v4victor.core.dto.StringHolder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChartFragment: Fragment() {
    @Inject
    lateinit var companyProfileHolder: CompanyProfileHolder
    private val companyProfile by lazy { companyProfileHolder.companyProfile }
    private lateinit var viewModel: ChartViewModel
    private lateinit var binding: ChartFragmentBinding
    private lateinit var company:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ChartFragmentBinding.inflate(inflater)
        company = companyProfile.symbol


        viewModel = ViewModelProvider(this)[ChartViewModel::class.java]
        viewModel.properties.observe(viewLifecycleOwner)
        {
            initChart(binding.chart, it)
        }
        viewModel.status.observe(viewLifecycleOwner)
        {
            if (it == ChartViewModel.ApiStatus.LOADING) {
                binding.progressBar.visibility = View.VISIBLE
                binding.chart.visibility = View.INVISIBLE
            }
            if (it == ChartViewModel.ApiStatus.DONE) {
                binding.progressBar.visibility = View.INVISIBLE
                binding.chart.visibility = View.VISIBLE
            }
            if (it == ChartViewModel.ApiStatus.ERROR) {
                binding.chart.visibility = View.VISIBLE
                binding.progressBar.visibility = View.INVISIBLE
            }
        }
        binding.favouriteButton.setImageResource(icon(companyProfile))
        binding.day.setOnClickListener { viewModel.getChartData(company, "D") }
        binding.week.setOnClickListener { viewModel.getChartData(company, "W") }
        binding.month.setOnClickListener { viewModel.getChartData(company, "M") }
        binding.halfYear.setOnClickListener { viewModel.getChartData(company, "6M") }
        binding.year.setOnClickListener { viewModel.getChartData(company, "Y") }
        binding.favouriteButton.setOnClickListener {
            Log.d("Chart", companyProfile.toString())
            companyProfile.favourite = !companyProfile.favourite
            binding.favouriteButton.setImageResource(icon(companyProfile))
        }
        viewModel.getChartData(
            company,
            "Y"
        )

        Log.d("Chart", company)
        return binding.root
    }

    private fun initChart(chart: CandleStickChart, data: Chart) {
        chart.setBackgroundColor(Color.TRANSPARENT)
        chart.description.isEnabled = false

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60)

        // scaling can now only be done on x- and y-axis separately
        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false)

        chart.setDrawGridBackground(false)

        val xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)

        val leftAxis = chart.axisLeft
        leftAxis.setLabelCount(7, false)
        leftAxis.setDrawGridLines(false)
        leftAxis.setDrawAxisLine(false)

        val rightAxis = chart.axisRight
        rightAxis.isEnabled = false
        setData(chart, data)
        chart.legend.isEnabled = false

//        chart.animateY(1000)
        chart.invalidate()
    }

    private fun setData(chart: CandleStickChart, data: Chart) {
        chart.resetTracking()
        val candles = mutableListOf<CandleEntry>()
        repeat(data.closePrice.size)
        {
            candles.add(
                CandleEntry(
                    it.toFloat(),
                    data.highPrice[it].toFloat(),
                    data.lowPrice[it].toFloat(),
                    data.openPrice[it].toFloat(),
                    data.closePrice[it].toFloat(),
                    R.drawable.ic_launcher_background
                )
            )
        }
        val candleDataSet = CandleDataSet(candles, "Data Set")
        with(candleDataSet)
        {
            setDrawIcons(false)
            axisDependency = YAxis.AxisDependency.LEFT
            shadowColor = Color.DKGRAY
            shadowWidth = 0.7f
            decreasingColor = Color.rgb(255, 0, 0)
            decreasingPaintStyle = Paint.Style.FILL
            increasingColor = Color.rgb(122, 242, 84)
            increasingPaintStyle = Paint.Style.FILL
            neutralColor = Color.BLUE
        }

        val fullData = CandleData(candleDataSet)
        chart.data = fullData
        chart.invalidate()
    }


}