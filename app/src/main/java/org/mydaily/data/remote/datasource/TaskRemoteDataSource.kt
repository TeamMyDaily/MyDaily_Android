package org.mydaily.data.remote.datasource

import org.mydaily.data.model.network.request.ReqTaskAdd
import org.mydaily.data.model.network.response.ResTaskAdd
import org.mydaily.data.model.network.response.ResTaskDetail
import org.mydaily.data.model.network.response.ResTaskGet
import org.mydaily.data.model.network.response.Response
import retrofit2.Call

interface TaskRemoteDataSource {
    fun getTasks(jwt: String, date: Long): Call<ResTaskGet>
    fun postTasks(jwt: String, body: ReqTaskAdd): Call<ResTaskAdd>
    fun getTaskById(jwt: String, id: Int): Call<ResTaskDetail>
    fun putTask(jwt: String, id: Int): Call<Response>
    fun deleteTask(jwt: String, id: Int): Call<Response>
}