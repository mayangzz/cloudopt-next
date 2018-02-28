/*
 * Copyright 2017 Cloudopt.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *  The Eclipse Public License is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  The Apache License v2.0 is available at
 *  http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 */
package net.cloudopt.next.web.test.controller

import io.vertx.core.AsyncResult
import io.vertx.core.Future
import io.vertx.core.Handler
import net.cloudopt.next.web.CloudoptServer
import net.cloudopt.next.web.Resource
import net.cloudopt.next.web.Validator
import net.cloudopt.next.web.Worker
import net.cloudopt.next.web.event.EventManager
import net.cloudopt.next.web.render.View
import net.cloudopt.next.web.route.API
import net.cloudopt.next.web.route.GET
import net.cloudopt.next.web.test.interceptor.TestInterceptor
import net.cloudopt.next.web.test.validator.TestValidator
import kotlin.reflect.KClass


/*
 * @author: Cloudopt
 * @Time: 2018/1/26
 * @Description: Test Controller
 */
@API("/",interceptor = arrayOf(TestInterceptor::class))
class IndexController : Resource() {

    @GET(valid = arrayOf(TestValidator::class))
    fun index() {
        var name = getParam("name") ?: ""
        renderText(name)
    }

    @GET("html")
    fun html() {
        var view = View()
        view.view = "index"
        renderHtml(view)
    }

    @GET("free")
    fun free() {
        var view = View()
        view.view = "index"
        view.parameters.put("name", "free")
        renderFree(view)
    }

    @GET("hbs")
    fun hbs() {
        var view = View()
        view.view = "index"
        view.parameters.put("name", "hbs")
        renderHbs(view)
    }

    @GET("json")
    fun json() {
        var map = hashMapOf<String, Any>()
        map.put("a", 1)
        map.put("b", 2)
        renderJson(map)
    }

    @GET("beetl")
    fun beetl() {
        var view = View()
        view.view = "index"
        view.parameters.put("name", "beetl")
        renderBeetl(view)
    }

    @GET("event")
    fun event() {
        EventManager.send("net.cloudopt.web.test", "This is test message!")
        renderJson("Send Event!")
    }

    @GET("500")
    fun fail500() {
        fail500()
    }

    @GET("i18n")
    fun i18n() {
        renderText(lang())
    }

    @GET("asyn")
    fun asyn() {
        Worker.worker<Any>(Handler<Future<Any>>{ f ->
            renderText("success!")
        }, Handler<AsyncResult<Any>>{ result ->

        })
    }

}