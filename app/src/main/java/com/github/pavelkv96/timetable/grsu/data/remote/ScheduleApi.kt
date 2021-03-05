/*
 * Copyright (C) 2021 The Android Open Source Project
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.github.pavelkv96.timetable.grsu.data.remote

import com.github.pavelkv96.timetable.grsu.data.remote.models.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ScheduleApi {

    @GET("getDepartments")
    suspend fun getDepartments(
        @Query("lang") lang: String
    ): Response<Entities<Department>>

    @GET("getFaculties")
    suspend fun getFaculties(
        @Query("lang") lang: String
    ): Response<Entities<Faculty>>

    @GET("getGroups")
    suspend fun getGroups(
        @Query("departmentId") departmentId: Int,
        @Query("facultyId") facultyId: Int,
        @Query("course") course: Int,
        @Query("lang") lang: String
    ): Response<Entities<Group>>

    //TODO fix request
    @GET("getTeachers?sort=surname,name&fields=surname,name,patronym,post")
    suspend fun getTeachers(
        @Query("lang") lang: String
    ): Response<Entities<Teacher>>

    @GET("getStudent")
    suspend fun getStudent(
        @Query("login") login: String,
        @Query("lang") lang: String
    ): Response<Student>

    @GET("getTeacherSchedule")
    suspend fun getTeacherSchedule(
        @Query("teacherId") scheduleId: Int,
        @Query("dateStart") dateStart: String,
        @Query("dateEnd") dateEnd: String,
        @Query("lang") lang: String
    ): Response<Schedule>

    @GET("getGroupSchedule")
    suspend fun getStudentSchedule(
        @Query("studentId") scheduleId: Int,
        @Query("dateStart") dateStart: String,
        @Query("dateEnd") dateEnd: String,
        @Query("lang") lang: String
    ): Response<Schedule>

    @GET("getGroupSchedule")
    suspend fun getGroupSchedule(
        @Query("groupId") scheduleId: Int,
        @Query("dateStart") dateStart: String,
        @Query("dateEnd") dateEnd: String,
        @Query("lang") lang: String
    ): Response<Schedule>
}