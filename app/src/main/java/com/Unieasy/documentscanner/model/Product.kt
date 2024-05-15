package com.tonyxlh.documentscanner.model

class Product {

    var name:String = ""
    var deadline:String = ""
    var grade:String = ""
    var imageUrl:String = ""
    var id:String = ""

    constructor(name: String, deadline: String, grade: String, imageUrl: String, id: String) {
        this.name = name
        this.deadline = deadline
        this.grade = grade
        this.imageUrl = imageUrl
        this.id = id
    }

    constructor()
}