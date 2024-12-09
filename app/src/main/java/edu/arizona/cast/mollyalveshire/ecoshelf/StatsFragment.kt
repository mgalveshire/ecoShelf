package edu.arizona.cast.mollyalveshire.ecoshelf

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.charts.ScatterChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.data.ScatterData
import com.github.mikephil.charting.data.ScatterDataSet
import com.github.mikephil.charting.formatter.PercentFormatter

class StatsFragment : Fragment() {
    lateinit var wastePieChart: PieChart
    lateinit var unsoldPieChart: PieChart
    lateinit var salesScatterChart: ScatterChart
    private lateinit var inventoryViewModel: InventoryViewModel
    private lateinit var wastePotentialTextView: TextView
    private lateinit var unsoldTextView: TextView
    private lateinit var scatterLayout: LinearLayout
    private lateinit var xAxis: TextView
    private lateinit var lineChart: LineChart
    private lateinit var averageProductCost: TextView
    private lateinit var overstockProfitability: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stats, container, false)
        inventoryViewModel = ViewModelProvider(requireActivity()).get(InventoryViewModel::class.java)
        scatterLayout = view.findViewById(R.id.scatter_layout)
        xAxis = view.findViewById(R.id.xAxisTitle)
        inventoryViewModel.getTotalItemCount().observe(viewLifecycleOwner, Observer { count ->
            if (count > 0) {
                wastePieChart.visibility = View.VISIBLE
                unsoldPieChart.visibility = View.VISIBLE
                scatterLayout.visibility = View.VISIBLE
                xAxis.visibility = View.VISIBLE
            }
        })

        val inventoryTurnover: TextView = view.findViewById(R.id.inventory_turnover)
        inventoryViewModel.getExcessItemsCount().observe(viewLifecycleOwner, Observer { count ->
            if (count != null) {
                Log.d("InventoryActivity", "Total number of items in database: $count")
                // Update your UI with the count
                inventoryTurnover.text = "Inventory Turnover Ratio: $count"
            } else {
                Log.d("InventoryActivity", "Failed to get total item count.")
            }
        })

        wastePotentialTextView = view.findViewById(R.id.waste_potential)
        wastePieChart = view.findViewById(R.id.pieChart)
        inventoryViewModel.calculateWastePotential().observe(viewLifecycleOwner, Observer { resultMap ->
            // Get values from the result map
            val averageWastePotential = resultMap["averageWastePotential"] ?: 0.0
            val remainingPotential = resultMap["remainingPotential"] ?: 0.0

            // Set up pie chart properties
            wastePieChart.setUsePercentValues(true)
            wastePieChart.description.isEnabled = false
            wastePieChart.setExtraOffsets(5f, 10f, 5f, 5f)
            wastePieChart.setRotationAngle(0f)
            wastePieChart.setRotationEnabled(true)
            wastePieChart.setHighlightPerTapEnabled(true)
            wastePieChart.animateY(1400, Easing.EaseInOutQuad)
            wastePieChart.legend.isEnabled = false
            wastePieChart.setEntryLabelColor(Color.WHITE)
            wastePieChart.setEntryLabelTextSize(12f)
            wastePieChart.setEntryLabelColor(Color.BLACK)

            // Prepare the entries for the pie chart
            val entries: ArrayList<PieEntry> = ArrayList()
            entries.add(PieEntry(averageWastePotential.toFloat(), "Excess Inventory"))
            entries.add(PieEntry(remainingPotential.toFloat(), "Remaining Inventory"))

            val dataSet = PieDataSet(entries, "Inventory Breakdown")

            dataSet.sliceSpace = 3f
            dataSet.selectionShift = 5f

            val colors = ArrayList<Int>()
            colors.add(Color.parseColor("#8BC34A")) // Green for Excess Inventory
            colors.add(Color.parseColor("#B0BEC5")) // Grey for Remaining Inventory
            dataSet.colors = colors

            val data = PieData(dataSet)
            data.setValueFormatter(PercentFormatter())
            data.setValueTextSize(15f)
            data.setValueTypeface(Typeface.DEFAULT_BOLD)
            data.setValueTextColor(Color.WHITE)

            wastePieChart.data = data
            wastePieChart.invalidate()
        })


        unsoldTextView = view.findViewById(R.id.unsold_inventory_cost)
        unsoldPieChart = view.findViewById(R.id.unsoldPieChart)


        inventoryViewModel.calculateUnsoldInventoryCost().observe(viewLifecycleOwner, Observer { resultMap ->
            // Get values from the result map
            val unsoldInventoryCost = resultMap["unsoldInventoryCost"] ?: 0.0
            val remainingInventoryCost = resultMap["remainingInventoryCost"] ?: 0.0

            // Set up pie chart properties
            unsoldPieChart.setUsePercentValues(true)
            unsoldPieChart.description.isEnabled = false
            unsoldPieChart.setExtraOffsets(5f, 10f, 5f, 5f)
            unsoldPieChart.setRotationAngle(0f)
            unsoldPieChart.setRotationEnabled(true)
            unsoldPieChart.setHighlightPerTapEnabled(true)
            unsoldPieChart.animateY(1400, Easing.EaseInOutQuad)
            unsoldPieChart.legend.isEnabled = false
            unsoldPieChart.setEntryLabelColor(Color.WHITE)
            unsoldPieChart.setEntryLabelTextSize(12f)
            unsoldPieChart.setEntryLabelColor(Color.BLACK)

            // Prepare the entries for the pie chart
            val entries: ArrayList<PieEntry> = ArrayList()
            entries.add(PieEntry(unsoldInventoryCost.toFloat(), "Unsold Inventory Cost"))
            entries.add(PieEntry(remainingInventoryCost.toFloat(), "Remaining Inventory Cost"))

            // PieDataSet Configuration
            val dataSet = PieDataSet(entries, "Inventory Financial Breakdown")

            // Customizing pie slice appearance
            dataSet.sliceSpace = 3f
            dataSet.selectionShift = 5f

            // Custom colors for each category
            val colors = ArrayList<Int>()
            colors.add(Color.parseColor("#F44336")) // Red for Unsold Inventory Cost
            colors.add(Color.parseColor("#8BC34A")) // Green for Remaining Inventory Cost
            dataSet.colors = colors

            // Final PieData
            val data = PieData(dataSet)
            data.setValueFormatter(PercentFormatter())
            data.setValueTextSize(15f)
            data.setValueTypeface(Typeface.DEFAULT_BOLD)
            data.setValueTextColor(Color.WHITE)

            // Set the data to the pie chart and refresh
            unsoldPieChart.data = data
            unsoldPieChart.invalidate() // Refresh the chart
        })

        salesScatterChart = view.findViewById(R.id.scatterChart)  // Replace with your actual chart reference
        inventoryViewModel.calculateSalesEfficiency().observe(viewLifecycleOwner, Observer { scatterData ->
            // Set up scatter plot properties
            salesScatterChart.description.isEnabled = false
            salesScatterChart.setExtraOffsets(5f, 10f, 5f, 5f)
            salesScatterChart.setTouchEnabled(true)
            salesScatterChart.isDragEnabled = true
            salesScatterChart.setScaleEnabled(true)

            // Prepare the entries for the scatter plot
            val entries: ArrayList<Entry> = ArrayList()
            scatterData.forEachIndexed { index, data ->
                val salesEfficiency = data.first
                val remainingStock = data.second
                entries.add(Entry(salesEfficiency, remainingStock))
            }

            // Set up the ScatterDataSet
            val dataSet = ScatterDataSet(entries, "Sales vs. Overstock Comparison")
            dataSet.setScatterShape(ScatterChart.ScatterShape.CIRCLE)
            dataSet.color = Color.parseColor("#FF4081")  // Set the color of the scatter points
            dataSet.setDrawValues(false)  // Don't draw the values directly on the points

            // Final ScatterData
            val scatterDataFinal = ScatterData(dataSet)

            // Set data to scatter chart
            salesScatterChart.data = scatterDataFinal
            salesScatterChart.invalidate() // Refresh the chart
        })
        averageProductCost = view.findViewById(R.id.average_product_cost)
        inventoryViewModel.calculateAverageProductCost().observe(viewLifecycleOwner, Observer { averageCost ->
            // Display the average product cost in your UI (e.g., TextView)
            val formattedCost = "$${"%.2f".format(averageCost)}"  // Format as currency
            averageProductCost.text = "Average Product Cost: $formattedCost"
        })
        overstockProfitability = view.findViewById(R.id.profitability_overstock)
        inventoryViewModel.calculateExcessProfitability().observe(viewLifecycleOwner, Observer { excessProfitabilities ->
            // Handle the excess profitabilities list (e.g., display it in a chart or log it)
            excessProfitabilities.forEach {
                Log.d("Excess Profitability", "Profitability: $it")
            }

            val totalProfitability = excessProfitabilities.sum()
            val formattedProfit = "$${"%.2f".format(totalProfitability)}"  // Format as currency
            overstockProfitability.text = "Profitability of Overstocked Items: $formattedProfit"
        })


        return view
    }

}
