package br.ufpe.cin.if710.rss

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import br.ufpe.cin.if710.rss.adapters.RssAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.longToast
import org.jetbrains.anko.uiThread
import java.net.URL

class MainActivity : Activity() {

    private var RSS_FEED = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RSS_FEED = getString(R.string.rssfeed)

        // Inicia o layout da recycler view
        conteudoRSS.layoutManager = LinearLayoutManager(this)
        conteudoRSS.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

    }

    override fun onStart() {
        super.onStart()
        doAsync {
            val result = URL(RSS_FEED).readText()   // carrega texto da internet
            val itemsRss = ParserRSS.parse(result)  // faz o parsing do texto

            uiThread {
                longToast("Request performed")
                conteudoRSS.adapter = RssAdapter(itemsRss, this@MainActivity)   // passa como parâmetro a lista de ítens
            }

        }
    }



}
