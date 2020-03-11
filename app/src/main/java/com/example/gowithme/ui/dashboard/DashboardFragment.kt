package com.example.gowithme.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gowithme.R
import com.example.gowithme.network.ApiRepository
import com.example.gowithme.responses.Event
import com.google.gson.Gson
import java.io.IOException


class DashboardFragment : Fragment() {

    private val dashboardViewModel by lazy {
        ViewModelProviders.of(this, DashboardViewModel.DashboardFactory(ApiRepository()))
            .get(DashboardViewModel::class.java)
    }
    private var adapter: EventsAdapter? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        observeTheFields()
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    private fun observeTheFields() {
        dashboardViewModel.events.observe(viewLifecycleOwner, Observer { listOfEvents ->
            adapter?.initTheList(listOfEvents)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
//        makeRequest()
        setEventsLocally()
    }

    private fun setEventsLocally() {
        val jsonStr: String? = loadJsonFromAsset()
        val gson = Gson()
        val clicks =
            gson.fromJson<Array<Event>>(jsonStr, Array<Event>::class.java)
        var list = ArrayList<Event>()

        for (click in clicks){
            list.add(click)
        }
        dashboardViewModel.events.value = list
    }

    private fun loadJsonFromAsset() : String?{
        var json: String? = null
        try {
            val inst = context!!.assets.open("[\n  {\n    \"id\": 1,\n    \"date\": \"2020-01-15T18:00:00Z\",\n    \"price\": \"7500\",\n    \"title\": \"Посиделки в кафе\",\n    \"category\": [],\n    \"description\": \"Артур: – Один из наиболее признанных комиков СНГ – Резидент «Стендапа на ТНТ» – Участник и креативный продюссер стендап-шоу «22 комика» – Соведущий YouTube Шоу «Что было дальше?» – Один из немногих комиков, выступивших со своим материалом на «Вечернем Урганте» – Создатель телеграмм канала «Бывшая» – Автор и ведущий YouTube-шоу «Вечервечер»\",\n    \"location\": {\n      \"latitude\": \"43.254744\",\n      \"longitude\": \"76.870988\"\n    },\n    \"images\": {\n      \"poster_url\": \"https://avatars.mds.yandex.net/get-pdb/1025580/2927f190-b50f-48c3-bae6-69783c904c46/s1200\",\n      \"carousel_url1\": \"https://i.ytimg.com/vi/i7OE9Rt_94w/maxresdefault.jpg\",\n      \"carousel_url2\": \"https://ticketon.kz/files/media/14505u30705_stendap-artura-chaparyana3.jpg\",\n      \"carousel_url3\": null,\n      \"carousel_url4\": null,\n      \"carousel_url5\": null\n    },\n    \"comments\": []\n  },\n  {\n    \"id\": 2,\n    \"date\": \"2020-01-15T18:00:00Z\",\n    \"price\": \"7500\",\n    \"title\": \"Выступление Артура Чапаряна\",\n    \"category\": [\n      \"Концерты\",\n      \"Живая музыка\",\n      \"Благотворительность\"\n    ],\n    \"description\": \"Артур: – Один из наиболее признанных комиков СНГ – Резидент «Стендапа на ТНТ» – Участник и креативный продюссер стендап-шоу «22 комика» – Соведущий YouTube Шоу «Что было дальше?» – Один из немногих комиков, выступивших со своим материалом на «Вечернем Урганте» – Создатель телеграмм канала «Бывшая» – Автор и ведущий YouTube-шоу «Вечервечер»\",\n    \"location\": {\n      \"latitude\": \"43.254744\",\n      \"longitude\": \"76.870988\"\n    },\n    \"images\": {\n      \"poster_url\": \"https://ticketon.kz/files/media/14505u30705_stendap-artura-chaparyana.jpg\",\n      \"carousel_url1\": \"https://i.ytimg.com/vi/i7OE9Rt_94w/maxresdefault.jpg\",\n      \"carousel_url2\": \"https://ticketon.kz/files/media/14505u30705_stendap-artura-chaparyana3.jpg\",\n      \"carousel_url3\": null,\n      \"carousel_url4\": null,\n      \"carousel_url5\": null\n    },\n    \"comments\": []\n  },\n  {\n    \"id\": 3,\n    \"date\": \"2020-01-15T18:00:00Z\",\n    \"price\": \"7500\",\n    \"title\": \"Илья Варламов\",\n    \"category\": [\n      \"Концерты\",\n      \"Живая музыка\",\n      \"Благотворительность\",\n      \"Туризм\"\n    ],\n    \"description\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised\",\n    \"location\": {\n      \"latitude\": \"43.259936\",\n      \"longitude\": \"76.931850\"\n    },\n    \"images\": {\n      \"poster_url\": \"https://ticketon.kz/files/media/14505u30705_stendap-artura-chaparyana.jpg\",\n      \"carousel_url1\": \"https://i.ytimg.com/vi/i7OE9Rt_94w/maxresdefault.jpg\",\n      \"carousel_url2\": \"https://ticketon.kz/files/media/14505u30705_stendap-artura-chaparyana3.jpg\",\n      \"carousel_url3\": null,\n      \"carousel_url4\": null,\n      \"carousel_url5\": null\n    },\n    \"comments\": [\n      {\n        \"parent_comment\": null,\n        \"date\": \"2020-01-15T18:00:00Z\",\n        \"message\": \"Смешно шутит комик, иду во второй раз\",\n        \"user_id\": 1\n      }\n    ]\n  },\n  {\n    \"id\": 4,\n    \"date\": \"2020-01-28T16:00:00Z\",\n    \"price\": \"6000\",\n    \"title\": \"Stand up\",\n    \"category\": [\n      \"Живая музыка\",\n      \"Благотворительность\",\n      \"Туризм\",\n      \"Горы\"\n    ],\n    \"description\": \"В 2005 году в эфир ТНТ вышел первый выпуск проекта, который за годы своего существования изменил представления о юморе. Сегодня Comedy Club — это больше, чем одно из самых популярных юмористических шоу в стране. Это всегда актуальные темы, эксперименты с жанром и топовые гости. Сотни выпусков, тысячи номеров, миллионы шуток — и это еще далеко не конец.  В 2017 году Comedy Club получил премию ТЭФИ в номинации «Юмористическая программа»\",\n    \"location\": {\n      \"latitude\": \"43.259936\",\n      \"longitude\": \"76.931850\"\n    },\n    \"images\": {\n      \"poster_url\": \"https://www.google.com/url?sa=i&url=https%3A%2F%2Fliveam.tv%2Fcomedy-club.html&psig=AOvVaw07o1ooytzhEGlbjahkm2If&ust=1580832202842000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCJC-oYLhtecCFQAAAAAdAAAAABAD\",\n      \"carousel_url1\": \"https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3DC33of3Xwgj8&psig=AOvVaw07o1ooytzhEGlbjahkm2If&ust=1580832202842000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCJC-oYLhtecCFQAAAAAdAAAAABAJ\",\n      \"carousel_url2\": \"https://www.google.com/url?sa=i&url=https%3A%2F%2Fkino.rambler.ru%2Ftv%2F42097160-kak-slozhilas-sudba-byvshih-rezidentov-comedy-club%2F&psig=AOvVaw07o1ooytzhEGlbjahkm2If&ust=1580832202842000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCJC-oYLhtecCFQAAAAAdAAAAABAP\",\n      \"carousel_url3\": \"https://www.google.com/url?sa=i&url=https%3A%2F%2Frutube.ru%2Fmetainfo%2Ftv%2F10%2Fseason12%2F&psig=AOvVaw07o1ooytzhEGlbjahkm2If&ust=1580832202842000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCJC-oYLhtecCFQAAAAAdAAAAABAV\",\n      \"carousel_url4\": null,\n      \"carousel_url5\": null\n    },\n    \"comments\": [\n      {\n        \"parent_comment\": null,\n        \"date\": \"2020-01-15T18:00:00Z\",\n        \"message\": \"Смешно шутит комик, иду во второй раз\",\n        \"user_id\": 1\n      }\n    ]\n  },\n  {\n    \"id\": 5,\n    \"date\": \"2020-01-15T18:00:00Z\",\n    \"price\": \"9500\",\n    \"title\": \"Концерт Рамштайн\",\n    \"category\": [\n      \"Концерты\",\n      \"Живая музыка\",\n      \"Благотворительность\",\n      \"Туризм\"\n    ],\n    \"description\": \"29 июля в Лужниках прошел концерт Rammstein в рамках европейского тура 2019 года.  Изначально он должен был состояться в ВТБ Арене, но из-за огромного ажиотажа организаторы выбрали самую крупную площадку в Москве.  Я не мог пропустить это событие, и приехал из другого города специально на этот концерт. Скажу сразу – остался в полном восторге. Смотрите, как это было\",\n    \"location\": {\n      \"latitude\": \"43.254744\",\n      \"longitude\": \"76.870988\"\n    },\n    \"images\": {\n      \"poster_url\": \"https://mir24.tv/uploaded/images/2019/July/2e43fe590a4150e8accdd16811fc3a86d578668bd4c342b000f014e3bcc18005.jpg\",\n      \"carousel_url1\": \"https://i.ytimg.com/vi/SU1rTUpaoGU/maxresdefault.jpg\",\n      \"carousel_url2\": \"https://cs11.pikabu.ru/post_img/big/2019/06/23/9/1561300336185025047.jpg\",\n      \"carousel_url3\": \"https://4.bp.blogspot.com/-r-Ius_VbJ_Y/XTbV6HPl31I/AAAAAAAAK7U/Vw-Cs7YHj1gZtvjB3KOodNnYO112cK4pQCLcBGAs/s640/48f1085.jpg\",\n      \"carousel_url4\": null,\n      \"carousel_url5\": null\n    },\n    \"comments\": [\n      {\n        \"parent_comment\": null,\n        \"date\": \"2020-01-15T18:00:00Z\",\n        \"message\": \"Смешно шутит комик, иду во второй раз\",\n        \"user_id\": 1\n      }\n    ]\n  }\n]")

            val size = inst.available()
            val buffer = ByteArray(size)
            inst.read(buffer)
            inst.close()

            json = String(buffer)

        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return json
    }

    private fun initRecycler() {
        adapter = EventsAdapter(
            _onClick = {
                d("___", "sadasd")
            },
            _context = (activity as Context)
        )
        recyclerView = view?.findViewById(R.id.recycler)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(activity)
    }
    private fun makeRequest() = dashboardViewModel.getEvents()
}