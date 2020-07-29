/*
 * Copyright 2017-2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.cloudopt.next.web.render

import net.cloudopt.next.web.NextServer
import net.cloudopt.next.web.Resource

/*
 * @author: Cloudopt
 * @Time: 2018/1/10
 * @Description: Render Interface
 */
interface Render {

    /**
     * Output html fragment.
     * @param resource Resource object
     * @param obj May be the view object may also be just a simple text
     */
    fun render(resource: Resource, obj: Any)

    /**
     * Ends the response. If no data has been written to the response body,
     * the actual response won't get written until this method gets called.
     * @see net.cloudopt.next.web.Resource
     * @param resource Resource object
     */
    fun end(resource: Resource) {
        resource.response.end()
    }

    /**
     * Ends the response. If no data has been written to the response body,
     * the actual response won't get written until this method gets called.
     * @see net.cloudopt.next.web.Resource
     * @param resource Resource object
     * @param text the string to write before ending the response
     */
    fun end(resource: Resource, text: String) {
        NextServer.handlers.forEach { handler ->
            if(!handler.afterRender(resource, text)){
                if (!resource.context.response().ended()) {
                    resource.context.response().end()
                }
                return
            }
        }
        resource.response.end(text)
    }

}
