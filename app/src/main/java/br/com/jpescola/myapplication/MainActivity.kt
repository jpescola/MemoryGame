package br.com.jpescola.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    private lateinit var primeiraImg: ImageView
    private lateinit var segundaImg: ImageView
    private var primeiraEscolha = -1
    private var segundaEscolha = -1
    private var cartas = IntArray(12)
    private var jogadas = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // definindo as cartas
        cartas[0] = android.R.drawable.btn_star
        cartas[1] = android.R.drawable.btn_star
        cartas[2] = android.R.drawable.ic_menu_edit
        cartas[3] = android.R.drawable.ic_menu_edit
        cartas[4] = android.R.drawable.ic_menu_camera
        cartas[5] = android.R.drawable.ic_menu_camera
        cartas[6] = android.R.drawable.ic_menu_call
        cartas[7] = android.R.drawable.ic_menu_call
        cartas[8] = android.R.drawable.ic_menu_manage
        cartas[9] = android.R.drawable.ic_menu_manage
        cartas[10] = android.R.drawable.ic_menu_myplaces
        cartas[11] = android.R.drawable.ic_menu_myplaces

        // embaralha
        cartas.shuffle()
    }

    fun novo(){
        finish(); // fecha o app
        startActivity(intent); // inicia o app atual
    }

    fun play(view: View){
        // armazena a img escolhida
        val img = findViewById<ImageView>(view.id)
        if (!img.isEnabled) // ignora clicks em cartas viradas
            return

        // verifica a carta escolhida
        val escolha = view.tag.toString().toInt()
        // mostra a carta
        img.setImageResource(cartas[escolha])
        // atualiza o score
        title = "${getString(R.string.app_name)} - jogadas: ${++jogadas}"

        // na terceira escolha, verifica o resultado
        if (primeiraEscolha != -1 && segundaEscolha != -1){ // verifica se acertou
            if (cartas[primeiraEscolha] != cartas[segundaEscolha]){ // errou
                primeiraImg.setImageResource(android.R.drawable.ic_menu_help)
                segundaImg.setImageResource(android.R.drawable.ic_menu_help)
                primeiraImg.isEnabled = true
                segundaImg.isEnabled = true
            }
            primeiraEscolha = -1
            segundaEscolha = -1
        }

        // primeira e segunda escolhas
        if (primeiraEscolha == -1) {
            primeiraEscolha = escolha
            primeiraImg = img
            primeiraImg.isEnabled = false

        }
        else{
            segundaEscolha = escolha
            segundaImg = img
            segundaImg.isEnabled = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.game_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mnuNovo)
            novo()
        else if (item.itemId == R.id.mnuSair)
            finish()
        return super.onOptionsItemSelected(item)
    }
}