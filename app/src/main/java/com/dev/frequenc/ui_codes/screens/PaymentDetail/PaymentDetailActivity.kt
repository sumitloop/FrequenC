package com.dev.frequenc.ui_codes.screens.PaymentDetail

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.dev.frequenc.R
import com.dev.frequenc.ui_codes.data.AudienceDataResponse
import com.dev.frequenc.ui_codes.data.EventResponse
import com.dev.frequenc.ui_codes.data.EventTicket
import com.dev.frequenc.databinding.ActivityPaymentDetailBinding
import com.dev.frequenc.ui_codes.screens.Stripe.StripePaymentActivity

import com.dev.frequenc.ui_codes.screens.utils.ApiClient
import com.dev.frequenc.util.AppCommonMethods
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.dev.frequenc.ui_codes.util.Constants
import com.google.android.material.bottomsheet.BottomSheetBehavior
import retrofit2.Call
import retrofit2.Response
import java.io.Serializable

class PaymentDetailActivity : AppCompatActivity() {

    lateinit var binding : ActivityPaymentDetailBinding

    lateinit var authorization : String
    lateinit var audience_id : String
    private lateinit var sharedPreferences: SharedPreferences

    lateinit var  eventDetails : EventResponse

    lateinit var  item : EventTicket

    lateinit var count : String

    lateinit var audience : AudienceDataResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(Constants.SharedPreference, Context.MODE_PRIVATE)!!

        authorization =  sharedPreferences.getString(Constants.Authorization, "-1").toString()
        audience_id = sharedPreferences.getString(Constants.AudienceId,"-1").toString()


        eventDetails = intent.getSerializableExtra("eventDetail") as EventResponse

        item = intent.getSerializableExtra("item") as EventTicket

        count = intent.getStringExtra("count").toString()

        binding.ivBackBtn.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        Glide.with(this).load(eventDetails.eventDetails.eventImage[0]).into(binding.ivEvent)

        binding.tvLocation.text = eventDetails.venueDetails.venue_locality

        binding.tvEventName.text = eventDetails.eventDetails.eventTitle

        var date = eventDetails.eventDetails.eventStartDate

        binding.tvCalendar.text =  AppCommonMethods.convertDateFormat2(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'","dd MMM yyyy",date)

        binding.tvTicketDes.text = "$count Tickets (${item.ticket_type})"

        binding.tvAmount.text = "FRQ ${count!!.toInt()* item.price}"
        binding.tvTotal.text = "FRQ ${count!!.toInt()* item.price}"


        Log.d("eventid",eventDetails.eventDetails._id)


        binding.btnConfirm.setOnClickListener {
//            goToMetamask()


           showDialogPayment()

        }


        getProfileApi()

    }

    private fun showDialogPayment() {
        val dialog = BottomSheetDialog(this)
        val bottomSheet = layoutInflater.inflate(R.layout.layout_bottom_payment, null)

        dialog.behavior.peekHeight = 800




//        var ivRazor = dialog.findViewById<ImageView>(R.id.rlRazor)
//           .bs_tv_remove .setOnClickListener { dialog.dismiss() }

//        var btnPay =  bottomSheet.findViewById<Button>(R.id.payButton)
//        var tvRemove =  bottomSheet.findViewById<TextView>(R.id.bs_tv_remove)

//        tvRemove.setOnClickListener {
//            dialog.dismiss()
//            savedEventViewModel.callBookmarkEventApi(
//                position,
//                sharedPreferences.getString(Constants.Authorization, "").toString()
//            )
//        }

//        tvCancel.setOnClickListener {
//            dialog.dismiss()
//
//        }

//        btnPay.setOnClickListener {
//            Toast.makeText(this,"Stripe",Toast.LENGTH_SHORT).show()
//        }




        dialog.setContentView(bottomSheet)
        dialog.show()

        var tvConfirm = dialog.findViewById<TextView>(R.id.btnConfirm)
        var rbRazor = dialog.findViewById<RadioButton>(R.id.rbRazor)
        var rbStripe = dialog.findViewById<RadioButton>(R.id.rbStripe)


        rbRazor!!.setOnClickListener {
            rbRazor.isChecked = true
            rbStripe!!.isChecked = false

            tvConfirm!!.isEnabled = true
        }

        rbStripe!!.setOnClickListener {
            rbStripe.isChecked = true
            rbRazor!!.isChecked = false

            tvConfirm!!.isEnabled = true

        }

        tvConfirm!!.setOnClickListener {

            if(rbStripe.isChecked || rbRazor.isChecked)
            {
                val intent = Intent(this,StripePaymentActivity::class.java)
                intent.putExtra("item",item as Serializable)
                intent.putExtra("count",count)
                intent.putExtra("audience",audience as Serializable)
                intent.putExtra("eventDetail", eventDetails as Serializable)

                Log.d("eventid",eventDetails.eventDetails._id)
                startActivity(intent)
                dialog.hide()
            }
            else
                Toast.makeText(this,"Please select one payment method",Toast.LENGTH_SHORT).show()
        }

    }


    private fun getProfileApi()
    {
        binding.progressDialog.visibility = View.VISIBLE

        ApiClient.getInstance()!!.getProfile(authorization,audience_id).enqueue(object  : retrofit2.Callback<AudienceDataResponse>
        {
            override fun onResponse(
                call: Call<AudienceDataResponse>,
                response: Response<AudienceDataResponse>
            ) {
                binding.progressDialog.visibility = View.GONE
                if(response.isSuccessful && response.body()!=null)
                {
                    Log.d("Profile Api", "onResponse Retrofit Profile Data: " + response.body())
                    val res = response.body()

                    audience  = res!!

                    binding.etName.editText!!.setText(res!!.name)
                    binding.etEmail.editText!!.setText(res!!.email)
                    binding.etMobile.editText!!.setText(res!!.mobile_no)


                }
            }

            override fun onFailure(call: Call<AudienceDataResponse>, t: Throwable) {
                binding.progressDialog.visibility = View.GONE
                Log.d("Profile Api", "onFailure Retrofit: " + t.localizedMessage)


            }

        })

    }


}