/**
 * Project:test
 * File:controllers.Application.java
 * 
 * History:
 * ----------------------------------------------------------------------------------------------------
 * Author                   | Date                |        Description                                |
 * ----------------------------------------------------------------------------------------------------
 *  Vishal Joshi            |Feb 06, 2015         | Creation                                          |
 * ----------------------------------------------------------------------------------------------------
 */

package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

    // just loads the main page
    public static Result index() {
        return ok(index.render());
    }

}
