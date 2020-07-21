package com.angga.telkomselapprenticeprogam.activities

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.angga.telkomselapprenticeprogam.R
import com.angga.telkomselapprenticeprogam.fragments.home_fragment.HomeFragment
import com.angga.telkomselapprenticeprogam.fragments.profile_fragment.ProfileFragment
import com.angga.telkomselapprenticeprogam.fragments.program_fragment.ProgramFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    companion object{ var navStatus = -1 }
    private var fragment : Fragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        if(savedInstanceState == null){ navigation.selectedItemId = R.id.navigation_home }
        supportActionBar?.hide()
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.navigation_home -> {
                if(navStatus != 0){
                    fragment = HomeFragment()
                    navStatus = 0
                }
            }
            R.id.navigation_program -> {
                if(navStatus != 1){
                    fragment = ProgramFragment()
                    navStatus = 1
                }
            }
            R.id.navigation_profile -> {
                if(navStatus != 2){
                    fragment = ProfileFragment()
                    navStatus = 2
                }
            }
        }
        if(fragment == null){
            navStatus = 0
            fragment = HomeFragment()
        }

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.screen_container, fragment!!)
        fragmentTransaction.commit()
        true
    }

}
