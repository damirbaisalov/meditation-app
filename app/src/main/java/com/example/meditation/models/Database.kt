package com.example.meditation.models

object Database {
    //DATABASE FOR LIST OF VIDEOMATERIALS
    val meditationVideoDatabase : MutableList<MeditationVideoData> = mutableListOf()

    init {

        val meditationVideoDataId = arrayListOf<String>()
        val meditationVideoDataImage = arrayListOf<String>()
        val meditationVideoDataTitle = arrayListOf<String>()
        val meditationVideoDataLink = arrayListOf<String>()

        meditationVideoDataId.add("mD3QwerSmLs")
        meditationVideoDataImage.add("https://img.youtube.com/vi/mD3QwerSmLs/0.jpg")
        meditationVideoDataTitle.add("Руководство по дыхательному методу Вима Хофа")
        meditationVideoDataLink.add("https://www.youtube.com/watch?v=mD3QwerSmLs")

        meditationVideoDataId.add("kndqIj8Qgok")
        meditationVideoDataImage.add("https://img.youtube.com/vi/kndqIj8Qgok/0.jpg")
        meditationVideoDataTitle.add("Спокойная Музыка Для Медитации")
        meditationVideoDataLink.add("https://www.youtube.com/watch?v=kndqIj8Qgok")

        meditationVideoDataId.add("f6Bo-J9r94c")
        meditationVideoDataImage.add("https://img.youtube.com/vi/f6Bo-J9r94c/0.jpg")
        meditationVideoDataTitle.add("Медитация глубокого расслабления - Шавасана")
        meditationVideoDataLink.add("https://www.youtube.com/watch?v=f6Bo-J9r94c")

        meditationVideoDataId.add("CO2behcu9Go")
        meditationVideoDataImage.add("https://img.youtube.com/vi/CO2behcu9Go/0.jpg")
        meditationVideoDataTitle.add("МЕДИТАЦИЯ ДЛЯ НАЧИНАЮЩИХ!")
        meditationVideoDataLink.add("https://www.youtube.com/watch?v=CO2behcu9Go")

        meditationVideoDataId.add("0HySud3G97g")
        meditationVideoDataImage.add("https://img.youtube.com/vi/0HySud3G97g/0.jpg")
        meditationVideoDataTitle.add("МЕДИТАЦИЯ ДЛЯ НАЧИНАЮЩИХ!")
        meditationVideoDataLink.add("https://www.youtube.com/watch?v=0HySud3G97g")

        meditationVideoDataId.add("yUoXLaZshXY")
        meditationVideoDataImage.add("https://img.youtube.com/vi/yUoXLaZshXY/0.jpg")
        meditationVideoDataTitle.add("ХООПОНОПОНО МЕДИТАЦИЯ")
        meditationVideoDataLink.add("https://www.youtube.com/watch?v=yUoXLaZshXY")

        meditationVideoDataId.add("nxe28UxAOds")
        meditationVideoDataImage.add("https://img.youtube.com/vi/nxe28UxAOds/0.jpg")
        meditationVideoDataTitle.add("Как медитировать?")
        meditationVideoDataLink.add("https://www.youtube.com/watch?v=nxe28UxAOds")

        meditationVideoDataId.add("1VKDQraF82Y")
        meditationVideoDataImage.add("https://img.youtube.com/vi/1VKDQraF82Y/0.jpg")
        meditationVideoDataTitle.add("Иша крийя — направленная медитация с Садхгуру")
        meditationVideoDataLink.add("https://www.youtube.com/watch?v=1VKDQraF82Y")

        meditationVideoDataId.add("SvUtP7LWL5Y")
        meditationVideoDataImage.add("https://img.youtube.com/vi/SvUtP7LWL5Y/0.jpg")
        meditationVideoDataTitle.add("МЕДИТАЦИЯ ДЛЯ НАЧИНАЮЩИХ - Тренировка концентрации внимания | neofit 69")
        meditationVideoDataLink.add("https://www.youtube.com/watch?v=SvUtP7LWL5Y")

        meditationVideoDataId.add("y3DjEepGlkQ")
        meditationVideoDataImage.add("https://img.youtube.com/vi/y3DjEepGlkQ/0.jpg")
        meditationVideoDataTitle.add("Медитация для начинающих | Эти 15 минут в день изменят вашу жизнь (практика)")
        meditationVideoDataLink.add("https://www.youtube.com/watch?v=y3DjEepGlkQ")

        meditationVideoDataId.add("gpV_nshKfIM")
        meditationVideoDataImage.add("https://img.youtube.com/vi/gpV_nshKfIM/0.jpg")
        meditationVideoDataTitle.add("Как Медитировать (Медитация для Начинающих)")
        meditationVideoDataLink.add("https://www.youtube.com/watch?v=gpV_nshKfIM")

        meditationVideoDataId.add("qK1qhKVqj7o")
        meditationVideoDataImage.add("https://img.youtube.com/vi/qK1qhKVqj7o/0.jpg")
        meditationVideoDataTitle.add("Медитация \"Комната Дыхания\" 8 мин для успокоения и гармонии | Йога chilelavida")
        meditationVideoDataLink.add("https://www.youtube.com/watch?v=qK1qhKVqj7o")

        for (i in 0 until meditationVideoDataId.size){
            val meditationVideoData =  MeditationVideoData(
                id = meditationVideoDataId[i],
                image = meditationVideoDataImage[i],
                title = meditationVideoDataTitle[i],
                link = meditationVideoDataLink[i]
            )
            meditationVideoDatabase.add(meditationVideoData)
        }
    }
}