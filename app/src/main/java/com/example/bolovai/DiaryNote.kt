package com.example.bolovai

class DiaryNote{
    var text:String=""
    var mood:String=""
    var time:String = ""
    var date:String = ""


    constructor(){}


    constructor(text:String,mood:String,time:String,date:String){
        this.text = text
        this.mood = mood
        this.time = time
        this.date = date

    }
}


