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
import com.github.pavelkv96.timetable.grsu.utils.Mocks
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class ScheduleApiTest {

    private var mockScheduleApi: ScheduleApi = Mockito.mock(ScheduleApi::class.java)

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun departmentsModel() = runBlocking {
        val type = TypeToken.getParameterized(Entities::class.java, Department::class.java).type
        val call = Mocks.getSuccessResponse<Entities<Department>>("departments.json", type)
        `when`(mockScheduleApi.getDepartments("")).thenReturn(call)

        val entities = mockScheduleApi.getDepartments("").body()
        assertNotNull(entities)

        val departments = entities!!.items
        assertNotNull(departments)
        assertFalse(departments!!.isEmpty())
        assertEquals(4, departments.size.toLong())

        val error = entities.error
        assertNull(error)

        assertEquals(2, departments[0].id.toLong())
        assertEquals("дневная форма", departments[0].title)

        assertEquals(3, departments[1].id.toLong())
        assertEquals("заочная форма", departments[1].title)

        assertEquals(4, departments[2].id.toLong())
        assertEquals("вечерняя форма", departments[2].title)
    }

    @Test
    fun facultiesModel() = runBlocking {
        val type = TypeToken.getParameterized(Entities::class.java, Faculty::class.java).type
        val call = Mocks.getSuccessResponse<Entities<Faculty>>("faculties.json", type)
        `when`(mockScheduleApi.getFaculties("")).thenReturn(call)
        val groupsCall = mockScheduleApi.getFaculties("")

        val entities = groupsCall.body()
        assertNotNull(entities)

        val faculties = entities!!.items
        assertNotNull(faculties)
        assertFalse(faculties!!.isEmpty())
        assertEquals(18, faculties.size.toLong())

        assertEquals(4907, faculties[1].id.toLong())
        assertEquals("Инженерно-строительный факультет", faculties[1].title)

        assertEquals(4908, faculties[8].id.toLong())
        assertEquals("Факультет инновационных технологий машиностроения", faculties[8].title)

        assertEquals(30, faculties[14].id.toLong())
        assertEquals("Факультет экономики и управления", faculties[14].title)
    }

    @Test
    fun groupsModel() = runBlocking {
        val type = TypeToken.getParameterized(Entities::class.java, Group::class.java).type
        val call = Mocks.getSuccessResponse<Entities<Group>>("groups.json", type)
        `when`(mockScheduleApi.getGroups(0, 0, 0, "")).thenReturn(call)
        val groupsCall = mockScheduleApi.getGroups(0, 0, 0, "")

        val entities = groupsCall.body()
        assertNotNull(entities)

        val groups = entities!!.items
        assertNotNull(groups)
        assertFalse(groups!!.isEmpty())
        assertEquals(11, groups.size.toLong())

        assertEquals(6967, groups[1].id.toLong())
        assertEquals("МДП-ПКАД-172", groups[1].title)

        assertEquals(6748, groups[6].id.toLong())
        assertEquals("СДП-ИСИТ-171", groups[6].title)

        assertEquals(6750, groups[8].id.toLong())
        assertEquals("СДП-ФИК-171", groups[8].title)
    }

    @Test
    fun teachersModel() = runBlocking {
        val type = TypeToken.getParameterized(Entities::class.java, Teacher::class.java).type
        val call = Mocks.getSuccessResponse<Entities<Teacher>>("teachers.json", type)
        `when`(mockScheduleApi.getTeachers("ru_RU")).thenReturn(call)
        val groupsCall = mockScheduleApi.getTeachers("ru_RU")

        val entities = groupsCall.body()
        assertNotNull(entities)

        val teachers = entities!!.items
        assertNotNull(teachers)
        assertFalse(teachers!!.isEmpty())
        assertEquals(1055, teachers.size.toLong())

        var position = 19
        assertEquals(8509, teachers[position].id.toLong())
        assertEquals("Анисько", teachers[position].surname)
        assertEquals("Пётр", teachers[position].name)
        assertEquals("Евгеньевич", teachers[position].patronymic)
        assertEquals("доц.", teachers[position].post)

        position = 103
        assertEquals(76745, teachers[position].id.toLong())
        assertEquals("Булай", teachers[position].surname)
        assertEquals("Татьяна", teachers[position].name)
        assertEquals("Вячеславовна", teachers[position].patronymic)
        assertEquals("ст.пр.", teachers[position].post)

        position = 906
        assertEquals(5836, teachers[position].id.toLong())
        assertEquals("Тюненкова", teachers[position].surname)
        assertEquals("Елена", teachers[position].name)
        assertEquals("Владимировна", teachers[position].patronymic)
        assertEquals("доц.", teachers[position].post)
    }

    @Test
    fun studentModel() = runBlocking {
        val call = Mocks.getSuccessResponse<Student>("student.json", Student::class.java)
        `when`(mockScheduleApi.getStudent("some_login", "ru_RU")).thenReturn(call)
        val studentCall = mockScheduleApi.getStudent("some_login", "ru_RU")

        val student = studentCall.body()
        assertNotNull(student)

        assertNotEquals(0, student!!.id.toLong())
        assertEquals(145491, student.id.toLong())

        assertNotEquals("", student.fullname)
        assertEquals("Калинин Павел Викторович", student.fullname)

        assertNotEquals("", student.studenttype)
        assertEquals("Магистрант", student.studenttype)

        assertNotEquals("", student.grouptitle)
        assertEquals("МДП-ПКАД-182", student.grouptitle)

        assertNotEquals("", student.nzach)
        assertEquals("2018-125", student.nzach)
    }

    @Test
    fun teacherScheduleModel() = runBlocking {
        val call = Mocks.getSuccessResponse<Schedule>("teacher_schedule.json", Schedule::class.java)
        `when`(mockScheduleApi.getTeacherSchedule(0, "", "", "")).thenReturn(call)
        val scheduleCall = mockScheduleApi.getTeacherSchedule(0, "", "", "")

        val schedule = scheduleCall.body()
        assertNotNull(schedule)
        assertEquals(3, schedule!!.count.toLong())

        val days = schedule.days
        assertNotNull(days)
        assertFalse(days!!.isEmpty())
        assertEquals(3, days.size.toLong())

        val day = days[2]
        assertNotNull(day)
        assertEquals(3, day.count.toLong())
        assertEquals(5, day.num.toLong())
        assertEquals("2018-09-14", day.date)

        val lessons = day.lessons
        assertNotNull(lessons)
        assertFalse(lessons!!.isEmpty())
        assertEquals(3, lessons.size.toLong())

        val lesson = lessons[1]
        assertNotNull(lesson)
        assertEquals("13:30", lesson.timeStart)
        assertEquals("14:50", lesson.timeEnd)
        assertEquals("Инженерная компьютерная графика", lesson.title)
        assertEquals("Гаспадарчая, 23", lesson.address)
        assertEquals("306", lesson.room)
        assertEquals("лек.", lesson.type)

        val groups = lesson.groups
        assertNotNull(groups)
        assertEquals("СДП-ЭМ-181", groups!![0].title)
    }

    @Test
    fun studentScheduleModel() = runBlocking {
        val call = Mocks.getSuccessResponse<Schedule>(
            "group_schedule_by_student_id.json",
            Schedule::class.java
        )
        `when`(mockScheduleApi.getStudentSchedule(0, "", "", "")).thenReturn(call)
        val scheduleCall = mockScheduleApi.getStudentSchedule(0, "", "", "")

        val schedule = scheduleCall.body()
        assertNotNull(schedule)
        assertEquals(4, schedule!!.count.toLong())

        val days = schedule.days
        assertNotNull(days)
        assertFalse(days!!.isEmpty())
        assertEquals(4, days.size.toLong())

        val day = days[1]
        assertNotNull(day)
        assertEquals(2, day.count.toLong())
        assertEquals(2, day.num.toLong())
        assertEquals("2018-09-11", day.date)

        val lessons = day.lessons
        assertNotNull(lessons)
        assertFalse(lessons!!.isEmpty())
        assertEquals(2, lessons.size.toLong())

        val lesson = lessons[1]
        assertNotNull(lesson)
        assertEquals("19:50", lesson.timeStart)
        assertEquals("21:10", lesson.timeEnd)
        assertEquals("Мат. моделир. и оптимизация сложных сист.", lesson.title)
        assertEquals("Гаспадарчая, 23", lesson.address)
        assertEquals("204", lesson.room)
        assertEquals("лаб.", lesson.type)

        val teacher = lesson.teacher
        assertNotNull(teacher)
        assertEquals(14785, teacher!!.id.toLong())
        assertEquals("Цехан Ольга Борисовна", teacher.fullName)
    }

    @Test
    fun groupScheduleModel() = runBlocking {
        val call = Mocks.getSuccessResponse<Schedule>(
            "group_schedule_by_group_id.json",
            Schedule::class.java
        )
        `when`(mockScheduleApi.getGroupSchedule(0, "", "", "")).thenReturn(call)
        val scheduleCall = mockScheduleApi.getGroupSchedule(0, "", "", "")

        val schedule = scheduleCall.body()
        assertNotNull(schedule)
        assertEquals(4, schedule!!.count.toLong())

        val days = schedule.days
        assertNotNull(days)
        assertFalse(days!!.isEmpty())
        assertEquals(4, days.size.toLong())

        val day = days[1]
        assertNotNull(day)
        assertEquals(2, day.count.toLong())
        assertEquals(2, day.num.toLong())
        assertEquals("2018-09-11", day.date)

        val lessons = day.lessons
        assertNotNull(lessons)
        assertFalse(lessons!!.isEmpty())
        assertEquals(2, lessons.size.toLong())

        val lesson = lessons[1]
        assertNotNull(lesson)
        assertEquals("19:50", lesson.timeStart)
        assertEquals("21:10", lesson.timeEnd)
        assertEquals("Мат. моделир. и оптимизация сложных сист.", lesson.title)
        assertEquals("Гаспадарчая, 23", lesson.address)
        assertEquals("204", lesson.room)
        assertEquals("лаб.", lesson.type)

        val teacher = lesson.teacher
        assertNotNull(teacher)
        assertEquals(14785, teacher!!.id.toLong())
        assertEquals("Цехан Ольга Борисовна", teacher.fullName)
    }

    @Test
    fun error() = runBlocking {
        val type = TypeToken.getParameterized(Entities::class.java, Department::class.java).type
        val call = Mocks.getSuccessResponse<Entities<Department>>("error.json", type)
        `when`(mockScheduleApi.getDepartments("")).thenReturn(call)
        val departmentCall = mockScheduleApi.getDepartments("")

        val entities = departmentCall.body()
        assertNotNull(entities)

        val departments = entities!!.items
        assertNull(departments)

        val error = entities.error
        assertNotNull(error)
        assertEquals(11002, error!!.code.toLong())
        val errorMessage = "Параметр sort метода getTeachers содержит несуществующее имя поля"
        assertEquals(errorMessage, error.message)
        val errorDetails = "fullname"
        assertEquals(errorDetails, error.details)
    }

    @Test
    fun `error 500`() = runBlocking {
        val call = Mocks.getErrorResponse<Entities<Department>>(500, "EMPTY")
        `when`(mockScheduleApi.getDepartments("")).thenReturn(call)
        val departmentCall = mockScheduleApi.getDepartments("")

        val entities = departmentCall.body()
        assertNull(entities)
        assertEquals(departmentCall.code(), 500)
        assertEquals(departmentCall.errorBody()!!.string(), "EMPTY")
    }
}
