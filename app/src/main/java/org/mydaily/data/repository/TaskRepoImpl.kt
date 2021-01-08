package org.mydaily.data.repository

import org.mydaily.data.model.network.request.ReqTaskAdd
import org.mydaily.data.model.network.response.ResTaskAdd
import org.mydaily.data.model.network.response.ResTaskDetail
import org.mydaily.data.model.network.response.ResTaskGet
import org.mydaily.data.model.network.response.Response
import org.mydaily.data.remote.datasource.TaskRemoteDataSource
import retrofit2.Call

class TaskRepoImpl(private val remoteDataSource: TaskRemoteDataSource) : TaskRepo {
    override fun getTasks(jwt: String, date: Long): Call<ResTaskGet> =
        remoteDataSource.getTasks(jwt, date)

    override fun postTasks(jwt: String, body: ReqTaskAdd): Call<ResTaskAdd> =
        remoteDataSource.postTasks(jwt, body)

    override fun getTaskById(jwt: String, id: Int): Call<ResTaskDetail> =
        remoteDataSource.getTaskById(jwt, id)

    override fun putTask(jwt: String, id: Int): Call<Response> = remoteDataSource.putTask(jwt, id)

    override fun deleteTask(jwt: String, id: Int): Call<Response> =
        remoteDataSource.deleteTask(jwt, id)
}