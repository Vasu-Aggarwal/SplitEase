package com.example.splitease.ui.DetailedGroup

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.splitease.Models.TransactionsModel
import com.example.splitease.Models.UserDataModel
import com.example.splitease.R
import com.example.splitease.ui.DetailedGroup.Adapter.TransactionsAdapter
import com.example.splitease.ui.DetailedGroup.Adapter.UsersAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.lang.Exception

class DetailedGroup : AppCompatActivity() {

    private var userDataList: MutableList<UserDataModel> = ArrayList()
    private lateinit var userItemModel: MutableList<UserDataModel>
    private var transactionDataList: MutableList<TransactionsModel> = ArrayList()
    private lateinit var transactionItemModel : MutableList<TransactionsModel>
    val db = FirebaseFirestore.getInstance()
    var userAdaper: UsersAdapter?= null
    var transactionAdapter: TransactionsAdapter ?= null
    private lateinit var user: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private var groupId = ""
    private var transactionIds = ArrayList<Any>()
    private var userIds = ArrayList<Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_group)

        val bundle = intent.extras
        groupId = bundle?.getString("groupId").toString()

        findViewById<RecyclerView>(R.id.rvUsers)?.layoutManager = LinearLayoutManager(this)
        findViewById<RecyclerView>(R.id.rvTransactions)?.layoutManager = LinearLayoutManager(this)

        getGroupUsers()
        getAllGroupTransactions()
    }

    private fun getGroupUsers() {
        try {
            db.collection("GroupData").document(groupId)
                .get().addOnSuccessListener { it ->
                    userIds = (it.get("grp_users") as ArrayList<Any>)
                    for (tid in userIds){
                        db.collection("UserData").document(tid.toString())
                            .collection("users")
                            .get().addOnSuccessListener {
                                userItemModel = it.toObjects(UserDataModel::class.java)
                                userDataList.addAll(userItemModel)
                                setUserAdapter()
                            }
                    }
                }
        }
        catch (e: Exception){
            System.err.print("Some Error Occurred")
        }
    }

    private fun setUserAdapter() {
        if(userAdaper != null){
            userAdaper?.updateList(userDataList)
            findViewById<RecyclerView>(R.id.rvUsers)?.adapter = userAdaper
        }
        else {
            userAdaper = UsersAdapter(userDataList)
            findViewById<RecyclerView>(R.id.rvUsers)?.adapter = userAdaper
        }
    }

    private fun getAllGroupTransactions() {
        try {
            db.collection("GroupData").document(groupId)
                .get().addOnSuccessListener { it ->
                    transactionIds = (it.get("grp_transactions") as ArrayList<Any>)
                    for (tid in transactionIds){
                        db.collection("TransactionData").document(tid.toString())
                            .collection("trns")
                            .get().addOnSuccessListener {
                                transactionItemModel = it.toObjects(TransactionsModel::class.java)
                                transactionDataList.addAll(transactionItemModel)
                                setTransactionAdapter()
                            }
                    }
                }
        }
        catch (e: Exception){
            System.err.print("Some Error Occurred")
        }
    }

    private fun setTransactionAdapter() {
        if(transactionAdapter != null){
            transactionAdapter?.updateList(transactionDataList)
            findViewById<RecyclerView>(R.id.rvTransactions)?.adapter = transactionAdapter
        }
        else {
            transactionAdapter = TransactionsAdapter(transactionItemModel)
            findViewById<RecyclerView>(R.id.rvTransactions)?.adapter = transactionAdapter
        }
    }
}