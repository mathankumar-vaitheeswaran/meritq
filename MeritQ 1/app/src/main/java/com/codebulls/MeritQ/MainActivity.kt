package com.codebulls.MeritQ

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.codebulls.MeritQ.helpers.RequestsAdapter
import com.codebulls.MeritQ.helpers.singleRequest
import com.codebulls.MeritQ.ui.login.LoginActivity

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.widget.Toast
import com.codebulls.MeritQ.helpers.helperAPIDynamo


class MainActivity : AppCompatActivity() {

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var requests: List<singleRequest>
    private val mStaticList = listOf(
        singleRequest("REQ001", "R", "Y", "99"),
        singleRequest("REQ002", "R", "Y", "75"),
        singleRequest("REQ003", "R", "Y", "87"),
        singleRequest("REQ004", "A", "N", "75"),
        singleRequest("REQ005", "D", "N", "45"),
        singleRequest("REQ006", "A", "N", "75"),
        singleRequest("REQ007", "D", "Y", "44"),
        singleRequest("REQ008", "A", "Y", "75"),
        singleRequest("REQ009", "D", "Y", "75"),
        singleRequest("REQ010", "D", "Y", "56")
    )
    private var selectedItemId: Int = -1
    private lateinit var selectedItemTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //Exit Area
        if (intent.getBooleanExtra("EXIT", false)) {
            finish()
        }

        //Logout Area
        if (intent.getBooleanExtra("LOUT", false)) {
            finish()
            this@MainActivity.startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }

        val parentLayout = findViewById<View>(R.id.requestsLayout)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Add New Request", Snackbar.LENGTH_LONG)
                .setAction("NEW", NewRequestListener()).show()
        }

        requests = emptyList()

        requests = mStaticList

        if(requests.isEmpty()) {
            Snackbar.make(parentLayout, "No Requests Found!", Snackbar.LENGTH_LONG)
                .setAction("ADD NEW", NewRequestListener()).show()
        }
        viewManager = LinearLayoutManager(this@MainActivity)
        viewAdapter = RequestsAdapter(requests)

        recycleRequests.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        registerForContextMenu(recycleRequests)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_fetch -> {
                helperAPIDynamo(this@MainActivity)
                    .execute("S", "MeritQ1002", "R")
                helperAPIDynamo(this@MainActivity)
                    .execute("L", "", "R")
                true
            }
            R.id.action_settings -> {
                true
            }
            R.id.action_logout -> {
                val intentE = Intent(this@MainActivity, MainActivity::class.java)
                intentE.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                intentE.putExtra("LOUT", true)
                startActivity(intentE)
                true
            }
            R.id.action_exit -> {
                val intentE = Intent(this@MainActivity, MainActivity::class.java)
                intentE.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                intentE.putExtra("EXIT", true)
                startActivity(intentE)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_context, menu)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {

        return when (item!!.itemId) {
            R.id.context_amend ->{
                Toast.makeText(this@MainActivity, "Test: ", Toast.LENGTH_LONG).show()
                return true
            }
            R.id.context_cancel ->{
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    inner class NewRequestListener : View.OnClickListener {
        override fun onClick(v: View?) {
            this@MainActivity.startActivity(Intent(this@MainActivity, NewActivity::class.java))
        }
    }

}
