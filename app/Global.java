import java.lang.reflect.Method;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import play.Application;
import play.Configuration;
import play.GlobalSettings;
import play.Logger;
import play.Logger.ALogger;
import play.Play;
import play.libs.F.Function0;
import play.libs.F.Promise;
import play.libs.Json;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Http.Request;
import play.mvc.Results;
import play.mvc.SimpleResult;
import product.dto.Error;

public class Global extends GlobalSettings {
    private static ALogger log = Logger.of(Global.class);
    private ApplicationContext applicationContext;

    /*
     * GlobalSettings Implementation
     */

    @Override
    public void onStart(Application app) {
        Configuration cfg = Play.application().configuration();
        String springConfigLoc = cfg.getString("spring.context.location");
        Logger.info("Spring Configuration location :  " + springConfigLoc);
        applicationContext = new ClassPathXmlApplicationContext(springConfigLoc);

    }

    @Override
    public <A> A getControllerInstance(Class<A> type) throws Exception {

        if (type.isAnnotationPresent(Controller.class)) {
            return applicationContext.getBean(type);
        } else {
            return super.getControllerInstance(type);
        }
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Action<?> onRequest(final Request req, Method actionMethod) {
        return new Action.Simple() {
            public Promise<SimpleResult> call(Context ctx) throws Throwable {

                try {
                    return this.delegate.call(ctx);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    return Promise.promise(new Function0<SimpleResult>() {
                        @Override
                        public SimpleResult apply() throws Throwable {
                            Error er = new Error("101", "some things gone bad");
                            return Results.ok(Json.toJson(er));
                        }

                    });
                }

            }
        };

    }

}