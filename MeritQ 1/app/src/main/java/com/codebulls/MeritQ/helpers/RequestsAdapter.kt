package com.codebulls.MeritQ.helpers

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.recycler_row_request.view.*
import com.codebulls.MeritQ.R


class RequestsAdapter(private var requests: List<singleRequest>) : RecyclerView.Adapter<RequestsAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val requestLayout = layoutInflater.inflate(R.layout.recycler_row_request, parent, false)

        return MyViewHolder(requestLayout)

    }

    override fun getItemCount(): Int {
        return requests.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (requests.isEmpty()) {
            Toast.makeText(holder.itemView.context, "No Requests Found!", Toast.LENGTH_LONG).show()
        }
        holder.itemView.txtReqID.text = "Request ID: " + requests[position].requestID
        holder.itemView.txtRequestStatus.text = "Status: " + requests[position].requestStatus
        holder.itemView.txtRequestEligibility.text = "Eligibility: " + requests[position].requestEligibility
        holder.itemView.txtRequestMerit.text = "Merit: " + requests[position].requestMerit

        holder.itemView.setOnLongClickListener {
            holder.adapterPosition
            false
        }

    }

    override fun onViewRecycled(holder: MyViewHolder) {
        holder.itemView.setOnLongClickListener(null)
        super.onViewRecycled(holder)
    }

}