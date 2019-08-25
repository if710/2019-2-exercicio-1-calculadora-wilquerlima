package br.ufpe.cin.android.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var operacao = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setandos listeners para os elementos do xml
        btn_0.setOnClickListener(this)
        btn_1.setOnClickListener(this)
        btn_2.setOnClickListener(this)
        btn_3.setOnClickListener(this)
        btn_4.setOnClickListener(this)
        btn_5.setOnClickListener(this)
        btn_6.setOnClickListener(this)
        btn_7.setOnClickListener(this)
        btn_8.setOnClickListener(this)
        btn_9.setOnClickListener(this)
        btn_Divide.setOnClickListener(this)
        btn_Multiply.setOnClickListener(this)
        btn_Subtract.setOnClickListener(this)
        btn_Add.setOnClickListener(this)
        btn_Equal.setOnClickListener(this)
        btn_Dot.setOnClickListener(this)
        btn_LParen.setOnClickListener(this)
        btn_RParen.setOnClickListener(this)
        btn_Power.setOnClickListener(this)
        btn_Clear.setOnClickListener(this)

        //capturando os valores apos mudança de estado
        if (savedInstanceState != null) {
            operacao = savedInstanceState.getString("valor_calc") as String
            text_calc.setText(operacao)
            text_info.text = operacao
        }
    }

    //salvando valores apos mudança de estado
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("valor_calc", operacao)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_0 -> {
                operacao += "0"
                text_calc.setText(operacao)
                text_info.text = text_calc.text.toString()
            }
            R.id.btn_1 -> {
                operacao += "1"
                text_calc.setText(operacao)
                text_info.text = text_calc.text.toString()
            }
            R.id.btn_2 -> {
                operacao += "2"
                text_calc.setText(operacao)
                text_info.text = text_calc.text.toString()
            }
            R.id.btn_3 -> {
                operacao += "3"
                text_calc.setText(operacao)
                text_info.text = text_calc.text.toString()
            }
            R.id.btn_4 -> {
                operacao += "4"
                text_calc.setText(operacao)
                text_info.text = text_calc.text.toString()
            }
            R.id.btn_5 -> {
                operacao += "5"
                text_calc.setText(operacao)
                text_info.text = text_calc.text.toString()
            }
            R.id.btn_6 -> {
                operacao += "6"
                text_calc.setText(operacao)
                text_info.text = text_calc.text.toString()
            }
            R.id.btn_7 -> {
                operacao += "7"
                text_calc.setText(operacao)
                text_info.text = text_calc.text.toString()
            }
            R.id.btn_8 -> {
                operacao += "8"
                text_calc.setText(operacao)
                text_info.text = text_calc.text.toString()
            }
            R.id.btn_9 -> {
                operacao += "9"
                text_calc.setText(operacao)
                text_info.text = text_calc.text.toString()
            }
            R.id.btn_Divide -> {
                operacao += "/"
                text_calc.setText(operacao)
                text_info.text = text_calc.text.toString()
            }
            R.id.btn_Multiply -> {
                operacao += "*"
                text_calc.setText(operacao)
                text_info.text = text_calc.text.toString()
            }
            R.id.btn_Subtract -> {
                operacao += "-"
                text_calc.setText(operacao)
                text_info.text = text_calc.text.toString()
            }
            R.id.btn_Add -> {
                operacao += "+"
                text_calc.setText(operacao)
                text_info.text = text_calc.text.toString()
            }
            R.id.btn_Equal -> {
                text_calc.setText(eval(operacao).toString())
                //comentado pq ao realizar uma operacao, a string ela
                //limpada para quando fosse digitado novamente outra operacao,
                // o campo ja estivesse limpo para n ter que apagar para digitar
                // mas para salvar a operacao na mudança de estava, foi necessario comentar
                //operacao = ""
            }
            R.id.btn_Dot -> {
                operacao += "."
                text_calc.setText(operacao)
                text_info.text = text_calc.text.toString()
            }
            R.id.btn_LParen -> {
                operacao += "("
                text_calc.setText(operacao)
                text_info.text = text_calc.text.toString()
            }
            R.id.btn_RParen -> {
                operacao += ")"
                text_calc.setText(operacao)
                text_info.text = text_calc.text.toString()
            }
            R.id.btn_Power -> {
                operacao += "^"
                text_calc.setText(operacao)
                text_info.text = text_calc.text.toString()
            }
            R.id.btn_Clear -> {
                operacao = ""
                text_calc.setText(operacao)
                text_info.text = text_calc.text.toString()
            }
        }
    }

    //Como usar a função:
    // eval("2+2") == 4.0
    // eval("2+3*4") = 14.0
    // eval("(2+3)*4") = 20.0
    //Fonte: https://stackoverflow.com/a/26227947
    private fun eval(str: String): Double {
        return object : Any() {
            var pos = -1
            var ch: Char = ' '
            fun nextChar() {
                val size = str.length
                ch = if ((++pos < size)) str[pos] else (-1).toChar()
            }

            fun eat(charToEat: Char): Boolean {
                while (ch == ' ') nextChar()
                if (ch == charToEat) {
                    nextChar()
                    return true
                }
                return false
            }

            fun parse(): Double {
                nextChar()
                val x = parseExpression()
                if (pos < str.length) Toast.makeText(this@MainActivity, "Caractere inesperado: $ch", Toast.LENGTH_LONG).show() //throw RuntimeException("Caractere inesperado: " + ch)
                return x
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            // | number | functionName factor | factor `^` factor
            fun parseExpression(): Double {
                var x = parseTerm()
                while (true) {
                    when {
                        eat('+') -> x += parseTerm() // adição
                        eat('-') -> x -= parseTerm() // subtração
                        else -> return x
                    }
                }
            }

            fun parseTerm(): Double {
                var x = parseFactor()
                while (true) {
                    when {
                        eat('*') -> x *= parseFactor() // multiplicação
                        eat('/') -> x /= parseFactor() // divisão
                        else -> return x
                    }
                }
            }

            fun parseFactor(): Double {
                if (eat('+')) return parseFactor() // + unário
                if (eat('-')) return -parseFactor() // - unário
                var x = 0.0
                val startPos = this.pos
                if (eat('(')) { // parênteses
                    x = parseExpression()
                    eat(')')
                } else if ((ch in '0'..'9') || ch == '.') { // números
                    while ((ch in '0'..'9') || ch == '.') nextChar()
                    x = java.lang.Double.parseDouble(str.substring(startPos, this.pos))
                } else if (ch in 'a'..'z') { // funções
                    while (ch in 'a'..'z') nextChar()
                    val func = str.substring(startPos, this.pos)
                    x = parseFactor()
                    when (func) {
                        "sqrt" -> x = Math.sqrt(x)
                        "sin" -> x = Math.sin(Math.toRadians(x))
                        "cos" -> x = Math.cos(Math.toRadians(x))
                        "tan" -> x = Math.tan(Math.toRadians(x))
                        else -> Toast.makeText(this@MainActivity, "Função desconhecida: $func", Toast.LENGTH_LONG).show()
                    }
                    //throw RuntimeException("Função desconhecida: " + func)
                } else {
                    Toast.makeText(this@MainActivity, "Caractere inesperado: $ch", Toast.LENGTH_LONG).show()
                    //throw RuntimeException("Caractere inesperado: " + ch.toChar())
                }
                if (eat('^')) x = Math.pow(x, parseFactor()) // potência
                return x
            }
        }.parse()
    }
}
