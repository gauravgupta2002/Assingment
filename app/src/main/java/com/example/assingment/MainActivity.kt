package com.example.assingment

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import com.androidplot.xy.*
import com.example.assingment.bottomnav.fragments.AddFragment
import com.example.assingment.bottomnav.fragments.CameraFragment
import com.example.assingment.bottomnav.fragments.HomeFragment
import com.example.assingment.bottomnav.fragments.PlaneFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import java.text.FieldPosition
import java.text.Format
import java.text.Normalizer
import java.text.ParsePosition
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val plot = findViewById<XYPlot>(R.id.plot)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomnav)

        val logoutbutton = findViewById<Button>(R.id.logout)
        logoutbutton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this@MainActivity,LoginActivity::class.java))
            finish()
        }

        val homeFragment = HomeFragment()
        val planeFragment = PlaneFragment()
        val addFragment = AddFragment()
        val cameraFragment = CameraFragment()
        
        makeCurrentFragment(homeFragment)
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.ic_home -> makeCurrentFragment(homeFragment)
                R.id.ic_photo ->makeCurrentFragment(cameraFragment)
                R.id.ic_plane ->makeCurrentFragment(planeFragment)
                R.id.icon_only ->makeCurrentFragment(addFragment)
            }
            true
        }

        val series1No = arrayOf<Number>(2,1,1,2,3,2,4)
        val domainLabels = arrayOf<Number>(0,1,2,3,4,5,6)

        val series1 : XYSeries = SimpleXYSeries(
            Arrays.asList(* series1No),
            SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"Series 1")

        val series1Format = LineAndPointFormatter(Color.BLUE, Color.BLACK,null,null)
        plot.addSeries(series1,series1Format)

        series1Format.setInterpolationParams(CatmullRomInterpolator.Params(10,CatmullRomInterpolator.Type.Centripetal))

        plot.graph.getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).format = object : Format(){
            override fun format(obj: Any?, p1: StringBuffer?, p2: FieldPosition?): StringBuffer {
                val i = Math.round((obj as Number).toFloat())
                return p1!!.append(domainLabels[i])
            }

            override fun parseObject(p0: String?, p1: ParsePosition?): Any? {
                return null
            }

        }

       PanZoom.attach(plot)

    }

    private fun makeCurrentFragment(fragment: Fragment) {
       supportFragmentManager.beginTransaction().apply {
           replace(R.id.flwrap,fragment)
           commit()
       }
    }
}