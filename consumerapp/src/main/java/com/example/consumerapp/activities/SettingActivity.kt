package com.example.consumerapp.activities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import com.example.consumerapp.R
import com.example.consumerapp.alarm.AlarmReceiver
import com.example.consumerapp.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        const val PREFS_NAME = "settingPref"
        private const val DAILY = "daily"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBarTitle()

        alarmReceiver = AlarmReceiver()
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if(sharedPreferences.getBoolean(DAILY, false)){
            binding.switchAlarm.isChecked = true
        }
        binding.switchAlarm.setOnCheckedChangeListener(this)

    }

    private fun setActionBarTitle() {
        if (supportActionBar != null){
            supportActionBar?.title = "Setting"
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if(isChecked) {
            alarmReceiver.setAlarm(this,AlarmReceiver.TYPE_DAILY,getString(R.string.daily_message))
        }else {
            alarmReceiver.setAlarmCanceled(this)
        }
        saveChange(isChecked)
    }

    private fun saveChange(checked: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(DAILY, checked)
        editor.apply()
    }
}