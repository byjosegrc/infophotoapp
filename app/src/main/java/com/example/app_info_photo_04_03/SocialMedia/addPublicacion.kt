package com.example.app_info_photo_04_03.SocialMedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.app_info_photo_04_03.Home.HomeActivity
import com.example.app_info_photo_04_03.PerfilUsuario
import com.example.app_info_photo_04_03.R
import com.example.app_info_photo_04_03.databinding.ActivityAddPublicacionBinding
import com.example.app_info_photo_04_03.model.Likes
import com.example.app_info_photo_04_03.model.Publicacion
import com.example.app_info_photo_04_03.pref.Prefs
import com.google.firebase.database.FirebaseDatabase

class addPublicacion : AppCompatActivity() {

        lateinit var binding: ActivityAddPublicacionBinding
        lateinit var prefs: Prefs
        lateinit var db: FirebaseDatabase
    /**
     *Esta es la funcion on  ejecutas la lógica de arranque básica de la aplicación que debe ocurrir una
     * sola vez en toda la vida de la actividad. Por ejemplo, tu implementación de onCreate() podría vincular
     * datos a listas, asociar la actividad con un ViewModel y crear instancias de algunas variables de alcance de clase.
     */

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityAddPublicacionBinding.inflate(layoutInflater)
            prefs = Prefs(this)

            title = "\uD83D\uDCAC \uD835\uDC12\uD835\uDC14\uD835\uDC01\uD835\uDC08\uD835\uDC11 \uD835\uDC0F\uD835\uDC0E\uD835\uDC12\uD835\uDC13"

            //REAL TIME:

            db = FirebaseDatabase.getInstance("https://infophoto-2023-default-rtdb.europe-west1.firebasedatabase.app/")
            setContentView(binding.root)
            setListeners()
        }


    /**
     * Funcion para el listener de los botones  btnReset y  btnAdd
     *
     * Donde ...
     *
     * btnReset -> Borra el contenido introducido por pantalla
     *
     * btnAdd -> Llama a la funcion añadirPost()
     *
     */
        private fun setListeners() {
            binding.btnReset.setOnClickListener {
                binding.etPost.text.clear()
            }
            binding.btnAdd.setOnClickListener {
                añadirPost()
            }
        }


    /**
     *Esta funcion añado a firebase el post para ello el campo de contenido no puede estar vacio
     */


    private fun añadirPost() {
            var contenido = binding.etPost.text.toString().trim()
            if (contenido.isEmpty()) {
                binding.etPost.error = "Este campo no puede estar vacio!"
                binding.etPost.requestFocus()
                return
            }


            //No esta vacio, añadimos
            val post = Publicacion(contenido = contenido, autor = prefs.getEmail().toString())

            val likes = Likes(idPost = post.fecha )

            db.getReference("posts").child(post.fecha.toString()).setValue(post).addOnSuccessListener {
                db.getReference("likes").child(post.fecha.toString()).setValue(likes).addOnSuccessListener {
                    finish()
                }
            }
        }

    /**
     * funcion de onCreateOptionsMenu() es el que nos va a permitir inflar nuestro menú, es decir, hacer
     * que las opciones definidas en el fichero XML tengan su propia apariencia dentro de nuestra aplicación Android.
     */

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu1, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * En este método, puedes aumentar el recurso de menú (definido en XML) hacia el Menu proporcionado
     * en la devolución de llamada
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.inicio -> {
                startActivity(Intent(this, HomeActivity::class.java))
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    }