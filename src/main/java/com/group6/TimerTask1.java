package com.group6;

import java.util.TimerTask;

public class TimerTask1 extends TimerTask{
    MusicPlayer mp;

    public TimerTask1(MusicPlayer mp) {
        this.mp = mp;
    }

	public void run(){
        
        if(mp.sec >= mp.timeSec && mp.min >= mp.timeMin){
            mp.timer.cancel();
        }
        if (mp.sec >= 10) {
            mp.sec = 0;
            mp.sec10++;
        }
        if(mp.sec10 >=6) {
        	mp.sec = 0;
            mp.sec10 = 0;
            mp.min++;
        }
        
        
        mp.getDisplayTimer().setText((mp.min+":"+mp.sec10+mp.sec+"/"+mp.timeDuration));
        mp.sec++;
        
        
    }



}