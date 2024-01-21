package com.y.wirelesstemperaturemeasurement.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController


object NavHostViewModel : ViewModel() {
    lateinit var navHostController: NavHostController
        private set
    fun navHost(navHostController: NavHostController) {
        this.navHostController = navHostController
    }
    fun navigate(route: String) {
        navHostController.navigate(route)
    }
    fun popBackStack() {
        navHostController.popBackStack()
    }
}