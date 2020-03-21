package com.example.sqlite_01

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.sqlite_01.`object`.EmpModelClass
import com.example.sqlite_01.helper.MyAdapter
import com.example.sqlite_01.model.DatabaseHandler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewRecord()
    }

    // fungsi untuk menyimpan data 
    fun saveRecord(view: View){

        //untuk mengambil data dari editText
        val id = u_id.text.toString()
        val name = u_name.text.toString()
        val email = u_email.text.toString()
        val telepon = u_telepon.text.toString()

        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        if(id.trim()!="" && name.trim()!="" && email.trim()!="" && telepon.trim()!=""){
            //digunakan untuk memanggil fun dari databaseHandler untuk menambah data
            val status = databaseHandler.addEmployee(EmpModelClass(Integer.parseInt(id),name, email, telepon))
            if(status > -1){
                Toast.makeText(applicationContext,"Data telah disimpan",Toast.LENGTH_LONG).show()
                u_id.text.clear() //untuk mengosongkan field pada editText setelah menekan button Insert
                u_name.text.clear() //untuk mengosongkan field pada editText setelah menekan button Insert
                u_email.text.clear() //untuk mengosongkan field pada editText setelah menekan button Insert
                u_telepon.text.clear() //untuk mengosongkan field pada editText setelah menekan button Insert
                viewRecord()
            }
        }else{
            Toast.makeText(applicationContext,"silahkan lengkapi data yang belum terisi",Toast.LENGTH_LONG).show()
        }

    }
    // fungsi untuk membaca data dari database dan menampilkannya dari listview
    fun viewRecord(){
        // membuat instanisasi databasehandler
        val databaseHandler: DatabaseHandler= DatabaseHandler(this)

        // memamnggil fungsi viewemployee dari databsehandler untuk mengambil data
        val emp: List<EmpModelClass> = databaseHandler.viewEmployee()
        val empArrayId = Array<String>(emp.size){"0"}
        val empArrayName = Array<String>(emp.size){"null"}
        val empArrayEmail = Array<String>(emp.size){"null"}
        val empArrayTelepon = Array<String>(emp.size){"null"}
        var index = 0

        // setiap data yang didapatkan dari database akan dimasukkan ke array
        for(e in emp){
            empArrayId[index] = e.userId.toString()
            empArrayName[index] = e.userName
            empArrayEmail[index] = e.userEmail
            empArrayTelepon[index] = e.userTelepon
            index++
        }

        // membuat customadapter untuk view UI
        val myListAdapter = MyAdapter(this,empArrayId,empArrayName,empArrayEmail, empArrayTelepon)
        listView.adapter = myListAdapter

        // ketika user menekan data pada listview
        listView.setOnItemClickListener{
                parent, view, position, id ->

            var view_id = empArrayId[position]
            var view_name = empArrayName[position]
            var view_email = empArrayEmail[position]
            var view_telepon = empArrayTelepon[position]

            actionRecord(view_id, view_name, view_email, view_telepon)
        }

    }

    // fungsi untuk memperbarui data dan menghapus data dengan menekan data pada listview
    fun actionRecord(data1: String, data2: String, data3: String, data4: String) {

        //insisalisasi editText untuk memunculkan data ke editText
        val editId = findViewById(R.id.u_id) as EditText
        val editName = findViewById(R.id.u_name) as EditText
        val editEmail = findViewById(R.id.u_email) as EditText
        val editTelepon = findViewById(R.id.u_telepon) as EditText

        //menampilkan data ke editText
        editId.setText(data1)
        editName.setText(data2)
        editEmail.setText(data3)
        editTelepon.setText(data4)

        //ketika user menekan button update, source code ini digunakan untuk menghapus data ketika user menekan salah satu data pada listview
        update.setOnClickListener{

            //mengambil data dari editText
            val updateId = editId.text.toString()
            val updateName = editName.text.toString()
            val updateEmail = editEmail.text.toString()
            val updateTelepon = editTelepon.text.toString()

            val databaseHandler: DatabaseHandler= DatabaseHandler(this)
            if(updateId.trim()!="" && updateName.trim()!="" && updateEmail.trim()!="" && updateTelepon.trim()!=""){
                //digunakan untuk memanggil fun dari databaseHandler untuk memperbarui data
                val status = databaseHandler.updateEmployee(EmpModelClass(Integer.parseInt(updateId), updateName, updateEmail, updateTelepon))
                if(status > -1){
                    Toast.makeText(applicationContext,"data ke - $data1 telah diupdate",Toast.LENGTH_LONG).show()
                    u_id.text.clear() //untuk mengosongkan field pada editText setelah menekan button Update
                    u_name.text.clear() //untuk mengosongkan field pada editText setelah menekan button Update
                    u_email.text.clear() //untuk mengosongkan field pada editText setelah menekan button Update
                    u_telepon.text.clear() //untuk mengosongkan field pada editText setelah menekan button Update
                    viewRecord()
                }
            }else{
                Toast.makeText(applicationContext,"silahkan lengkapi data yang belum terisi",Toast.LENGTH_LONG).show()
            }
        }

        //ketika user menekan button delete, source code ini digunakan untuk menghapus data ketika user menekan salah satu data pada listview
        delete.setOnClickListener{

            //mengambil data dari editText
            val updateId = editId.text.toString()
            val databaseHandler: DatabaseHandler= DatabaseHandler(this)

            //digunakan untuk memanggil fun dari databaseHandler untuk menghapus data
            val status = databaseHandler.deleteEmployee(EmpModelClass(Integer.parseInt(updateId),"","", ""))
            if(status > -1){
                Toast.makeText(applicationContext,"data ke - $data1 telah dihapus",Toast.LENGTH_LONG).show()
                u_id.text.clear() //untuk mengosongkan field pada editText setelah menekan button Delete
                u_name.text.clear() //untuk mengosongkan field pada editText setelah menekan button Delete
                u_email.text.clear() //untuk mengosongkan field pada editText setelah menekan button Delete
                u_telepon.text.clear() //untuk mengosongkan field pada editText setelah menekan button Delete
                viewRecord()
            }

        }

    }

}
